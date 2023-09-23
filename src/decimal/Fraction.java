package decimal;

/**
 * Fraction的正则定义式：fraction->.integral
 */
public class Fraction{
    /**
     * 记录小数部分数字的列表
     */
	public final Integral integral;

    /**
     * 构造函数：接收数字的字符串形式，生成Integral形式的数值
     *
     * @param s 数字字符串
     */
    public Fraction(String s) {
        integral = new Integral(s);
    }

    /**
     * 构造函数：接收另一个数字的小数部分，生成Integral形式的数值
     *
     * @param other 另一个小数部分
     */
    public Fraction(Fraction other) {
        integral = other.integral;
    }

    /**
     * 获取小数部分的数值
     *
     * @return 小数部分的数值
     */
    public double getValue() {
        double length = integral.length();
        return Math.pow(0.1, length) * integral.getValue();
    }

    /**
     * 获取小数部分的字符串形式，包含小数点
     *
     * @return 小数部分的字符串形式
     */
	public String getString() {
		String dot = ".";
        return dot + integral.getString();
    }
}
