package com.example.mylib.api;

import android.util.Log;

public interface ITestApi {

    String TAG = "ITestApi";

    default void hello() {
        Log.e(TAG, "hello world");
    }

    void test();
}