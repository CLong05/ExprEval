package token;

/**
 * 定义各种token的接口
 */
public abstract class Token {
    /**
     * Token的类型（字符串形式）
     */
	public String type;
	
	/**
     * Token类型的编号
     */
    public int tag;

    /**
     * 构造函数
     */
    public Token() {
        type = "";
    }

    /**
     * 获取token类型的字符串形式
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取token类型的编号
     *
     * @return 种类
     */
    public int getTag() {
        return tag;
    }

    /**
     * 获取token的值的字符串形式
     *
     * @return 值的字符串形式
     */
    public abstract String getString();
}
