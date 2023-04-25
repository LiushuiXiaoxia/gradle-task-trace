package com.example.include_lib_01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ic_lib_01.IncludeLib01;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new IncludeLib01().test();
    }
}