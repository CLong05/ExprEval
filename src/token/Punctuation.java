package token;


/**
 * ����Punctuation���͵�token
 */
public class Punctuation extends Token {
    /**
     * ��¼Punctuation
     */
	private final String punctuation;

    /**
     * ���캯��
     */
    public Punctuation(String punctuation, int tag) {
        this.tag = tag;
        this.punctuation = punctuation;
    }

    /**
     * ��ȡtoken���ַ�����ʽ
     *
     * @return �ַ�����ʽ
     */
	public String getString() {
        return punctuation;
    }

}
