package decimal;

/**
 * Digit��������ʽ��digit->0|1|2|3|4|5|6|7|8|9
 */
public class Digit{
    /**
     * ��¼����ֵ
     */
    private final int value;

    /**
     * ���캯�����������ֵ��ַ�����ʽ������int��ʽ����ֵ
     *
     * @param str ���ֵ��ַ�����ʽ
     */
    public Digit(String str) {
        value = Integer.parseInt(str);
    }

    /**
     * ��ȡ���ֵ���ֵ
     *
     * @return ��ֵ
     */
    public int getValue() {
        return value;
    }

    /**
     * ��ȡ���ֵ��ַ�����ʽ
     *
     * @return ���ֵ��ַ�����ʽ
     */
	public String getString() {
        return String.valueOf(value);
    }

}
