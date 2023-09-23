package token;


/**
 * 定义Operator类型的token
 */
public class Operator extends Token {
    /**
     * 记录Operator
     */
	private final String operator;

    /**
     * 构造函数
     */
    public Operator(String operator, int tag) {
        type = "Operator";
        this.operator = operator;
        this.tag = tag;
    }

    /**
     * 获取token的字符串形式
     *
     * @return 字符串形式
     */
	public String getString() {
        return operator;
    }
}
