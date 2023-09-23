package parser;

import exceptions.*;
import expr.*;
import token.Dollar;
import token.Scanner;
import token.typeofToken;

import java.util.ArrayList;

/**
 * Parser�������﷨�����������������󷵻ر��ʽ��������
 */
public class Parser {
	/**
	 * �ʷ�������
	 */
    private final Scanner scanner;
	
	/**
	 * ��Լ��
	 */
    private final Reducer reducer;
	
	/**
	 * ���OPP�������ջ
	 */
    private final ArrayList<Expr> stack;
	
	/**
	 * ����ջ�����OPP����������
	 */
	private final ArrayList<Expr> auxiliaryStack;
	
	/**
	 * ��¼lookahead������
	 */
    private Terminal lookahead;

    /**
     * ���캯��
     *
     * @param input �û�����ı��ʽ
     * @throws EmptyExpressionException �ձ��ʽ����
     */
    public Parser(String input) throws EmptyExpressionException {
        if (input.isEmpty())  			        // �ж����봮�Ƿ�Ϊ�մ�
            throw new EmptyExpressionException();
        scanner = new Scanner(input);
        reducer = new Reducer();
        stack = new ArrayList<>();
		auxiliaryStack = new ArrayList<>();
        stack.add(new Terminal(new Dollar()));  // �ڲ�����ջ�м���$
    }

    /**
     * ����parse�����ĺ���,��calculator���е���
     * ��ֵΪ0ʱ����shift��1����reduce��2����accept�����಻ͬ�ĸ�ֵ����ͬ���ʹ���
     *
     * @return ������
     * @throws ExpressionException ��ͬ����ļ���
     */
    public double parse() throws ExpressionException {
        lookahead = new Terminal(scanner.getNextToken());
        if(lookahead.getTag() == typeofToken.DOLLAR) 
			throw new EmptyExpressionException();  // ������ط�����$����˵��ԭ���ʽֻ�пո񣬽��б���
		while (true) {
			Expr topMostTerminal = stack.get(stack.size() - 1);  // ��ȡ������ջ���Ĳ�����
			/* ��ѯ������ȹ�ϵ��ִ����Ӧ�Ĳ��� */
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
     * ������ȹ�ϵ����parser��ʹ��
	 * �±��ж�Ӧ��˳���Ӧ����typeofToken�еı��˳��
	 * ����������ϵ���������num,bool,+-,* /,-,^,fuc,(,,,),Relation,!,&,|,?,:,$
     * ��ֵΪ0ʱ����shift��1����reduce��2����accept�����಻ͬ�ĸ�ֵ����ͬ���ʹ���
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
     * ִ��accept�Ĳ���������������Ľ��
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
     * ִ�й�Լ����
     */
	private void reduce() throws ExpressionException {
        Expr topMostTerminal = stack.get(stack.size() - 1);  // ��ȡ������ջ���Ĳ�����
        switch (topMostTerminal.getTag()) {					 // ���ݸò�����������ִ�ж�Ӧ�Ĺ�Լ����
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
     * ִ���ƽ�����: �����ս��������shift��ջ��Ȼ���ں�����ִ�й��б���Լ
     */
	private void shift() throws ExpressionException {
        stack.add(lookahead);
        lookahead = new Terminal(scanner.getNextToken());  // ��ȡ��һ������
    }

}