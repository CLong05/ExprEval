package expr;

import token.Scanner;
import token.typeofToken;


/**
 * �����������ʽ�б�
 */
public class ArithExprList extends Expr {
    /**
     * ��ʱ�б��е����ֵ����Сֵ
     */
    public double max, min;

    /**
     * ���캯��1������BNF��ArithExprList -> ArithExpr������һ��ArithExpr
     *
     * @param ae ArithExpr����
     */
    public ArithExprList(ArithExpr ae) {
        tag = typeofToken.ArithExprList;
        max = min = ae.getValue();
    }

    /**
     * ���캯��2������BNF��ArithExprList -> ArithExpr , ArithExprList������һ��ArithExpr��һ��ArithExprList
     *
     * @param ae  ArithExpr
     * @param ael ArithExprList
     */
    public ArithExprList(ArithExpr ae, ArithExprList ael) {
        tag = typeofToken.ArithExprList;
        double a = ae.getValue();
        max = Math.max(a, ael.max);
        min = Math.min(a, ael.min);
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
