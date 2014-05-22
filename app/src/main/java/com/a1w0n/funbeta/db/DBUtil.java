package com.aiwan.funbeta.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtil extends SQLiteOpenHelper {

	private final static String DATABSE_NAME = "db_cn.db";
	private final static int DATABASE_VERSION = 1;

	public DBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}

	public DBUtil(Context context) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable_article(db);
	}

	private void createTable_article(SQLiteDatabase db) {
		String sql = "CREATE TABLE  articles"
				+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "type INTEGER, " //
				+ "url TEXT," //
				+ "title TEXT,"//
				+ "content TEXT)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS  acticles");
		db.close();
	}

}
