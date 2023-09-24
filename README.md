# 基于表达式的计算器   ExprEval

本仓库内为2022年春季学期中山大学编译原理课程的实验项目4 ExprEval。

设计并实现一个实际可用的计算器ExpreVal。输入表达式支持布尔类型常量、数值类型常量（其中包括科学记数法）、各种算术运算、关系运算、逻辑运算、以及预定义函数等。

核心知识点是算符优先分析（Operator Precedence Parsing，简称 OPP）技术，重点是算符优先关系表的构造。  

文件说明：

- src 文件夹中存放源代码
- bin 文件夹中存放由源代码编译出来的可执行程序，即 Java 字节码
- doc 文件夹中存放根据上述源代码生成的 javadoc 文档
- ref 文件夹中存放实验软装置所有源程序的 HTML 文档
- testcase 文件夹中存放回归测试用例
- build.bat：编译所有源代码的脚本文件（批命令） 
- run.bat：运行程序的脚本
- design.pdf：实验报告
- doc.bat：生成javadoc 文档的脚本
- test_custom.bat：运行\testcases\custom.xml 中定义的所有自定义的测试用例的脚本
- test_simple.bat：运行\testcases\simple.xml 中定义的所有简单测试用例的脚本
- test_standard.bat：运行\testcases\standard.xml 中定义的所有标准测试用例的脚本。
-   Lab04-ExprEval.pdf：本次实验要求

---

This is the experimental project 4 ExprEval for the Compiling principle course Sun Yat-Sen University in the spring semester of 2022.

Design and implement a practical working calculator ExpreVal. Input expressions support Boolean-type constants, numerical type constants (including scientific notation), various arithmetic operations, relational operations, logical operations, and predefined functions.

The key knowledge point is Operator Precedence Parsing (OPP) technology, and the focus is on the construction of operator precedence table.

Document description:

- src folder stores the source code
The -bin folder contains the executable program compiled from the source code, that is, the Java bytecode
The -doc folder contains the javadoc documents generated from the above source code
- ref folder stores the HTML documents of all source programs of the experimental software device
- testcase folder contains regression test cases
- build.bat: script file that compiles all source code (batch command)
- run.bat: indicates the script that runs the program
- design.pdf: Experiment report
- doc.bat: indicates the script that generates the javadoc document
- test_custom.bat: Scripts that run all custom testcases defined in \testcases\custom.xml
- test_simple-bat: Scripts that run all the simple testcases defined in \testcases\ simple-xml
- test_standard.bat: Scripts that run all standard testcases defined in \testcases\standard.xml.
- Lab04-ExprEval.pdf: Requirements for this experiment

---
