package com.example.ic_lib_01;

import android.util.Log;

public class IncludeLib01 {

    private static final String TAG = "IncludeLib01";

    public String test() {
        Log.i(TAG, "test: ");

        return this.getClass().getName() + "#test";
    }
}
