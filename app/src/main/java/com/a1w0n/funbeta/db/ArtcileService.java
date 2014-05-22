package com.aiwan.funbeta.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.aiwan.funbeta.po.Article;

public class ArtcileService extends DBUtil {

	private static final String TABLE_NAME = "articles";

	private static ArtcileService service = null;

	public static ArtcileService getInstance(Context context) {
		if (service == null) {
			service = new ArtcileService(context);
		}
		return service;
	}

	public ArtcileService(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public ArtcileService(Context context) {
		super(context);
	}

	/**
	 * ����һ�����
	 * 
	 * @param article
	 * @return
	 */
	public boolean insert(Article article) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("title", article.getTitle());
			cv.put("content", article.getContent());
			cv.put("url", article.getUrl());
			cv.put("type", article.getType());
			SQLiteDatabase db = this.getWritableDatabase();
			db.insert(TABLE_NAME, null, cv);
			db.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * ���URlɾ���¼
	 * 
	 * @param url
	 * @return
	 */
	public boolean deleteByUrl(String url) {
		if (this.getWritableDatabase().delete(TABLE_NAME, "url=?",
				new String[] { url }) != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��id��ȡArticle
	 * 
	 * @param id
	 * @return
	 */
	public Article getArticleById(int id) {
		Cursor cursor = this.getReadableDatabase().query(TABLE_NAME, //
				null,//
				"_id = ?",//
				new String[] { String.valueOf(id) },//
				null,//
				null,//
				null);
		if (cursor.moveToFirst()) {
			Article artcile = new Article();
			artcile.setType(cursor.getInt(cursor.getColumnIndex("type")));
			artcile.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			artcile.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			artcile.setContent(cursor.getString(cursor
					.getColumnIndex("content")));
			cursor.close();
			return artcile;
		} else {
			cursor.close();
			return null;
		}
	}

	/**
	 * ��url��ȡArticle
	 * 
	 * @param url
	 * @return
	 */
	public Article getArticleByUrl(String url) {
		Cursor cursor = this.getReadableDatabase().query(TABLE_NAME, //
				null,//
				"url = ?",//
				new String[] { String.valueOf(url) },//
				null,//
				null,//
				null);
		if (cursor.moveToFirst()) {
			Article artcile = new Article();
			artcile.setType(cursor.getInt(cursor.getColumnIndex("type")));
			artcile.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			artcile.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			artcile.setContent(cursor.getString(cursor
					.getColumnIndex("content")));
			cursor.close();
			return artcile;
		} else {
			cursor.close();
			return null;
		}
	}

	/**
	 * 
	 * @return ��ݿ��е����������б�
	 */
	public List<Map<String, String>> getLocalArtclesList() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Cursor cursor = this.getReadableDatabase().query(TABLE_NAME, //
				null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Map<String, String> map = new HashMap<String, String>();
				map.put("title",
						cursor.getString(cursor.getColumnIndex("title")));
				map.put("url", cursor.getString(cursor.getColumnIndex("url")));
				list.add(map);
			} while (cursor.moveToNext());
			cursor.close();
		}
		cursor.close();
		return list;
	}

	/**
	 * ɾ�����е����
	 */
	public void deleteAll() {
		this.getWritableDatabase().delete(TABLE_NAME, null, null);
	}
}
