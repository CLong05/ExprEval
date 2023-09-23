package decimal;

/**
 * Digit的正则定义式：digit->0|1|2|3|4|5|6|7|8|9
 */
public class Digit{
    /**
     * 记录数字值
     */
    private final int value;

    /**
     * 构造函数：接收数字的字符串形式，生成int形式的数值
     *
     * @param str 数字的字符串形式
     */
    public Digit(String str) {
        value = Integer.parseInt(str);
    }

    /**
     * 获取数字的数值
     *
     * @return 数值
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取数字的字符串形式
     *
     * @return 数字的字符串形式
     */
	public String getString() {
        return String.valueOf(value);
    }

}
