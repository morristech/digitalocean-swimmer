package com.yassirh.digitalocean.data;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class SqlDao<T> {
	
	public abstract DatabaseHelper getDatabaseHelper();
	public abstract TableHelper getTableHelper();
	public abstract T newInstance(Cursor cursor);
	
	public List<T> getAll(String orderBy) {
		SQLiteDatabase db = getDatabaseHelper().getWritableDatabase();

		List<T> collection = new ArrayList<T>();
		Cursor cursor = db.query(getTableHelper().TABLE_NAME,
				getTableHelper().getAllColumns(), null, null, orderBy, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T object = newInstance(cursor);
			collection.add(object);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return collection;
	}
	
	public T findById(long id) {
		SQLiteDatabase db = getDatabaseHelper().getWritableDatabase();
		getTableHelper();
		Cursor cursor = db.query(getTableHelper().TABLE_NAME,
				getTableHelper().getAllColumns(), TableHelper.ID + " = " + id, null, null, null, null);
		cursor.moveToNext();		
		T t = newInstance(cursor);
		db.close();
		return t;
	}
}
