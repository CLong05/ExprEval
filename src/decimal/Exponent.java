package decimal;

/**
 * Exponent的正则定义式：exponent->(E|e)(+|–|epsilon)integral
 */
public class Exponent{
    /**
     * 记录科学计数法的数字部分
     */
    private final Integral integral;
    
	/**
     * +|–|epsilon
     */
    private final String a;

    /**
     * 构造函数：接收数字的Integral形式，此时的符号默认为+
     *
     * @param integral 数字部分
     * @param op       +|–|epsilon
     */
    public Exponent(Integral integral) {
        this.integral = integral;
        this.a = "+";
    }
	
	/**
     * 构造函数：接收数字的Integral形式以及正负号
     *
     * @param integral 数字部分
     * @param op       +|–|epsilon
     */
    public Exponent(Integral integral, String op) {
        this.integral = integral;
        this.a = op;
    }

    /**
     * 构造函数：接收另一个exponent
     *
     * @param other 另一个exponent
     */
    public Exponent(Exponent other) {
        integral = other.integral;
        a = other.a;
    }

    /**
     * 获取科学计数法的数值
     *
     * @return 科学计数法的数值
     */
    public double getValue() {
        return a.equals("-") ? -integral.getValue() : integral.getValue();
    }

    /**
     * 获取科学计数法的字符串形式
     *
     * @return 科学计数法的字符串形式
     */
	public String getString() {
        String e = "e";
		return e + a + integral.getString();
    }
}
