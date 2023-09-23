package token;


/**
 * 定义Punctuation类型的token
 */
public class Punctuation extends Token {
    /**
     * 记录Punctuation
     */
	private final String punctuation;

    /**
     * 构造函数
     */
    public Punctuation(String punctuation, int tag) {
        this.tag = tag;
        this.punctuation = punctuation;
    }

    /**
     * 获取token的字符串形式
     *
     * @return 字符串形式
     */
	public String getString() {
        return punctuation;
    }

}
