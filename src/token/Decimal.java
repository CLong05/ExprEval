package token;

import decimal.Exponent;
import decimal.Fraction;
import decimal.Integral;

/**
 * ����Decimal���͵�token
 */
public class Decimal extends Token {
    /**
	 * ��������
	 */
	Integral integral;
	
	/**
	 * С������
	 */
    Fraction fraction;
    
	/**
	 * ��ѧ����������
	 */
	Exponent exponent;

    /**
     * ���캯��1�������������ֺ�С�������Լ���ѧ������
     */
    public Decimal(Integral i, Fraction f, Exponent e) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = new Fraction(f);
        exponent = new Exponent(e);
    }

    /**
     * ���캯��2�������������ֺͿ�ѧ������
     */
    public Decimal(Integral i, Exponent e) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = null;
        exponent = new Exponent(e);
    }

    /**
     * ���캯��3�������������ֺͿ�ѧ������
     */
    public Decimal(Integral i, Fraction f) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = new Fraction(f);
        exponent = null;
    }

    /**
     * ���캯��4��������������
     */
    public Decimal(Integral i) {
        type = "Decimal";
        tag = typeofToken.NUM;
        integral = new Integral(i);
        fraction = null;
        exponent = null;
    }

    /**
     * ��ȡdecimal����ֵ
     *
     * @return decimal����ֵ
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
     * ��ȡdecimal��ֵ���ַ�����ʽ
     *
     * @return decimal��ֵ���ַ�����ʽ
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
