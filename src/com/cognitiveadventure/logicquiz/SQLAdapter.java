package com.cognitiveadventure.logicquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLAdapter extends SQLiteOpenHelper {
	public static final String KEY_POS = "pos";
	public static final String KEY_SCORE = "score";
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "LogicQuiz";
	private static final String DATABASE_TABLE_NAME = "scoreboard";
	private static final String DATABASE_CREATE = 
			"CREATE TABLE " + DATABASE_TABLE_NAME + " (" + KEY_POS + " INTEGER, " +
			KEY_SCORE + " INTEGER);";
	
	private final Context mCtx;
	private SQLiteDatabase mDb;
	
	SQLAdapter(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		
		mCtx = ctx;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
	}
	
	public SQLAdapter open() throws SQLException {
		mDb = new SQLAdapter(mCtx).getWritableDatabase();
		return(this);
	}
	
	
}
