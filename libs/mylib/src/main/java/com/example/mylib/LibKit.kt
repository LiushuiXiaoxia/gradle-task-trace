package com.example.mylib

import android.util.Log

object LibKit {

    private const val TAG = "LibKit"

    fun test(): String {
        Log.i(TAG, "test")
        return "${this.javaClass.name}#test"
    }
}