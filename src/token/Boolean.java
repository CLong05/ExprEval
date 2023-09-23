package token;

/**
 * 定义Boolean类型的token
 */
public class Boolean extends Token {
    /**
     * 记录布尔值
     */
    private final boolean value;

    /**
     * 构造函数：接收字符串形式的bool
     *
     * @param str 字符串
     */
    public Boolean(String str) {
        type = "bool";
        tag = typeofToken.BOOL;
        value = str.equalsIgnoreCase("true");
    }

    /**
     * 获取boolean值的字符串形式
     *
     * @return 字符串
     */
	public String getString() {
        return value ? "true" : "false";
    }

    /**
     * 获取boolean值
     *
     * @return boolean值
     */
    public boolean getValue() {
        return value;
    }
}
