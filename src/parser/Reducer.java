package parser;

import exceptions.*;
import expr.*;
import token.Boolean;
import token.Decimal;
import token.Scanner;
import token.typeofToken;

import java.util.ArrayList;

/**
 * 执行归约操作
 */
public class Reducer {
    /**
     * Num reducer: 归约不带“-”的操作数。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     */
    public void numReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) {
        int i = stack.size() - 1;
        Terminal k = (Terminal) stack.get(i);
        ArithExpr arithExpr = new ArithExpr((Decimal) k.token);
        stack.remove(i);  // 从操作符栈中移除该操作数
        auxiliaryStack.add(arithExpr);  // 将归约后的操作数放入辅助栈中
    }

    /**
     * Bool reducer：归约布尔类型数据。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void boolReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
        Terminal t = (Terminal) stack.get(i);
        BoolExpr b = new BoolExpr((Boolean) t.token);
        stack.remove(i);  // 从操作符栈中移除该操作数
        auxiliaryStack.add(b);  // 将归约后的操作数放入辅助栈中
    }

    /**
     * Neg reducer：归约带有一元运算符的“-”的操作数。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void negReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = auxiliaryStack.size() - 1;
        Expr k = auxiliaryStack.get(i);
        //if (k.getTag() != typeofToken.ArithExpr)
        //    throw new TypeMismatchedException();
        double v = ((Decimal) k.token).getValue();
        ArithExpr arithExpr = new ArithExpr(-v);  // 改为负值
        stack.remove(stack.size()-1);	   // 从操作符栈中移除“-”
		auxiliaryStack.remove(i);
        auxiliaryStack.add(arithExpr);  // 将归约后的操作数放入辅助栈中
    }

    /**
     * Not reducer：归约非运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void notReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = auxiliaryStack.size() - 1;
        Expr a = auxiliaryStack.get(i);
        if (a.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
        BoolExpr be = new BoolExpr(!((BoolExpr) a).getValue());
		stack.remove(stack.size()-1);	    // 从操作符栈中移除"!"
		auxiliaryStack.remove(i);			// 从操作符栈中移除该操作数  
        auxiliaryStack.add(be);  // 将归约后的操作数放入辅助栈中
    }

    /**
     * Add sub reducer：归约加减运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void add_subReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
		Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* 若出现布尔表达式则报错 */
        if (a.getTag() == typeofToken.BoolExpr || b.getTag() == typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* 若操作数不是算术表达式则报错 */
        else if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new MissingOperandException();
		ArithExpr arithExpr;
        if (o.token.getString().equals("+"))  // 加法
            arithExpr = new ArithExpr(((ArithExpr) a).getValue() + ((ArithExpr) b).getValue());
        else  								  // 减法
            arithExpr = new ArithExpr(((ArithExpr) a).getValue() - ((ArithExpr) b).getValue());
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(arithExpr);   // 将归约后的操作数放入辅助栈中
    }

    /**
     * And or reducer：归约与或操作。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void and_orReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* 若辅助栈内的操作数不足则报错 */
		if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(i);
		/* 若操作数不是布尔表达式则报错 */
        if (a.getTag() != typeofToken.BoolExpr || b.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		
		BoolExpr be;
        if (o.getTag() == typeofToken.AND)  // &运算
            be = new BoolExpr(((BoolExpr) a).getValue() && ((BoolExpr) b).getValue());
        else							   // |运算
            be = new BoolExpr(((BoolExpr) a).getValue() || ((BoolExpr) b).getValue());
        stack.remove(i);
		auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);       
        auxiliaryStack.add(be);  // 将归约后的操作数放入辅助栈中
    }

    /**
     * Mul div reducer：归约乘除运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void mul_divReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {		
		int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
		Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* 若出现布尔表达式则报错 */
        if (a.getTag() == typeofToken.BoolExpr || b.getTag() == typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* 若操作数不是算术表达式则报错 */
        else if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new MissingOperandException();
		ArithExpr ae;
        if (o.token.getString().equals("*"))  // 乘法
            ae = new ArithExpr(((ArithExpr) a).getValue() * ((ArithExpr) b).getValue());
        else if (((ArithExpr) b).getValue() == 0)  // 除以0
            throw new DividedByZeroException();
        else  // 除法
            ae = new ArithExpr(((ArithExpr) a).getValue() / ((ArithExpr) b).getValue());
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(ae);   // 将归约后的操作数放入辅助栈中
    }

    /**
     * Colon reducer： （？：）归约三元运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void colonReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;

		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 3 || stack.size() < 2)
            throw new MissingOperandException();

		Expr a = auxiliaryStack.get(j);
        Expr b = auxiliaryStack.get(j - 1);
		Expr c = stack.get(i - 1);  // ?号
        Expr d = auxiliaryStack.get(j - 2);
				
        /* 若各部分的表达式类型不匹配则报错 */
		if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr || d.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* 若“：”的前一个操作符不是“？”则报错 */
        if (c.getTag() != typeofToken.QM)
            throw new exceptions.TrinaryOperationException();
        if (((BoolExpr) d).getValue()) { 	// true
            stack.remove(i);
            stack.remove(i - 1);
            auxiliaryStack.remove(j);
			auxiliaryStack.remove(j - 2);
			
        } else {  							// false
            stack.remove(i);
            stack.remove(i - 1);
            auxiliaryStack.remove(j - 1);
			auxiliaryStack.remove(j - 2);
        }
    }

    /**
     * Pow reducer：归约求幂运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void powReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* 若操作数不是算术表达式则报错 */
        if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new TypeMismatchedException();
        ArithExpr ae;
        ae = new ArithExpr(Math.pow(((ArithExpr) a).getValue(), ((ArithExpr) b).getValue()));
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(ae);   // 将归约后的操作数放入辅助栈中
    }

    /**
     * Relation reducer: 归约关系运算。
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void relationReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* 若操作数不是算术表达式则报错 */
        if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new TypeMismatchedException();
        BoolExpr be = null;
        switch (o.token.getString()) {
            case "=":
                be = new BoolExpr(((ArithExpr) a).getValue() == ((ArithExpr) b).getValue());
                break;
            case "<>":
                be = new BoolExpr(((ArithExpr) a).getValue() != ((ArithExpr) b).getValue());
                break;
            case "<=":
                be = new BoolExpr(((ArithExpr) a).getValue() <= ((ArithExpr) b).getValue());
                break;
            case "<":
                be = new BoolExpr(((ArithExpr) a).getValue() < ((ArithExpr) b).getValue());
                break;
            case ">=":
                be = new BoolExpr(((ArithExpr) a).getValue() >= ((ArithExpr) b).getValue());
                break;
            case ">":
                be = new BoolExpr(((ArithExpr) a).getValue() > ((ArithExpr) b).getValue());
                break;
        }
		stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(be);   // 将归约后的操作数放入辅助栈中
    }

    /**
     * Rp reducer：归约右括号,这一过程中需要完成预定义函数以及（）的运算
     *
     * @param stack 用于存放操作符的栈
	 * @param auxiliaryStack 用于存放操作数的辅助栈
     * @throws ExpressionException the expression exception
     */
    public void rpReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
        Expr nextop = stack.get(i - 1);  // 获取下一个操作符
		
		/* 若辅助栈内的操作数不足则报错 */
        if (auxiliaryStack.size() < 1)
            throw new MissingOperandException();       
		Expr a = auxiliaryStack.get(j);  // 获取第一个操作数
        /* 如果括号内为布尔表达式,则直接计算相应的值 */
        if (a.getTag() == typeofToken.BoolExpr) {
			if (nextop.getTag() != typeofToken.LP) //如果下一个操作符不是左括号则报错
				throw new TypeMismatchedException();             
			stack.remove(i);
            stack.remove(i - 1);
            return;
			
		/* 如果括号内为ArithList,则需要进行继续合并其他ArithExpr,或者计算min max值 */
        } else if (a.getTag() == typeofToken.ArithExprList) {
			/* 若辅助栈内的操作数不足则报错 */
			if (auxiliaryStack.size() < 2)
				throw new MissingOperandException();
			Expr c = auxiliaryStack.get(j - 1);  // 获取下一个操作数
            if (c.getTag() != typeofToken.ArithExpr)  // 检测下一个操作数是否合法
                throw new TypeMismatchedException();
            ArithExprList ael = new ArithExprList((ArithExpr) c, (ArithExprList) a);  // 若合法则归约为新的ArithExprList
			
			/* 若栈内的操作符不足则报错 */
            if (stack.size() < 4)
				throw new MissingOperatorException();
			
			Expr d = stack.get(i - 2);
			Expr f = stack.get(i - 3);
            ArithExpr ae = null;
			/* 计算min max的值 */
            if (d.getTag() == typeofToken.LP && f.getTag() == typeofToken.FUNC) { // 没有后续其他ArithExpr时
                switch (f.token.getString()) {
                    case "max":
                        ae = new ArithExpr(ael.max);
                        break;
                    case "min":
                        ae = new ArithExpr(ael.min);
                        break;
                    default:
                        throw new FunctionCallException();
                }
                stack.remove(i);
                stack.remove(i - 1);
                stack.remove(i - 2);
                stack.remove(i - 3);
                auxiliaryStack.remove(j);
				auxiliaryStack.remove(j - 1);
                auxiliaryStack.add(ae);
            } else {  // 存在多个ArithExpr时,继续合并其他ArithExpr
                stack.remove(i - 1); // 去掉逗号
                auxiliaryStack.remove(j);
				auxiliaryStack.remove(j - 1);
                auxiliaryStack.add(ael);
            }
            return;
			
		/* 如果括号内为ArithExpr,则需要进行归约或者计算cos sin值,或者直接计算 */
        } else if (a.getTag() == typeofToken.ArithExpr) {
			/* 若栈内的操作符不足则报错 */
            if (stack.size() < 2)
				throw new MissingOperatorException();
            
			/* 如果括号内出现了“,”则将ArithExpr归约为ArithExprList */
			if (nextop.getTag() == typeofToken.COMMA) {
                ArithExprList ael = new ArithExprList((ArithExpr) a);
                auxiliaryStack.set(j, ael);
                return;
			
			/* 否则判断是否需要进行归约sin cos */
            } else if (stack.size() >= 3 && stack.get(i - 2).getTag() == typeofToken.FUNC) {
                ArithExpr ae;
				switch (stack.get(i - 2).token.getString()) {
                    case "sin":
                        ae = new ArithExpr(Math.sin(((ArithExpr) a).getValue()));
                        break;
                    case "cos":
                        ae = new ArithExpr(Math.cos(((ArithExpr) a).getValue()));
                        break;
                    default:
                        throw new MissingOperandException();
                }
                stack.remove(i);
                stack.remove(i - 1);
                stack.remove(i - 2);
                auxiliaryStack.remove(j);				
                auxiliaryStack.add(ae);
                return;
			/* 归约括号运算 */
            } else if(nextop.getTag() == typeofToken.LP){
                // System.out.println(1);
                stack.remove(i);
                stack.remove(i - 1);
                return;
            }
        }
        throw new SyntacticException();
    }
}
