package token;

/**
 * �������token�Ľӿ�
 */
public abstract class Token {
    /**
     * Token�����ͣ��ַ�����ʽ��
     */
	public String type;
	
	/**
     * Token���͵ı��
     */
    public int tag;

    /**
     * ���캯��
     */
    public Token() {
        type = "";
    }

    /**
     * ��ȡtoken���͵��ַ�����ʽ
     *
     * @return ����
     */
    public String getType() {
        return type;
    }

    /**
     * ��ȡtoken���͵ı��
     *
     * @return ����
     */
    public int getTag() {
        return tag;
    }

    /**
     * ��ȡtoken��ֵ���ַ�����ʽ
     *
     * @return ֵ���ַ�����ʽ
     */
    public abstract String getString();
}
