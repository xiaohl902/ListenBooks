package com.mwkj.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${WU} on 2016/10/31.
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    public MySqliteHelper(Context context) {
        super(context, "mydata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table search(_id integer primary key, name)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase("search");
    }

}
