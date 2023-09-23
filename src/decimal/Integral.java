package decimal;

import java.util.ArrayList;

/**
 * Integral的正则定义式：integral->digit+
 */
public class Integral{
    /**
     * 记录数字列表
     */
    private ArrayList<Digit> integral = new ArrayList<>();

    /**
     * 构造函数：接收数字的Integral形式，生成Integral形式的数值
     *
     * @param a 另一个数
     */
    public Integral(Integral a) {
        integral = a.integral;
    }

    /**
     * 构造函数：接收数字的字符串形式，生成Integral形式的数值
     *
     * @param str 字符串形式
     */
    public Integral(String str) {
        for (int i = 0; i < str.length(); i++) {
            integral.add(new Digit(String.valueOf(str.charAt(i))));
        }
    }

    /**
     * 获取数字double形式的数值
     *
     * @return 数字的数值
     */
    public double getValue() {
        double value = 0, j = 1;
        for (int i = integral.size() - 1; i >= 0; i--, j *= 10) {
            value += integral.get(i).getValue() * j;
        }
        return value;
    }

    /**
     * 获取数字的字符串形式
     *
     * @return 数字的字符串形式
     */
	public String getString() {
        String s = "";
        for (Digit digit : integral) {
            s += digit.getString();
        }
        return s;
    }

    /**
     * 获取数字的长度
     *
     * @return 数字的长度
     */
    public int length() {
        return integral.size();
    }
}
