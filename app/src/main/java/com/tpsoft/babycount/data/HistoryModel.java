package com.tpsoft.babycount.data;

import android.provider.BaseColumns;

public class HistoryModel {

    public static final String TABLE = "history";

    public HistoryModel(){ }

    public HistoryModel(int id, int count){
        this.id = id;
        this.count = count;
    }

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String COUNT = "count";
        public static final String CREATED_DATE = "created_date";
    }


    private int id;
    private int count;
    private String createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int content) {
        this.count = count;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
