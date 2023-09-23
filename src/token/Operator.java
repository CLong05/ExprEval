package token;


/**
 * ����Operator���͵�token
 */
public class Operator extends Token {
    /**
     * ��¼Operator
     */
	private final String operator;

    /**
     * ���캯��
     */
    public Operator(String operator, int tag) {
        type = "Operator";
        this.operator = operator;
        this.tag = tag;
    }

    /**
     * ��ȡtoken���ַ�����ʽ
     *
     * @return �ַ�����ʽ
     */
	public String getString() {
        return operator;
    }
}
