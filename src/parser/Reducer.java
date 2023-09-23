package parser;

import exceptions.*;
import expr.*;
import token.Boolean;
import token.Decimal;
import token.Scanner;
import token.typeofToken;

import java.util.ArrayList;

/**
 * ִ�й�Լ����
 */
public class Reducer {
    /**
     * Num reducer: ��Լ������-���Ĳ�������
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     */
    public void numReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) {
        int i = stack.size() - 1;
        Terminal k = (Terminal) stack.get(i);
        ArithExpr arithExpr = new ArithExpr((Decimal) k.token);
        stack.remove(i);  // �Ӳ�����ջ���Ƴ��ò�����
        auxiliaryStack.add(arithExpr);  // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Bool reducer����Լ�����������ݡ�
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void boolReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
        Terminal t = (Terminal) stack.get(i);
        BoolExpr b = new BoolExpr((Boolean) t.token);
        stack.remove(i);  // �Ӳ�����ջ���Ƴ��ò�����
        auxiliaryStack.add(b);  // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Neg reducer����Լ����һԪ������ġ�-���Ĳ�������
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void negReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = auxiliaryStack.size() - 1;
        Expr k = auxiliaryStack.get(i);
        //if (k.getTag() != typeofToken.ArithExpr)
        //    throw new TypeMismatchedException();
        double v = ((Decimal) k.token).getValue();
        ArithExpr arithExpr = new ArithExpr(-v);  // ��Ϊ��ֵ
        stack.remove(stack.size()-1);	   // �Ӳ�����ջ���Ƴ���-��
		auxiliaryStack.remove(i);
        auxiliaryStack.add(arithExpr);  // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Not reducer����Լ�����㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void notReducer(ArrayList<Expr> stack, ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = auxiliaryStack.size() - 1;
        Expr a = auxiliaryStack.get(i);
        if (a.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
        BoolExpr be = new BoolExpr(!((BoolExpr) a).getValue());
		stack.remove(stack.size()-1);	    // �Ӳ�����ջ���Ƴ�"!"
		auxiliaryStack.remove(i);			// �Ӳ�����ջ���Ƴ��ò�����  
        auxiliaryStack.add(be);  // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Add sub reducer����Լ�Ӽ����㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void add_subReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
		Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* �����ֲ������ʽ�򱨴� */
        if (a.getTag() == typeofToken.BoolExpr || b.getTag() == typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* �������������������ʽ�򱨴� */
        else if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new MissingOperandException();
		ArithExpr arithExpr;
        if (o.token.getString().equals("+"))  // �ӷ�
            arithExpr = new ArithExpr(((ArithExpr) a).getValue() + ((ArithExpr) b).getValue());
        else  								  // ����
            arithExpr = new ArithExpr(((ArithExpr) a).getValue() - ((ArithExpr) b).getValue());
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(arithExpr);   // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * And or reducer����Լ��������
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void and_orReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* ������ջ�ڵĲ����������򱨴� */
		if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(i);
		/* �����������ǲ������ʽ�򱨴� */
        if (a.getTag() != typeofToken.BoolExpr || b.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		
		BoolExpr be;
        if (o.getTag() == typeofToken.AND)  // &����
            be = new BoolExpr(((BoolExpr) a).getValue() && ((BoolExpr) b).getValue());
        else							   // |����
            be = new BoolExpr(((BoolExpr) a).getValue() || ((BoolExpr) b).getValue());
        stack.remove(i);
		auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);       
        auxiliaryStack.add(be);  // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Mul div reducer����Լ�˳����㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void mul_divReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {		
		int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
		Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* �����ֲ������ʽ�򱨴� */
        if (a.getTag() == typeofToken.BoolExpr || b.getTag() == typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* �������������������ʽ�򱨴� */
        else if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new MissingOperandException();
		ArithExpr ae;
        if (o.token.getString().equals("*"))  // �˷�
            ae = new ArithExpr(((ArithExpr) a).getValue() * ((ArithExpr) b).getValue());
        else if (((ArithExpr) b).getValue() == 0)  // ����0
            throw new DividedByZeroException();
        else  // ����
            ae = new ArithExpr(((ArithExpr) a).getValue() / ((ArithExpr) b).getValue());
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(ae);   // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Colon reducer�� ����������Լ��Ԫ���㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void colonReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;

		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 3 || stack.size() < 2)
            throw new MissingOperandException();

		Expr a = auxiliaryStack.get(j);
        Expr b = auxiliaryStack.get(j - 1);
		Expr c = stack.get(i - 1);  // ?��
        Expr d = auxiliaryStack.get(j - 2);
				
        /* �������ֵı��ʽ���Ͳ�ƥ���򱨴� */
		if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr || d.getTag() != typeofToken.BoolExpr)
            throw new TypeMismatchedException();
		/* ����������ǰһ�����������ǡ������򱨴� */
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
     * Pow reducer����Լ�������㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void powReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* �������������������ʽ�򱨴� */
        if (a.getTag() != typeofToken.ArithExpr || b.getTag() != typeofToken.ArithExpr)
            throw new TypeMismatchedException();
        ArithExpr ae;
        ae = new ArithExpr(Math.pow(((ArithExpr) a).getValue(), ((ArithExpr) b).getValue()));
        stack.remove(i);
        auxiliaryStack.remove(j);
        auxiliaryStack.remove(j - 1);
        auxiliaryStack.add(ae);   // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Relation reducer: ��Լ��ϵ���㡣
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void relationReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 2)
            throw new MissingOperandException();
        Expr a = auxiliaryStack.get(j - 1);
        Expr o = stack.get(i);
        Expr b = auxiliaryStack.get(j);
		/* �������������������ʽ�򱨴� */
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
        auxiliaryStack.add(be);   // ����Լ��Ĳ��������븨��ջ��
    }

    /**
     * Rp reducer����Լ������,��һ��������Ҫ���Ԥ���庯���Լ�����������
     *
     * @param stack ���ڴ�Ų�������ջ
	 * @param auxiliaryStack ���ڴ�Ų������ĸ���ջ
     * @throws ExpressionException the expression exception
     */
    public void rpReducer(ArrayList<Expr> stack,ArrayList<Expr> auxiliaryStack) throws ExpressionException {
        int i = stack.size() - 1;
		int j = auxiliaryStack.size() - 1;
        Expr nextop = stack.get(i - 1);  // ��ȡ��һ��������
		
		/* ������ջ�ڵĲ����������򱨴� */
        if (auxiliaryStack.size() < 1)
            throw new MissingOperandException();       
		Expr a = auxiliaryStack.get(j);  // ��ȡ��һ��������
        /* ���������Ϊ�������ʽ,��ֱ�Ӽ�����Ӧ��ֵ */
        if (a.getTag() == typeofToken.BoolExpr) {
			if (nextop.getTag() != typeofToken.LP) //�����һ�������������������򱨴�
				throw new TypeMismatchedException();             
			stack.remove(i);
            stack.remove(i - 1);
            return;
			
		/* ���������ΪArithList,����Ҫ���м����ϲ�����ArithExpr,���߼���min maxֵ */
        } else if (a.getTag() == typeofToken.ArithExprList) {
			/* ������ջ�ڵĲ����������򱨴� */
			if (auxiliaryStack.size() < 2)
				throw new MissingOperandException();
			Expr c = auxiliaryStack.get(j - 1);  // ��ȡ��һ��������
            if (c.getTag() != typeofToken.ArithExpr)  // �����һ���������Ƿ�Ϸ�
                throw new TypeMismatchedException();
            ArithExprList ael = new ArithExprList((ArithExpr) c, (ArithExprList) a);  // ���Ϸ����ԼΪ�µ�ArithExprList
			
			/* ��ջ�ڵĲ����������򱨴� */
            if (stack.size() < 4)
				throw new MissingOperatorException();
			
			Expr d = stack.get(i - 2);
			Expr f = stack.get(i - 3);
            ArithExpr ae = null;
			/* ����min max��ֵ */
            if (d.getTag() == typeofToken.LP && f.getTag() == typeofToken.FUNC) { // û�к�������ArithExprʱ
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
            } else {  // ���ڶ��ArithExprʱ,�����ϲ�����ArithExpr
                stack.remove(i - 1); // ȥ������
                auxiliaryStack.remove(j);
				auxiliaryStack.remove(j - 1);
                auxiliaryStack.add(ael);
            }
            return;
			
		/* ���������ΪArithExpr,����Ҫ���й�Լ���߼���cos sinֵ,����ֱ�Ӽ��� */
        } else if (a.getTag() == typeofToken.ArithExpr) {
			/* ��ջ�ڵĲ����������򱨴� */
            if (stack.size() < 2)
				throw new MissingOperatorException();
            
			/* ��������ڳ����ˡ�,����ArithExpr��ԼΪArithExprList */
			if (nextop.getTag() == typeofToken.COMMA) {
                ArithExprList ael = new ArithExprList((ArithExpr) a);
                auxiliaryStack.set(j, ael);
                return;
			
			/* �����ж��Ƿ���Ҫ���й�Լsin cos */
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
			/* ��Լ�������� */
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
