package com.example.mylib2;

import com.example.mylib2.api.ApiKit;

public class JavaLib02 {

    public String lib02() {
        System.out.println("121212121");
        ApiKit.defaultApi().hello();
        ApiKit.defaultApi().test();

        return this.getClass().getName() + "#lib2";
    }
}