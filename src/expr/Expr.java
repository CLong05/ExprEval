package expr;

import token.Scanner;
import token.Token;
import token.typeofToken;

/**
 * ���ֱ��ʽ���͵Ľӿ�
 */
public abstract class Expr {

    public Token token;
    public int tag;

    /**
     * ���캯��1������һ��token
     *
     * @param t ���յ�token
     */
    public Expr(Token t) {
        token = t;
        tag = typeofToken.Expr;
    }

    /**
     * ���캯��2�������ղ���
     */
    public Expr() {
        token = null;
        tag = typeofToken.NULL;
    }

    /**
     * ��ȡExpr��Ӧ��tag.
     *
     * @return the tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * �ж��Ƿ����ս��
     *
     * @return �жϽ��
     */
    public abstract boolean isTerminal();
}
