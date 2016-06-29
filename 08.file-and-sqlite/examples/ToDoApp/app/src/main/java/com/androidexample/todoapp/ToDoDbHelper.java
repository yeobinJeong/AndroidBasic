package com.androidexample.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDbHelper extends SQLiteOpenHelper {

    public ToDoDbHelper(
            Context context,    //SQLite를 사용하는 Activity, Service, ....
            String name,        //SQLite DB Filename
            SQLiteDatabase.CursorFactory factory, //Custom Cursor (null)
            int version) {      //SQLite DB 파일 버전
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("DROP TABLE IF EXISTS todo");

        //Schema 생성 - Table 생성
        db.execSQL("CREATE TABLE todo " +
                "(_id integer primary key, title text, content text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //버전 변경시 처리해야할 작업
        onCreate(db);
    }
}
