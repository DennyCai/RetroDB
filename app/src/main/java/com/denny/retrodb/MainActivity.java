package com.denny.retrodb;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denny.retrodb.annotation.Select;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.class.getMethods()[0].getP
    }

    interface Test{

        @Select(where = "_id = ${id}")
        Cursor find();
    }
}
