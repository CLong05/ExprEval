package expr;

import token.Scanner;
import token.Boolean;
import token.typeofToken;

/**
 * 定义布尔表达式
 */
public class BoolExpr extends Expr {
    /**
	 * 记录该布尔表达式的结果
	 */
	private final boolean value;

    /**
     * 构造函数1：接收一个自定义的Boolean类型数据
     *
     * @param t 接收的Boolean类型数据
     */
    public BoolExpr(Boolean t) {
        super(t);
        tag = typeofToken.BoolExpr;
        value = t.getValue();
    }

    /**
     * 构造函数2：接收一个布尔值
     *
     * @param v 布尔值
     */
    public BoolExpr(boolean v) {
        super(null);
        tag = typeofToken.BoolExpr;
        value = v;
    }

    /**
     * 获取布尔表达式的值
     *
     * @return 布尔表达式的值
     */
    public boolean getValue() {
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
