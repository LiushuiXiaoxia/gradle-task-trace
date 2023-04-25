package com.example.mylib;

import android.util.Log;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class JavaLib01 {

    private static final String TAG = "JavaLib01";

    public String lib01() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.stream().forEach(it -> {
            Log.i(TAG, "lib01: foreach" + it);
            Log.i(TAG, "lib01: foreach" + it);
        });

        new Thread(() -> {
            Log.i(TAG, "lib01: run");
            Log.i(TAG, "lib01: run");
        }).start();

        LocalDate.now();
        return this.getClass().getName() + "#lib01";
    }
}