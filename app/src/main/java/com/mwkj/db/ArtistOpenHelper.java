package com.mwkj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArtistOpenHelper extends SQLiteOpenHelper {
	private static final int VERSION = 0X001;
	private static final String SQL_NAME = "artist.db";
	private static final String CREATE_TABLE_ARTIST = "create table artists(_id integer primary key,artistId,artistName,artistWork,workNumber,playNumber,artistImg)";


	public ArtistOpenHelper(Context context) {
		super(context, SQL_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ARTIST);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
