package com.imooc.test;


import java.util.Stack;

public class SearchBrace {

    public static void main(String[] args) {
        String str = "((()))";
        System.out.println("isValid(str) = " + isValid(str));
        System.out.println("isValidateComplex(str) = " + isValidateComplex(str));
    }

    /**
     * 使用stack 简单实现括号是否成对出现
     *
     * @param s 目标字符串
     * @return validate result
     */
    public static boolean isValid(String s) {
        if (null == s || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        //消除法核心逻辑
        Stack<Character> t = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                t.push(c);
            } else if (c == ')') {
                //容错
                if (t.isEmpty()) {
                    return false;
                }
                t.pop();
            }
        }
        return t.isEmpty();
    }

    /**
     * 优化
     * 1. 栈中内容一样 计数器优化
     * 2. 栈中存放真正的内容
     */
    public static boolean isValidateComplex(String s) {
        if (null == s || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        //消除法主要逻辑升级
        int leftBraceNumber = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftBraceNumber++;
            } else if (c == ')') {
                if (leftBraceNumber <= 0) {
                    return false;
                }
                leftBraceNumber--;
            }
        }
        return leftBraceNumber == 0;
    }
}
