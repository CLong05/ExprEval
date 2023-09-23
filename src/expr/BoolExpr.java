package expr;

import token.Scanner;
import token.Boolean;
import token.typeofToken;

/**
 * ���岼�����ʽ
 */
public class BoolExpr extends Expr {
    /**
	 * ��¼�ò������ʽ�Ľ��
	 */
	private final boolean value;

    /**
     * ���캯��1������һ���Զ����Boolean��������
     *
     * @param t ���յ�Boolean��������
     */
    public BoolExpr(Boolean t) {
        super(t);
        tag = typeofToken.BoolExpr;
        value = t.getValue();
    }

    /**
     * ���캯��2������һ������ֵ
     *
     * @param v ����ֵ
     */
    public BoolExpr(boolean v) {
        super(null);
        tag = typeofToken.BoolExpr;
        value = v;
    }

    /**
     * ��ȡ�������ʽ��ֵ
     *
     * @return �������ʽ��ֵ
     */
    public boolean getValue() {
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
