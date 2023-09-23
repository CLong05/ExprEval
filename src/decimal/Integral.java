package decimal;

import java.util.ArrayList;

/**
 * Integral��������ʽ��integral->digit+
 */
public class Integral{
    /**
     * ��¼�����б�
     */
    private ArrayList<Digit> integral = new ArrayList<>();

    /**
     * ���캯�����������ֵ�Integral��ʽ������Integral��ʽ����ֵ
     *
     * @param a ��һ����
     */
    public Integral(Integral a) {
        integral = a.integral;
    }

    /**
     * ���캯�����������ֵ��ַ�����ʽ������Integral��ʽ����ֵ
     *
     * @param str �ַ�����ʽ
     */
    public Integral(String str) {
        for (int i = 0; i < str.length(); i++) {
            integral.add(new Digit(String.valueOf(str.charAt(i))));
        }
    }

    /**
     * ��ȡ����double��ʽ����ֵ
     *
     * @return ���ֵ���ֵ
     */
    public double getValue() {
        double value = 0, j = 1;
        for (int i = integral.size() - 1; i >= 0; i--, j *= 10) {
            value += integral.get(i).getValue() * j;
        }
        return value;
    }

    /**
     * ��ȡ���ֵ��ַ�����ʽ
     *
     * @return ���ֵ��ַ�����ʽ
     */
	public String getString() {
        String s = "";
        for (Digit digit : integral) {
            s += digit.getString();
        }
        return s;
    }

    /**
     * ��ȡ���ֵĳ���
     *
     * @return ���ֵĳ���
     */
    public int length() {
        return integral.size();
    }
}
