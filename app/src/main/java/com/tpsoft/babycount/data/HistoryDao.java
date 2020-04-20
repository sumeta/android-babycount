package com.tpsoft.babycount.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            model.setType(cursor.getString(2));
            model.setComment(cursor.getString(3));
            model.setCreatedDate(cursor.getString(4));
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
            model.setType(cursor.getString(2));
            model.setComment(cursor.getString(3));
            model.setCreatedDate(cursor.getString(4));
            models.add(model);

            cursor.moveToNext();
        }

        sqLiteDatabase.close();
        cursor.close();

        return models;
    }

    public List<HistoryModel> getTodayList() {
        List<HistoryModel> models = new ArrayList<>();

        //String whereClause =HistoryModel.Column.CREATED_DATE+" =?";
        //String[] whereArgs = new String[]{id};

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateStr = dateFormat.format(date);
        String whereClause  = "strftime('%Y-%m-%d',created_date) = ?";
        String[] whereArgs = new String[]{dateStr};
        Cursor cursor = sqLiteDatabase.query
                (HistoryModel.TABLE, null, whereClause, whereArgs, null, null, "created_date DESC");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            HistoryModel model = new HistoryModel();
            Integer idKey = Integer.parseInt(cursor.getString(0));
            model.setId(idKey);
            model.setCount(cursor.getInt(1));
            model.setType(cursor.getString(2));
            model.setComment(cursor.getString(3));
            model.setCreatedDate(cursor.getString(4));
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
        values.put(HistoryModel.Column.TYPE, model.getType());
        values.put(HistoryModel.Column.COMMENT, model.getComment());
        values.put(HistoryModel.Column.CREATED_DATE, model.getCreatedDate());

        long id = sqLiteDatabase.insert(HistoryModel.TABLE, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public boolean update(HistoryModel model){
        //sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(HistoryModel.Column.ID, model.getId());
        values.put(HistoryModel.Column.COUNT, model.getCount());
        values.put(HistoryModel.Column.TYPE, model.getType());
        values.put(HistoryModel.Column.COMMENT, model.getComment());
        //sqLiteDatabase.update(HistoryModel.TABLE,values, columnId+"="+model.getId(),null);
        sqLiteDatabase.update(HistoryModel.TABLE,values,
                HistoryModel.Column.ID + " = ? ",
                new String[] { String.valueOf(model.getId()) });
        sqLiteDatabase.close();

        return true;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM `history` WHERE " + BaseColumns._ID + " = " + id;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteByLast(String type) {
        String sql = "DELETE FROM history WHERE _id IN\n" +
                " (SELECT _id FROM history\n" +
                " WHERE type = '"+type+"'\n" +
                " ORDER BY created_date DESC LIMIT 1)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
        return true;
    }

    public void clear() {
        String sql = "DELETE FROM `history`";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

}
