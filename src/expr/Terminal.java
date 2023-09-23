package expr;

import token.Token;


/**
 * �����ս��
 */
public class Terminal extends Expr {

    /**
     * ���캯��
     *
     * @param t ���յ�token
     */
    public Terminal(Token t) {
        super(t);
        tag = t.getTag();
    }

    /**
     * �ж��Ƿ����ս��
     *
     * @return �жϽ��
     */
	public boolean isTerminal() {
        return true;
    }

}
