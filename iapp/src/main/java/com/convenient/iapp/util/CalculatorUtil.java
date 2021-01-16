package com.convenient.iapp.util;

import java.text.DecimalFormat;
import java.util.*;

public class CalculatorUtil {
    private static final int MAXCAPACITY = 500;
//    private String expression;

    public CalculatorUtil() {
    }

    public int getPriority(String str) {
        int pri=-1;

        if(str.equals("("))
            pri=0;
        else if(str.equals("+") || str.equals("-"))
            pri=1;
        else if(str.equals("*") || str.equals("/"))
            pri=2;

        return pri;
    }

    public String[] split(String expression) {
        ArrayList<String> strList=new ArrayList<String>();
        int splitIndex=0;

        for(int i=0;i<expression.length();i++) {
            if(expression.charAt(i)=='+' || expression.charAt(i)=='-' || expression.charAt(i)=='*' || expression.charAt(i)=='/' || expression.charAt(i)=='(' || expression.charAt(i)==')'){
                if(splitIndex == i) { //处理连续多个操作符
                    if(expression.charAt(i)=='-') {     //处理操作符"( + - * /"符号后面遇到"-"情况时，将后面的数字当做负数处理
                        splitIndex=i;
                        continue;
                    }
                    else
                        strList.add(expression.substring(splitIndex, i+1));
                }
                else {
                    strList.add(expression.substring(splitIndex, i));
                    strList.add(expression.substring(i, i+1));
                }
                splitIndex=i+1;
            }
            if((i==expression.length()-1) && (splitIndex<=i))
                strList.add(expression.substring(splitIndex));
        }

        return strList.toArray(new String[0]);
    }

    public String[] getSuffixExpression(String[] expression) {
        Stack<String> strStack=new Stack<String>();
        ArrayList<String> strList=new ArrayList<String>();

        for(int i=0;i<expression.length;i++) {
            if(expression[i].equals("("))  //遇到'('时入栈
                strStack.push(expression[i]);
            else if(expression[i].equals("+") || expression[i].equals("-") ||expression[i].equals("*") ||expression[i].equals("/")) {
                if(strStack.isEmpty())     //栈空时操作符入栈
                    strStack.push(expression[i]);
                else {                     //否则比较栈顶操作符优先级
                    while((!strStack.isEmpty()) && (getPriority(strStack.peek())>=getPriority(expression[i]))) {
                        strList.add(strStack.pop());
                    }
                    strStack.push(expression[i]);
                }
            }
            else if(expression[i].equals(")")) {//遇到')'时出栈直至'('
                while(!strStack.peek().equals("(")){
                    strList.add(strStack.pop());
                }
                strStack.pop(); //抛出'('
            }
            else
                strList.add(expression[i]);
        }
        while(!strStack.isEmpty())
            strList.add(strStack.pop());

        return strList.toArray(new String[0]);
    }

    public double getResult(String[] expression) {
        Stack<Double> strStack=new Stack<Double>();
        double num1,num2;
        double result=0;

        for(int i=0;i<expression.length;i++) {
            if(expression[i].equals("+") || expression[i].equals("-") ||expression[i].equals("*") ||expression[i].equals("/")) {
                String str=expression[i];
                switch(str) {
                    case "+":{num1=strStack.pop();num2=strStack.pop();result=num2+num1;strStack.push(result);break;}
                    case "-":{num1=strStack.pop();num2=strStack.pop();result=num2-num1;strStack.push(result);break;}
                    case "*":{num1=strStack.pop();num2=strStack.pop();result=num2*num1;strStack.push(result);break;}
                    case "/":{num1=strStack.pop();num2=strStack.pop();result=num2/num1;strStack.push(result);break;}
                }
            }
            else
                strStack.push(Double.valueOf(expression[i]));
        }

        result=0;
        while(!strStack.isEmpty()) {
            result+=strStack.pop();
        }

        return result;
    }

    public double getResult(String expression) {
        if(expression.length() > MAXCAPACITY)
            return 0D;
        return getResult(getSuffixExpression(split(expression)));
    }

    /**
     * 四舍五入
     * @param d
     * @return
     */
    public String getRound(double d){
        DecimalFormat dfc = new DecimalFormat("#.########");
        String cellContent = dfc.format(d);
        return cellContent;
    }

    /**
     * 四舍五入
     * @param d
     * @return
     */
    public static String getRoundForRate(double d){
        DecimalFormat dfc = new DecimalFormat("#.####");
        return dfc.format(d);
    }

}
