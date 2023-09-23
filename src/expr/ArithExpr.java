package expr;

import token.Scanner;
import token.Decimal;
import token.typeofToken;

/**
 * �����������ʽ.
 */
public class ArithExpr extends Expr {
    /**
     * ��¼�������ʽ�Ľ��ֵ 
     */
	private final double value;

    /**
     * ���캯��1������һ���Զ����Decimal��������
     *
     * @param t ���յ�Decimal��������
     */
	public ArithExpr(Decimal t) {
        super(t);
        tag = typeofToken.ArithExpr;
        value = t.getValue();
    }

    /**
     * ���캯��2������һ��doubleֵ
     *
     * @param t ���յ�doubleֵ
     */
	public ArithExpr(double v) {
        super(null);
        tag = typeofToken.ArithExpr;
        value = v;
    }

    /**
     * ��ȡ�������ʽ��ֵ
     *
     * @return �������ʽ��ֵ
     */
    public double getValue() {
        return value;
    }

    /**
     * �ж��Ƿ����ս��
     *
     * @return �жϽ��
     */
	public boolean isTerminal() {
        return false;
    }

}
