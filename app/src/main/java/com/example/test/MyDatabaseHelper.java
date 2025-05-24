package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyDatabase.db";
    private static final int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS clothes (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "style TEXT, type TEXT, preference INTEGER, image_url TEXT)");
        //db.execSQL("INSERT INTO clothes (style, type) VALUES ('홍길동', '값1'), ('김철수', '값2')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clothes");
        onCreate(db);
    }
}
