package com.example.mylib2.api;

import com.example.mylib.api.ITestApi;

public class ApiKit {

    private static final ITestApi api = new TestApiImpl();

    public static ITestApi defaultApi() {
        return api;
    }
}
