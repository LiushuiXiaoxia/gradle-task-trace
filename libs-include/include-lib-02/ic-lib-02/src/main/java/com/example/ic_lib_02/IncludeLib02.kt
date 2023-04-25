package com.example.ic_lib_02

import android.util.Log

class IncludeLib02 {

    companion object {
        private const val TAG = "IncludeLib02"
    }

    fun test(): String {
        Log.i(TAG, "test: ")

        return this.javaClass.name + "#test; DEBUG=${BuildConfig.DEBUG}"
    }
}