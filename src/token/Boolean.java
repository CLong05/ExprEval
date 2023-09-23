package token;

/**
 * ����Boolean���͵�token
 */
public class Boolean extends Token {
    /**
     * ��¼����ֵ
     */
    private final boolean value;

    /**
     * ���캯���������ַ�����ʽ��bool
     *
     * @param str �ַ���
     */
    public Boolean(String str) {
        type = "bool";
        tag = typeofToken.BOOL;
        value = str.equalsIgnoreCase("true");
    }

    /**
     * ��ȡbooleanֵ���ַ�����ʽ
     *
     * @return �ַ���
     */
	public String getString() {
        return value ? "true" : "false";
    }

    /**
     * ��ȡbooleanֵ
     *
     * @return booleanֵ
     */
    public boolean getValue() {
        return value;
    }
}
