package com.asule.blog.base;

public class B extends A{

    public B(int x){
        this();
        System.out.println(x+"B no constructor");
    }


    public B() {
        System.out.println("B no constructor");
    }



}
