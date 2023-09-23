package token;

/**
 * 定义各类非终结符和终结符对应的编号
 */
public class typeofToken {
        public final static int NUM = 0, BOOL = 1, 
				ADDSUB = 2,  //+ -
				MULDIV = 3,  //* /
				NEG = 4, 	 //-一元运算符
				POWER = 5, 	 //^
				FUNC = 6,    // sin cos max min 
                LP = 7, 	 // ( 
				COMMA = 8,   // ,
				RP = 9, 	 // )
				RE = 10, 	 // >=<关系运算
				NOT = 11, AND = 12, OR = 13,  //与或非 
				QM = 14, COLON = 15, 		  //?:
				DOLLAR = 16;  				  //$
        public final static int Expr = 17, ArithExpr = 18, BoolExpr = 19, ArithExprList = 20;
        public final static int START = 21, NULL = 22;
    }