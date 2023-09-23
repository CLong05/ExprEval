package decimal;

/**
 * Exponent��������ʽ��exponent->(E|e)(+|�C|epsilon)integral
 */
public class Exponent{
    /**
     * ��¼��ѧ�����������ֲ���
     */
    private final Integral integral;
    
	/**
     * +|�C|epsilon
     */
    private final String a;

    /**
     * ���캯�����������ֵ�Integral��ʽ����ʱ�ķ���Ĭ��Ϊ+
     *
     * @param integral ���ֲ���
     * @param op       +|�C|epsilon
     */
    public Exponent(Integral integral) {
        this.integral = integral;
        this.a = "+";
    }
	
	/**
     * ���캯�����������ֵ�Integral��ʽ�Լ�������
     *
     * @param integral ���ֲ���
     * @param op       +|�C|epsilon
     */
    public Exponent(Integral integral, String op) {
        this.integral = integral;
        this.a = op;
    }

    /**
     * ���캯����������һ��exponent
     *
     * @param other ��һ��exponent
     */
    public Exponent(Exponent other) {
        integral = other.integral;
        a = other.a;
    }

    /**
     * ��ȡ��ѧ����������ֵ
     *
     * @return ��ѧ����������ֵ
     */
    public double getValue() {
        return a.equals("-") ? -integral.getValue() : integral.getValue();
    }

    /**
     * ��ȡ��ѧ���������ַ�����ʽ
     *
     * @return ��ѧ���������ַ�����ʽ
     */
	public String getString() {
        String e = "e";
		return e + a + integral.getString();
    }
}
