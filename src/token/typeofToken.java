package token;

/**
 * ���������ս�����ս����Ӧ�ı��
 */
public class typeofToken {
        public final static int NUM = 0, BOOL = 1, 
				ADDSUB = 2,  //+ -
				MULDIV = 3,  //* /
				NEG = 4, 	 //-һԪ�����
				POWER = 5, 	 //^
				FUNC = 6,    // sin cos max min 
                LP = 7, 	 // ( 
				COMMA = 8,   // ,
				RP = 9, 	 // )
				RE = 10, 	 // >=<��ϵ����
				NOT = 11, AND = 12, OR = 13,  //���� 
				QM = 14, COLON = 15, 		  //?:
				DOLLAR = 16;  				  //$
        public final static int Expr = 17, ArithExpr = 18, BoolExpr = 19, ArithExprList = 20;
        public final static int START = 21, NULL = 22;
    }