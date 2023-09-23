package decimal;

/**
 * Fraction��������ʽ��fraction->.integral
 */
public class Fraction{
    /**
     * ��¼С���������ֵ��б�
     */
	public final Integral integral;

    /**
     * ���캯�����������ֵ��ַ�����ʽ������Integral��ʽ����ֵ
     *
     * @param s �����ַ���
     */
    public Fraction(String s) {
        integral = new Integral(s);
    }

    /**
     * ���캯����������һ�����ֵ�С�����֣�����Integral��ʽ����ֵ
     *
     * @param other ��һ��С������
     */
    public Fraction(Fraction other) {
        integral = other.integral;
    }

    /**
     * ��ȡС�����ֵ���ֵ
     *
     * @return С�����ֵ���ֵ
     */
    public double getValue() {
        double length = integral.length();
        return Math.pow(0.1, length) * integral.getValue();
    }

    /**
     * ��ȡС�����ֵ��ַ�����ʽ������С����
     *
     * @return С�����ֵ��ַ�����ʽ
     */
	public String getString() {
		String dot = ".";
        return dot + integral.getString();
    }
}
