package com.tpsoft.babycount.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "babycount.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("on Create");
        String CREATE_HISTORY_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s INTEGER, %s TEXT,%s TEXT,%s TEXT)",
                HistoryModel.TABLE,
                HistoryModel.Column.ID,
                HistoryModel.Column.COUNT,
                HistoryModel.Column.TYPE,
                HistoryModel.Column.COMMENT,
                HistoryModel.Column.CREATED_DATE);

        db.execSQL(CREATE_HISTORY_TABLE);

        System.out.println("Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS history";
//
//        db.execSQL(DROP_FRIEND_TABLE);
//
//        onCreate(db);

    }


}
