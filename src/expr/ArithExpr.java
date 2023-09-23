package expr;

import token.Scanner;
import token.Decimal;
import token.typeofToken;

/**
 * 定义算术表达式.
 */
public class ArithExpr extends Expr {
    /**
     * 记录算术表达式的结果值 
     */
	private final double value;

    /**
     * 构造函数1：接收一个自定义的Decimal类型数据
     *
     * @param t 接收的Decimal类型数据
     */
	public ArithExpr(Decimal t) {
        super(t);
        tag = typeofToken.ArithExpr;
        value = t.getValue();
    }

    /**
     * 构造函数2：接收一个double值
     *
     * @param t 接收的double值
     */
	public ArithExpr(double v) {
        super(null);
        tag = typeofToken.ArithExpr;
        value = v;
    }

    /**
     * 获取算术表达式的值
     *
     * @return 算术表达式的值
     */
    public double getValue() {
        return value;
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
