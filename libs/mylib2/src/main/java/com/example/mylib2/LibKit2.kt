package com.example.mylib2

import android.util.Log

object LibKit2 {

    private const val TAG = "LibKit"

    fun test(): String {
        Log.i(TAG, "test2 >>>")
        JavaLib02().lib02()
        Log.i(TAG, "test2 <<<")

        // return "${this.javaClass.name}#test; DEBUG = ${BuildConfig.DEBUG}"
        return "${this.javaClass.name}#test"
    }
}