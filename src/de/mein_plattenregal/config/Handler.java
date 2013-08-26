package de.mein_plattenregal.config;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Handler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "appconf.db";
	private static final String TABLE_PRODUCTS = "conf";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PRODUCTNAME = "key";
	public static final String COLUMN_QUANTITY = "value";

	public Handler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	public Handler() {
		super(new de.mein_plattenregal.SettingsActivity(), DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public static Handler factory(Activity a) {
		return new Handler(a.getBaseContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public static Handler factory() {
		return factory(new de.mein_plattenregal.SettingsActivity());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME
				+ " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";
		db.execSQL(CREATE_PRODUCTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		onCreate(db);
	}

	public void clearAll() {
		String query = "DELETE FROM " + TABLE_PRODUCTS;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}

	public void delete(String key) {
		String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE "
				+ COLUMN_PRODUCTNAME + " =  \"" + key + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			int id = Integer.parseInt(cursor.getString(0));
			db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
					new String[] { String.valueOf(id) });
			cursor.close();
		}
		db.close();
	}

	public void put(String key, String value) {
		this.delete(key);
		ContentValues values = new ContentValues();
		values.put(COLUMN_PRODUCTNAME, key);
		values.put(COLUMN_QUANTITY, value);
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_PRODUCTS, null, values);
		db.close();
	}

	public String get(String key) {
		return this.get(key, null);
	}

	public String get(String key, String defaultValue) {
		String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE "
				+ COLUMN_PRODUCTNAME + " =  \"" + key + "\"";

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		String value = defaultValue;

		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			value = cursor.getString(2);
			cursor.close();
		}
		db.close();
		return value;
	}
}
