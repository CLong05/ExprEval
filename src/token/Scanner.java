package token;

import decimal.Exponent;
import decimal.Fraction;
import decimal.Integral;
import decimal.Digit;
import exceptions.IllegalDecimalException;
import exceptions.IllegalIdentifierException;
import exceptions.IllegalSymbolException;
import exceptions.LexicalException;

/**
 * �ʷ�������scanner
 */
public class Scanner {
	/**
     * ��¼�����ַ���
     */
    private final String input;
	
	/**
     * ��¼��ǰ���һ��token������
     */
    private int lastTokenTag;
	
	/**
     * ��¼��ǰlookahead������
     */
    private int index;

    /**
     * ���캯��
     *
     * @param input ���ʽ
     */
    public Scanner(String input) {
        this.input = input.toLowerCase();
        lastTokenTag = typeofToken.START;
        index = 0;
    }

    /**
     * ��ȡ��һ��token
     *
     * @return the next token
     * @throws LexicalException the lexical exception
     */
    public Token getNextToken() throws LexicalException {
        String head;
		/* ֱ����ȡ�ǿո�������ַ� */
        while (true) { 
			/* ��ǰδ�ﵽ�����ַ�����βʱ */
            if (!isEnd()) {
                head = input.substring(index, index + 1);
                if (!head.equals(" ")) break; // ��ȡ���ǿո�������ַ�������ѭ��
                index++;  // ��ȡ���ո񣬼�������ȡ�ַ�
            } else {
			/* �����ַ�����β */
                lastTokenTag = typeofToken.DOLLAR; // ���Ϊ$����
                return new Dollar();
            }
        }
		
		/* ������ֵ���� */
        if (Character.isDigit(head.charAt(0))) { // �жϵ�ǰ�ַ��Ƿ�Ϊ����
            Integral integral = null;
            Fraction fraction = null;
            Exponent exponent = null;
            int i = findLastDigitOfString(index);  // ��ȡ��ǰ�������һλ������
            String intStr = input.substring(index, i);
            integral = new Integral(intStr);
            index = i;
            if (!isEnd()) { // δ�����ַ�����β
                head = input.substring(index, index + 1);
            } else {  // �����ַ�����β
                lastTokenTag = typeofToken.NUM;  // ��¼token����
                return new Decimal(integral);	 // ���ɶ�Ӧ��������
            }
			
			/* ����ǰ���ֵ�С������ */
            if (head.equals(".")) {  // �����ǰ���ְ���С������
                index++;
                i = findLastDigitOfString(index);
                if (i == index) {   // С������ֻ��С�����򱨴�
                    throw new IllegalDecimalException();
                }
                intStr = input.substring(index, i);
                fraction = new Fraction(intStr);
                index = i;
                if (!isEnd()) {   // δ�����ַ�����β
                    head = input.substring(index, index + 1);
                } else {  // �����ַ�����β
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, fraction);
                }
            }

            /* ����ǰ���ֵĿ�ѧ���������� */
			if (head.equalsIgnoreCase("e")) {  // �жϵ�ǰ�����Ƿ������ѧ����������
                String op = "+";
                index++;
                if (!isEnd()) { // δ�����ַ�����β
                    head = input.substring(index, index + 1);
                } else {
                    throw new IllegalDecimalException();
                }
                if (!Character.isDigit(head.charAt(0))) {  // �����ѧ�����������������������Ӧ����
                    if (head.equals("+") || head.equals("-")) {
                        op = head.equals("-") ? "-" : "+";
                        index++;
                    } else {
                        throw new IllegalDecimalException();
                    }
                }
                i = findLastDigitOfString(index);  // ��ȡ��ǰ�������һλ������
                if (i == index) {  // �����ֵ����ȱʧ�򱨴�
                    throw new IllegalDecimalException();
                }
                intStr = input.substring(index, i);
                exponent = new Exponent(new Integral(intStr), op); // ��¼token���Ͳ����ɶ�Ӧ��������
                index = i;
            }
			
			/* ���ö�Ӧ�Ĺ��캯����������ֵ���� */
            if (fraction != null && exponent != null) {  // ������ѧ��������С������
                lastTokenTag = typeofToken.NUM;
                return new Decimal(integral, fraction, exponent);
            } else {
                if (exponent != null) {  // ������ѧ������
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, exponent);
                } else if (fraction != null) {   // ����С������
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, fraction);
                } else {    // ֻ������������
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral);
                }
            }
        } else {
		/* ����Boolean�������� */
            if (head.equalsIgnoreCase("t") || head.equalsIgnoreCase("f")) {  // ��������ĸ�ж�ֵ
                if (index + 4 <= input.length() &&          // �ж��Ƿ�Խ��
				 input.substring(index, index + 4).equalsIgnoreCase("true")) {  // true
                    index += 4;
                    lastTokenTag = typeofToken.BOOL;
                    return new Boolean("true");
                } else if (index + 5 <= input.length() &&   // �ж��Ƿ�Խ��
				  input.substring(index, index + 5).equalsIgnoreCase("false")) {  // false
                    index += 5;
                    lastTokenTag = typeofToken.BOOL;
                    return new Boolean("false");
                } else {
                    throw new IllegalIdentifierException();
                }
            }

        /* ����������������ַ���"," �� "$"����Ԥ���庯�� */
			/* ����Ԥ���庯�� */
			if (head.equals("m") || head.equals("s") || head.equals("c")) {  // ��������ĸ�жϺ�������
                if (index + 3 > input.length()) {   // �ж��Ƿ�Խ��
                    throw new IllegalIdentifierException();
                }
                String temp = input.substring(index, index + 3);
                if (temp.equals("min") || temp.equals("max") || temp.equals("sin") || temp.equals("cos")) {
                    index += 3;
                    lastTokenTag = typeofToken.FUNC;
                    return new Operator(temp, typeofToken.FUNC);
                } else {
                    throw new IllegalIdentifierException();
                }
			
			/* �����������-�� */
            } else if (head.equals("-")) {
                index++;
                if (lastTokenTag == typeofToken.NUM || lastTokenTag == typeofToken.RP) {  // ��Ԫ�����
                    lastTokenTag = typeofToken.ADDSUB;
                    return new Operator("-", typeofToken.ADDSUB);
                } else {	// һԪ�����
                    lastTokenTag = typeofToken.NEG;
                    return new Operator("-", typeofToken.NEG);
                }
            
			/* �����������+�� */
			} else if (head.equals("+")) {
                index++;
                lastTokenTag = typeofToken.ADDSUB;
                return new Operator("+", typeofToken.ADDSUB);
            
			/* �����������*������/�� */
			} else if (head.equals("*") || head.equals("/")) {
                index++;
                lastTokenTag = typeofToken.MULDIV;
                return new Operator(head, typeofToken.MULDIV);
            
			/* �����ϵ����� */
			} else if (head.equals("=") || head.equals(">") || head.equals("<")) {
                String temp = "";
                if (index + 2 <= input.length()) {
                    temp = input.substring(index, index + 2);
                }
                if (head.equals("=")) {
                    index++;
                    lastTokenTag = typeofToken.RE;
                    return new Operator("=", typeofToken.RE);
                } else if (head.equals(">")) {
                    if (temp.equals(">=")) {
                        index += 2;
                        lastTokenTag = typeofToken.RE;
                        return new Operator(">=", typeofToken.RE);
                    } else {
                        index++;
                        lastTokenTag = typeofToken.RE;
                        return new Operator(">", typeofToken.RE);
                    }
                } else if (head.equals("<")) {
                    if (temp.equals("<>")) {
                        index += 2;
                        lastTokenTag = typeofToken.RE;
                        return new Operator("<>", typeofToken.RE);
                    }
                    if (temp.equals("<=")) {
                        index += 2;
                        lastTokenTag = typeofToken.RE;
                        return new Operator("<=", typeofToken.RE);
                    } else {
                        index++;
                        lastTokenTag = typeofToken.RE;
                        return new Operator("<", typeofToken.RE);
                    }
                }
			
			/* ���������� */
            } else if (head.equals("!")) {
                index++;
                lastTokenTag = typeofToken.NOT;
                return new Operator("!", typeofToken.NOT);
            
			/* ����������� */
			} else if (head.equals("&")) {
                index++;
                lastTokenTag = typeofToken.AND;
                return new Operator("&", typeofToken.AND);
            
			/* ���������� */
			} else if (head.equals("|")) {
                index++;
                lastTokenTag = typeofToken.OR;
                return new Operator("|", typeofToken.OR);
            
			/* ������Ԫ����� */
			} else if (head.equals("?")) {
                index++;
                lastTokenTag = typeofToken.QM;
                return new Operator("?", typeofToken.QM);
            } else if (head.equals(":")) {
                index++;
                lastTokenTag = typeofToken.COLON;
                return new Operator(":", typeofToken.COLON);
            
			/* �������� */
			} else if (head.equals("(")) {
                index++;
                lastTokenTag = typeofToken.LP;
                return new Operator("(", typeofToken.LP);
            } else if (head.equals(")")) {
                index++;
                lastTokenTag = typeofToken.RP;
                return new Operator(")", typeofToken.RP);
            
			/* ������ */
			} else if (head.equals(",")) {
                index++;
                lastTokenTag = typeofToken.COMMA;
                return new Punctuation(",", typeofToken.COMMA);
            
			/* ������������� */
			} else if (head.equals("^")) {
                index++;
                lastTokenTag = typeofToken.POWER;
                return new Operator("^", typeofToken.POWER);
            
			/* ���������ַ�����Ϊ�Ƿ���������׳��쳣 */
			} else {
                throw new IllegalSymbolException();
            }
        }
        return new Dollar();
    }

    /**
     * �ж��Ƿ񵽴������ַ����Ľ�β
     *
     * @return �жϽ��
     */
	private boolean isEnd() {
        return index >= input.length();
    }
	
	/**
     * ���������ַ����е�ǰ��ȡ���ֵ����һ���ַ���λ������
     *
     * @return λ������
     */
    private int findLastDigitOfString(int start) {
        int i = start;
        while (Character.isDigit(input.charAt(i))) {
            i++;
            if (i == input.length())
                break;
        }
        return i;
    }
}
