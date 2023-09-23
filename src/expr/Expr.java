package expr;

import token.Scanner;
import token.Token;
import token.typeofToken;

/**
 * 各种表达式类型的接口
 */
public abstract class Expr {

    public Token token;
    public int tag;

    /**
     * 构造函数1：接收一个token
     *
     * @param t 接收的token
     */
    public Expr(Token t) {
        token = t;
        tag = typeofToken.Expr;
    }

    /**
     * 构造函数2：不接收参数
     */
    public Expr() {
        token = null;
        tag = typeofToken.NULL;
    }

    /**
     * 获取Expr对应的tag.
     *
     * @return the tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * 判断是否是终结符
     *
     * @return 判断结果
     */
    public abstract boolean isTerminal();
}
