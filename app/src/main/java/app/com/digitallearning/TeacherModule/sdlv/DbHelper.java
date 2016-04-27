package app.com.digitallearning.TeacherModule.sdlv;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	public static final String TABLE_SERVER_DATA = "server_data";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_REQUEST_TYPE = "request_type";
	public static final String COLUMN_USER_DATA = "user_data";
	public static final String COLUMN_IN_PROGRESS = "in_progress";

	private static final String DATABASE_NAME = "serverData.db";
	private static final int DATABASE_VERSION = 2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_SERVER_DATA + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_URL + " text, " + COLUMN_REQUEST_TYPE + " text, "
			+ COLUMN_USER_DATA + " text, " + COLUMN_IN_PROGRESS + " INTEGER DEFAULT 0 );";

	private SQLiteDatabase database;

	private String TABLE_COLUMNS[] = { COLUMN_ID, COLUMN_URL, COLUMN_REQUEST_TYPE, COLUMN_USER_DATA, COLUMN_IN_PROGRESS };

	/***
	 * Initialize the constructor with writable permission
	 * 
	 * @param context
	 */
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.getWritePermission();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		//CategoryDao.createTable(database);
		//TimeSloatDao.createTable(database);
		DigitalLearningDao.createTable(database);
		DigitalLearningDao.createLesson(database);
		DigitalLearningDao.createResourceTable(database);


		Log.e("ServerData", "database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER_DATA);
		//db.execSQL("DROP TABLE IF EXISTS " + CategoryDao.TABLE_PRODUCT);
		//db.execSQL("DROP TABLE IF EXISTS " + CategoryDao.TABLE_CATEGORY);
		//db.execSQL("DROP TABLE IF EXISTS " + TimeSloatDao.TABLE_TIMESLOT);
		db.execSQL("DROP TABLE IF EXISTS " + DigitalLearningDao.TABLE_CLASSES);
		onCreate(db);
	}

	/**
	 * open database with writable permission
	 * 
	 * @return
	 * @throws SQLException
	 */
	public SQLiteDatabase getWritePermission() throws SQLException {
		database = this.getWritableDatabase();
		return database;
	}

	/*
	 * this method takes readeable permission
	 */
	public SQLiteDatabase getReadPermission() throws SQLException {
		database = this.getReadableDatabase();
		return database;
	}



	/**
	 * this method will clear all the db tables
	 */
	public void deleteAll() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_SERVER_DATA);
	//	db.execSQL("DELETE FROM " + ProductDao.TABLE_PRODUCT);
		//db.execSQL("DELETE FROM " + CategoryDao.TABLE_CATEGORY);
		//db.execSQL("DELETE FROM " + TimeSloatDao.TABLE_TIMESLOT);
		db.execSQL("DELETE FROM " + DigitalLearningDao.TABLE_CLASSES);
	}
}