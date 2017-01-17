package com.example;

public class MyClass {
    public static void main(String[] args){
        final String a = "hello";
        String b = "hello";
        String c = a+2;
        String d = b+2;
        String e = "hello2";
        System.out.println(e.equals(c));
        System.out.println(e.equals(d));
    }
}
