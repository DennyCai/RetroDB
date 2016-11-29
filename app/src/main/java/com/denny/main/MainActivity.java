package com.denny.main;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.denny.retrodb.RetroDB;
import com.denny.retrodb.annotation.Field;
import com.denny.retrodb.annotation.Select;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MainActivity.class.getMethods()[0].getP
//        new InvocationHandler(){
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        }
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this,"test.db",null,1){

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table test(_id integer)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id",1);
        db.insert("test",null,cv);
        cv.put("_id",2);
        db.insert("test",null,cv);
        RetroDB retroDB = new RetroDB.Builder().setSQLiteHelper(helper).build();
        System.out.println(retroDB.create(Test.class).find("1\' or \"1\"=\"1\"").getCount());
    }

    interface Test{

        @Select(where = "_id = {id}")
        Cursor find(@Field("id") String id);
    }
}
