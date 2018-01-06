package com.bolink;

public class Test{
    @org.junit.Test
    public  void main(){
        System.out.println("hello world!");
        int a = 11;
        int b = -11;
        printint(a);
        printint(a<<1);
        printint(a>>1);
        printint(a>>>1);
        printint(b);
        printint(b<<1);
        printint(b>>1);
        printint(b>>>1);
    }

    public void printint(int target){
//        System.out.println(Integer.toBinaryString(target));
        System.out.println(""+target);
    }
}