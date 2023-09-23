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
 * 词法分析器scanner
 */
public class Scanner {
	/**
     * 记录输入字符串
     */
    private final String input;
	
	/**
     * 记录当前最后一个token的类型
     */
    private int lastTokenTag;
	
	/**
     * 记录当前lookahead的索引
     */
    private int index;

    /**
     * 构造函数
     *
     * @param input 表达式
     */
    public Scanner(String input) {
        this.input = input.toLowerCase();
        lastTokenTag = typeofToken.START;
        index = 0;
    }

    /**
     * 获取下一个token
     *
     * @return the next token
     * @throws LexicalException the lexical exception
     */
    public Token getNextToken() throws LexicalException {
        String head;
		/* 直到读取非空格的输入字符 */
        while (true) { 
			/* 当前未达到输入字符串结尾时 */
            if (!isEnd()) {
                head = input.substring(index, index + 1);
                if (!head.equals(" ")) break; // 读取到非空格的输入字符，跳出循环
                index++;  // 读取到空格，继续向后读取字符
            } else {
			/* 到达字符串结尾 */
                lastTokenTag = typeofToken.DOLLAR; // 标记为$符号
                return new Dollar();
            }
        }
		
		/* 处理数值类型 */
        if (Character.isDigit(head.charAt(0))) { // 判断当前字符是否为数字
            Integral integral = null;
            Fraction fraction = null;
            Exponent exponent = null;
            int i = findLastDigitOfString(index);  // 获取当前数字最后一位的索引
            String intStr = input.substring(index, i);
            integral = new Integral(intStr);
            index = i;
            if (!isEnd()) { // 未到达字符串结尾
                head = input.substring(index, index + 1);
            } else {  // 到达字符串结尾
                lastTokenTag = typeofToken.NUM;  // 记录token类型
                return new Decimal(integral);	 // 生成对应类型数据
            }
			
			/* 处理当前数字的小数部分 */
            if (head.equals(".")) {  // 如果当前数字包含小数部分
                index++;
                i = findLastDigitOfString(index);
                if (i == index) {   // 小数部分只有小数点则报错
                    throw new IllegalDecimalException();
                }
                intStr = input.substring(index, i);
                fraction = new Fraction(intStr);
                index = i;
                if (!isEnd()) {   // 未到达字符串结尾
                    head = input.substring(index, index + 1);
                } else {  // 到达字符串结尾
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, fraction);
                }
            }

            /* 处理当前数字的科学计数法部分 */
			if (head.equalsIgnoreCase("e")) {  // 判断当前数字是否包含科学计数法部分
                String op = "+";
                index++;
                if (!isEnd()) { // 未到达字符串结尾
                    head = input.substring(index, index + 1);
                } else {
                    throw new IllegalDecimalException();
                }
                if (!Character.isDigit(head.charAt(0))) {  // 如果科学计数法包含正负号则进行相应处理
                    if (head.equals("+") || head.equals("-")) {
                        op = head.equals("-") ? "-" : "+";
                        index++;
                    } else {
                        throw new IllegalDecimalException();
                    }
                }
                i = findLastDigitOfString(index);  // 获取当前数字最后一位的索引
                if (i == index) {  // 如果数值部分缺失则报错
                    throw new IllegalDecimalException();
                }
                intStr = input.substring(index, i);
                exponent = new Exponent(new Integral(intStr), op); // 记录token类型并生成对应数据类型
                index = i;
            }
			
			/* 调用对应的构造函数，构造数值类型 */
            if (fraction != null && exponent != null) {  // 包含科学计数法和小数部分
                lastTokenTag = typeofToken.NUM;
                return new Decimal(integral, fraction, exponent);
            } else {
                if (exponent != null) {  // 包含科学计数法
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, exponent);
                } else if (fraction != null) {   // 包含小数部分
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral, fraction);
                } else {    // 只包含整数部分
                    lastTokenTag = typeofToken.NUM;
                    return new Decimal(integral);
                }
            }
        } else {
		/* 处理Boolean类型数据 */
            if (head.equalsIgnoreCase("t") || head.equalsIgnoreCase("f")) {  // 根据首字母判断值
                if (index + 4 <= input.length() &&          // 判断是否越界
				 input.substring(index, index + 4).equalsIgnoreCase("true")) {  // true
                    index += 4;
                    lastTokenTag = typeofToken.BOOL;
                    return new Boolean("true");
                } else if (index + 5 <= input.length() &&   // 判断是否越界
				  input.substring(index, index + 5).equalsIgnoreCase("false")) {  // false
                    index += 5;
                    lastTokenTag = typeofToken.BOOL;
                    return new Boolean("false");
                } else {
                    throw new IllegalIdentifierException();
                }
            }

        /* 处理运算符、辅助字符（"," 和 "$"）和预定义函数 */
			/* 处理预定义函数 */
			if (head.equals("m") || head.equals("s") || head.equals("c")) {  // 根据首字母判断函数类型
                if (index + 3 > input.length()) {   // 判断是否越界
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
			
			/* 处理运算符“-” */
            } else if (head.equals("-")) {
                index++;
                if (lastTokenTag == typeofToken.NUM || lastTokenTag == typeofToken.RP) {  // 二元运算符
                    lastTokenTag = typeofToken.ADDSUB;
                    return new Operator("-", typeofToken.ADDSUB);
                } else {	// 一元运算符
                    lastTokenTag = typeofToken.NEG;
                    return new Operator("-", typeofToken.NEG);
                }
            
			/* 处理运算符“+” */
			} else if (head.equals("+")) {
                index++;
                lastTokenTag = typeofToken.ADDSUB;
                return new Operator("+", typeofToken.ADDSUB);
            
			/* 处理运算符“*”、“/” */
			} else if (head.equals("*") || head.equals("/")) {
                index++;
                lastTokenTag = typeofToken.MULDIV;
                return new Operator(head, typeofToken.MULDIV);
            
			/* 处理关系运算符 */
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
			
			/* 处理非运算符 */
            } else if (head.equals("!")) {
                index++;
                lastTokenTag = typeofToken.NOT;
                return new Operator("!", typeofToken.NOT);
            
			/* 处理与运算符 */
			} else if (head.equals("&")) {
                index++;
                lastTokenTag = typeofToken.AND;
                return new Operator("&", typeofToken.AND);
            
			/* 处理或运算符 */
			} else if (head.equals("|")) {
                index++;
                lastTokenTag = typeofToken.OR;
                return new Operator("|", typeofToken.OR);
            
			/* 处理三元运算符 */
			} else if (head.equals("?")) {
                index++;
                lastTokenTag = typeofToken.QM;
                return new Operator("?", typeofToken.QM);
            } else if (head.equals(":")) {
                index++;
                lastTokenTag = typeofToken.COLON;
                return new Operator(":", typeofToken.COLON);
            
			/* 处理括号 */
			} else if (head.equals("(")) {
                index++;
                lastTokenTag = typeofToken.LP;
                return new Operator("(", typeofToken.LP);
            } else if (head.equals(")")) {
                index++;
                lastTokenTag = typeofToken.RP;
                return new Operator(")", typeofToken.RP);
            
			/* 处理逗号 */
			} else if (head.equals(",")) {
                index++;
                lastTokenTag = typeofToken.COMMA;
                return new Punctuation(",", typeofToken.COMMA);
            
			/* 处理求幂运算符 */
			} else if (head.equals("^")) {
                index++;
                lastTokenTag = typeofToken.POWER;
                return new Operator("^", typeofToken.POWER);
            
			/* 对于其他字符，视为非法运算符并抛出异常 */
			} else {
                throw new IllegalSymbolException();
            }
        }
        return new Dollar();
    }

    /**
     * 判断是否到达输入字符串的结尾
     *
     * @return 判断结果
     */
	private boolean isEnd() {
        return index >= input.length();
    }
	
	/**
     * 返回输入字符串中当前读取数字的最后一个字符的位置索引
     *
     * @return 位置索引
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
