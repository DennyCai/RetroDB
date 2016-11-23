package com.denny.retrodb;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denny.retrodb.annotation.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MainActivity.class.getMethods()[0].getP
        new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        }
    }

    interface Test{

        @Select(where = "_id = ${id}")
        Cursor find();
    }
}
