package com.example.mylib2.api;

import android.util.Log;

import com.example.mylib.api.ITestApi;

public class TestApiImpl implements ITestApi {

    private static final String TAG = "TestApiImpl";

    @Override
    public void test() {
        Log.e(TAG, "test: ");
    }
}
