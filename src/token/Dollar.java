package token;


/**
 * ����Dollar���͵�token
 */
public class Dollar extends Token {
    /**
     * ���캯��
     */
    public Dollar() {
        type = "$";
        tag = typeofToken.DOLLAR;
    }

    /**
     * ��ȡtoken���ַ�����ʽ
     *
     * @return �ַ�����ʽ
     */
	public String getString() {
        return type;
    }
}
