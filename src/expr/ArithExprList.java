package expr;

import token.Scanner;
import token.typeofToken;


/**
 * 定义算术表达式列表
 */
public class ArithExprList extends Expr {
    /**
     * 此时列表中的最大值和最小值
     */
    public double max, min;

    /**
     * 构造函数1：依据BNF（ArithExprList -> ArithExpr）接收一个ArithExpr
     *
     * @param ae ArithExpr对象
     */
    public ArithExprList(ArithExpr ae) {
        tag = typeofToken.ArithExprList;
        max = min = ae.getValue();
    }

    /**
     * 构造函数2：依据BNF（ArithExprList -> ArithExpr , ArithExprList）接收一个ArithExpr和一个ArithExprList
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
     * 判断是否是终结符
     *
     * @return 判断结果
     */
	public boolean isTerminal() {
        return false;
    }
}
