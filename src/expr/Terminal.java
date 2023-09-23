package expr;

import token.Token;


/**
 * 定义终结符
 */
public class Terminal extends Expr {

    /**
     * 构造函数
     *
     * @param t 接收的token
     */
    public Terminal(Token t) {
        super(t);
        tag = t.getTag();
    }

    /**
     * 判断是否是终结符
     *
     * @return 判断结果
     */
	public boolean isTerminal() {
        return true;
    }

}
