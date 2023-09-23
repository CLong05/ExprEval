package token;


/**
 * 定义Dollar类型的token
 */
public class Dollar extends Token {
    /**
     * 构造函数
     */
    public Dollar() {
        type = "$";
        tag = typeofToken.DOLLAR;
    }

    /**
     * 获取token的字符串形式
     *
     * @return 字符串形式
     */
	public String getString() {
        return type;
    }
}
