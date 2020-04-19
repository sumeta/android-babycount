package com.tpsoft.babycount.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class HistoryDao extends DBHelper {

    private SQLiteDatabase sqLiteDatabase;
    Context context;


    public HistoryDao(Context context) {
        super(context);
        sqLiteDatabase = this.getWritableDatabase();
    }

    public HistoryModel get(String id) {
        //sqLiteDatabase = this.getWritableDatabase();
        HistoryModel model = new HistoryModel();
        String whereClause = BaseColumns._ID+" =?";
        String[] whereArgs = new String[]{id};
        Cursor cursor = sqLiteDatabase.query
                (HistoryModel.TABLE, null, whereClause, whereArgs, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Integer idKey = Integer.parseInt(cursor.getString(0));
            model.setId(idKey);
            model.setCount(cursor.getInt(1));
            model.setCreatedDate(cursor.getString(2));
            cursor.moveToNext();
        }

        sqLiteDatabase.close();
        cursor.close();

        return model;
    }

    public List<HistoryModel> getList() {
        //sqLiteDatabase = this.getWritableDatabase();
        List<HistoryModel> models = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query
                (HistoryModel.TABLE, null, null, null, null, null, "created_date DESC");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            HistoryModel model = new HistoryModel();
            Integer idKey = Integer.parseInt(cursor.getString(0));
            model.setId(idKey);
            model.setCount(cursor.getInt(1));
            model.setCreatedDate(cursor.getString(2));
            models.add(model);

            cursor.moveToNext();
        }

        sqLiteDatabase.close();
        cursor.close();

        return models;
    }

    public long insert(HistoryModel model) {
        //sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryModel.Column.COUNT, model.getCount());
        values.put(HistoryModel.Column.CREATED_DATE, model.getCreatedDate());

        long id = sqLiteDatabase.insert(HistoryModel.TABLE, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public boolean update(HistoryModel model){
        //sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryModel.Column.ID, model.getId());
        values.put(HistoryModel.Column.COUNT, model.getCount());
        //sqLiteDatabase.update(HistoryModel.TABLE,values, columnId+"="+model.getId(),null);
        sqLiteDatabase.update(HistoryModel.TABLE,values,
                HistoryModel.Column.ID + " = ? ",
                new String[] { String.valueOf(model.getId()) });
        sqLiteDatabase.close();

        return true;
    }

    public boolean delete(String id) {
        //sqLiteDatabase = this.getWritableDatabase();
        String sql = "DELETE FROM `history` WHERE " + BaseColumns._ID + " = " + id;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
        return true;
    }

    public void clear() {
        //sqLiteDatabase = this.getWritableDatabase();
        String sql = "DELETE FROM `history`";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

}
