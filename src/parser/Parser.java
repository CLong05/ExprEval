package parser;

import exceptions.*;
import expr.*;
import token.Dollar;
import token.Scanner;
import token.typeofToken;

import java.util.ArrayList;

/**
 * Parser，进行语法分析和语义分析，最后返回表达式的运算结果
 */
public class Parser {
	/**
	 * 词法分析器
	 */
    private final Scanner scanner;
	
	/**
	 * 归约器
	 */
    private final Reducer reducer;
	
	/**
	 * 存放OPP运算符的栈
	 */
    private final ArrayList<Expr> stack;
	
	/**
	 * 辅助栈，存放OPP的运算数据
	 */
	private final ArrayList<Expr> auxiliaryStack;
	
	/**
	 * 记录lookahead的内容
	 */
    private Terminal lookahead;

    /**
     * 构造函数
     *
     * @param input 用户输入的表达式
     * @throws EmptyExpressionException 空表达式错误
     */
    public Parser(String input) throws EmptyExpressionException {
        if (input.isEmpty())  			        // 判断输入串是否为空串
            throw new EmptyExpressionException();
        scanner = new Scanner(input);
        reducer = new Reducer();
        stack = new ArrayList<>();
		auxiliaryStack = new ArrayList<>();
        stack.add(new Terminal(new Dollar()));  // 在操作符栈中加入$
    }

    /**
     * 进行parse动作的函数,由calculator进行调用
     * 当值为0时代表shift，1代表reduce，2代表accept，其余不同的负值代表不同类型错误
     *
     * @return 计算结果
     * @throws ExpressionException 不同错误的集合
     */
    public double parse() throws ExpressionException {
        lookahead = new Terminal(scanner.getNextToken());
        if(lookahead.getTag() == typeofToken.DOLLAR) 
			throw new EmptyExpressionException();  // 如果返回符号是$，则说明原表达式只有空格，进行报错
		while (true) {
			Expr topMostTerminal = stack.get(stack.size() - 1);  // 获取操作符栈顶的操作符
			/* 查询算符优先关系表，执行相应的操作 */
            switch (OPPTable.table[topMostTerminal.getTag()][lookahead.getTag()]) {
                case 0:
                    shift();
                    break;
                case 1:
                    reduce();
                    break;
                case 2:
                    return accept();
                case -1:
                    throw new MissingOperatorException();
                case -2:
                    throw new MissingRightParenthesisException();
                case -3:
                    throw new MissingLeftParenthesisException();
                case -4:
                    throw new FunctionCallException();
                case -5:
                    throw new TypeMismatchedException();
                case -6:
                    throw new MissingOperandException();
                case -7:
                    throw new TrinaryOperationException();
            }
        }
    }

    /**
     * 算符优先关系表，在parser中使用
	 * 下表中对应的顺序对应的是typeofToken中的编号顺序
	 * 从左到右与从上到下依次是num,bool,+-,* /,-,^,fuc,(,,,),Relation,!,&,|,?,:,$
     * 当值为0时代表shift，1代表reduce，2代表accept，其余不同的负值代表不同类型错误
     */
    public static class OPPTable {
        public final static int[][] table = new int[][]{
                {-1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -5, 1, 1, 1, 1, 1},
                {-1, -1, -5, -5, -5, -5, -1, -1, -5, 1, -5, -1, 1, 1, 1, 1, 1},
                {0, -5, 1, 0, 0, 0, 0, 0, 1, 1, 1, -5, -5, -5, -5, 1, 1},
                {0, -5, 1, 1, 0, 0, 0, 0, 1, 1, 1, -5, -5, -5, -5, 1, 1},
                {0, -5, 1, 1, 0, 1, 0, 0, 1, 1, 1, -5, -5, -5, -5, 1, 1},
                {0, -5, 1, 1, 0, 0, 0, 0, 1, 1, 1, -5, -5, -5, -5, 1, 1},
                {-3, -3, -3, -3, -3, -3, -3, 0, -3, 1, -3, -3, -3, -3, -3, 1, -3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2},
                {0, -5, 0, 0, 0, 0, 0, 0, 0, 0, -5, -5, -5, -5, 0, 0, -2},
                {-1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, -1, 1, 1, 1, 1, 1},
                {0, -5, 0, 0, 0, 0, 0, 0, -5, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, -5, -5, -5, -5, -5, 0, -5, 1, 0, 0, 1, 1, 1, 1, 1},
                {0, 0, -5, -5, -5, -5, -5, 0, -5, 1, 0, 0, 1, 1, 1, 1, 1},
                {0, 0, -5, -5, -5, -5, -5, 0, -5, 1, 0, 0, 0, 1, 1, 1, 1},
                {0, -7, 0, 0, 0, 0, 0, 0, -7, -6, 0, -7, -7, -7, 0, 0, -7},
                {0, -7, 0, 0, 0, 0, 0, 0, 1, 1, 0, -7, -7, -7, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, -3, -3, 0, 0, 0, 0, 0, -7, 2}
        };
    }

    /**
     * 执行accept的操作，并返回运算的结果
     */
	private double accept() throws TypeMismatchedException {
        Expr k = auxiliaryStack.get(stack.size() - 1);
        if (k.getTag() == typeofToken.BoolExpr) {
            System.out.println(((BoolExpr) k).getValue());
            throw new TypeMismatchedException();
        }
        return ((ArithExpr) k).getValue();
    }

    /**
     * 执行归约操作
     */
	private void reduce() throws ExpressionException {
        Expr topMostTerminal = stack.get(stack.size() - 1);  // 获取操作符栈顶的操作符
        switch (topMostTerminal.getTag()) {					 // 根据该操作符的类型执行对应的归约操作
            case typeofToken.NUM:
                reducer.numReducer(stack,auxiliaryStack);
                break;
            case typeofToken.BOOL:
                reducer.boolReducer(stack,auxiliaryStack);
                break;
            case typeofToken.ADDSUB:
                reducer.add_subReducer(stack,auxiliaryStack);
                break;
            case typeofToken.MULDIV:
                reducer.mul_divReducer(stack,auxiliaryStack);
                break;
            case typeofToken.NOT:
                reducer.notReducer(stack,auxiliaryStack);
                break;
            case typeofToken.NEG:
                reducer.negReducer(stack,auxiliaryStack);
                break;
            case typeofToken.POWER:
                reducer.powReducer(stack,auxiliaryStack);
                break;
            case typeofToken.AND:
            case typeofToken.OR:
                reducer.and_orReducer(stack,auxiliaryStack);
                break;
            case typeofToken.RE:
                reducer.relationReducer(stack,auxiliaryStack);
                break;
            case typeofToken.COLON:
                reducer.colonReducer(stack,auxiliaryStack);
                break;
            case typeofToken.RP:
                reducer.rpReducer(stack,auxiliaryStack);
                break;
            default:
                System.out.println(topMostTerminal.getTag());
                break;
        }
    }

    /**
     * 执行移进操作: 各类终结符都会先shift进栈，然后在后续的执行过中被归约
     */
	private void shift() throws ExpressionException {
        stack.add(lookahead);
        lookahead = new Terminal(scanner.getNextToken());  // 读取下一个输入
    }

}