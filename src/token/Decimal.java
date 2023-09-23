package token;

import decimal.Exponent;
import decimal.Fraction;
import decimal.Integral;

/**
 * 定义Decimal类型的token
 */
public class Decimal extends Token {
    /**
	 * 整数部分
	 */
	Integral integral;
	
	/**
	 * 小数部分
	 */
    Fraction fraction;
    
	/**
	 * 科学计数法部分
	 */
	Exponent exponent;

    /**
     * 构造函数1：接收整数部分和小数部分以及科学计数法
     */
    public Decimal(Integral i, Fraction f, Exponent e) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = new Fraction(f);
        exponent = new Exponent(e);
    }

    /**
     * 构造函数2：接收整数部分和科学计数法
     */
    public Decimal(Integral i, Exponent e) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = null;
        exponent = new Exponent(e);
    }

    /**
     * 构造函数3：接收整数部分和科学计数法
     */
    public Decimal(Integral i, Fraction f) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = new Fraction(f);
        exponent = null;
    }

    /**
     * 构造函数4：接收整数部分
     */
    public Decimal(Integral i) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = null;
        exponent = null;
    }

    /**
     * 获取decimal的数值
     *
     * @return decimal的数值
     */
    public double getValue() {
        double a = integral.getValue();
        if (fraction != null)
            a += fraction.getValue();
        if (exponent != null)
            a *= Math.pow(10, exponent.getValue());
        return a;
    }

    /**
     * 获取decimal数值的字符串形式
     *
     * @return decimal数值的字符串形式
     */
	public String getString() {
        String a = integral.getString();
        if (fraction != null)
            a += fraction.getString();
        if (exponent != null)
            a += exponent.getString();
        return a;
    }
}
