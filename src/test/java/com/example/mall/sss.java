package com.example.mall;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class sss {
    public static void main(String[] args) {

    }
    public boolean isValid(String s) {

        if(s.isEmpty())
            return true;
        Stack<Character> stack=new Stack<Character>();
        for(char c:s.toCharArray()){
            if(c=='(')
                stack.push(')');
            else if(c=='{')
                stack.push('}');
            else if(c=='[')
                stack.push(']');
            else if(stack.empty()||c!=stack.pop())
                return false;
        }
        if(stack.empty())
            return true;
        return false;
    }
}
