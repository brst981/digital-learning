package app.com.digitallearning.TeacherModule.sdlv;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DigitalLearningDao {
	private static final String TAG = DigitalLearningDao.class.getSimpleName();

	public static final String TABLE_CLASSES = "classes";
	public static final String TABLE_LESSON = "lesson";
	public static final String TABLE_RESOURCE="resource";


	public static final int TODAY = -1;
	public static final int PENDING = 0;
	public static final int DELIVERED = 1;
	public static final int FAILED = 2;

	static final String CLASS_ID = "id";
	static final String RESOURCE_ID = "resource_id";
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";


	static final String COLUMN_DATA = "jsondata";
	static final String LESSONID = "lessonId";

	static final String COLUMN_STATUS = "status";
	static final String CALLING_STATUS = "callingstatus";

	static final String COLUMN_MODIFIED = "modified";

	private DbHelper mHelper;

	public DigitalLearningDao(Context context) {
		mHelper = new DbHelper(context);
	}
	

	public static void createTable(SQLiteDatabase db) {
		
		String DATABASE_CREATE_DELIVERY = "create table " + TABLE_CLASSES + "(" + CLASS_ID + " INTEGER, " + COLUMN_DATA + " text, "
				+ COLUMN_STATUS + " text, " + CALLING_STATUS + " text, " + COLUMN_MODIFIED
				+ " TIMESTAMP DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')));";

		db.execSQL(DATABASE_CREATE_DELIVERY);
	}

	public static void createLesson(SQLiteDatabase db) {

		String DATABASE_CREATE_DELIVERY = "create table " + TABLE_LESSON + "(" + CLASS_ID + " INTEGER, " + LESSONID + " text, "
				+ COLUMN_STATUS + " text, " + CALLING_STATUS + " text, " + COLUMN_MODIFIED
				+ " TIMESTAMP DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')));";

		db.execSQL(DATABASE_CREATE_DELIVERY);
	}


	public static void createResourceTable(SQLiteDatabase db) {

		String DATABASE_CREATE_DELIVERY = "create table " + TABLE_RESOURCE + "(" + CLASS_ID + " INTEGER, " + RESOURCE_ID + " text, "
				+ TITLE + " text, " + DESCRIPTION + " text, " + COLUMN_MODIFIED
				+ " TIMESTAMP DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')));";

		db.execSQL(DATABASE_CREATE_DELIVERY);
	}
	public void insertClasses(int id, String data, String status, String callingStatus) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, id);
		values.put(COLUMN_DATA, data);
		//values.put(CALLING_STATUS, callingStatus);

		if (status != null)
			values.put(COLUMN_STATUS, status);
		
		String whereClause = CLASS_ID + "=" + id;
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { CLASS_ID }, whereClause, null, null, null, null);
		if (cursor.moveToFirst()) {
			long rowId = db.update(TABLE_CLASSES, values, whereClause, null);
			Log.e(TAG, rowId + " updated");
		} else {
			long rowId = db.insert(TABLE_CLASSES, null, values);
			Log.e(TAG, rowId + " inserted");
		}
	}
	public void insertLesson(int id, String data, String status, String callingStatus) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, id);
		values.put(LESSONID, data);

		//values.put(CALLING_STATUS, callingStatus);

		//if (status != null)
			//values.put(COLUMN_STATUS, status);

		String whereClause = LESSONID + "=" + data;
		Cursor cursor = db.query(TABLE_LESSON, new String[] { CLASS_ID ,LESSONID}, whereClause, null, null, null, null);
		if (cursor.moveToFirst()) {
			long rowId = db.update(TABLE_LESSON, values, whereClause, null);
			Log.e(TAG, rowId + " updated");
		} else {
			long rowId = db.insert(TABLE_LESSON, null, values);
			Log.e(TAG, rowId + " inserted");
		}
	}
	public void insertResource(int classId, String title, String description, String id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, classId);
		values.put(RESOURCE_ID, id);
		values.put(TITLE, title);
		values.put(DESCRIPTION, description);

		//values.put(CALLING_STATUS, callingStatus);

		//if (status != null)
		//values.put(COLUMN_STATUS, status);

		String whereClause = RESOURCE_ID + "=" + id;
		Cursor cursor = db.query(TABLE_RESOURCE, new String[] { CLASS_ID ,RESOURCE_ID}, whereClause, null, null, null, null);
		if (cursor.moveToFirst()) {
			long rowId = db.update(TABLE_RESOURCE, values, whereClause, null);
			Log.e(TAG, rowId + " updated");
		} else {
			long rowId = db.insert(TABLE_RESOURCE, null, values);
			Log.e(TAG, rowId + " inserted");
		}
	}

	public ArrayList<String> fecthAllClasses()
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_CLASSES, new String[]{CLASS_ID}, null, null, null, null, null);
		ArrayList<String> classId=new ArrayList<>();

		while (cursor.moveToNext()) {
			try {
				Log.e("detaillss", "daaa" + cursor.getString(0));
				classId.add(cursor.getString(0));
				//Delivery delivery = Parse.toDelivery(object, null, TODAY);
				//delivery.setDelivery_status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
				//delivery.setCallingStatus(cursor.getString(cursor.getColumnIndex(CALLING_STATUS)));
				//deliveries.add(delivery);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
         return classId;

	}

	public ArrayList<String> fecthAllLesson(int type)
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = CLASS_ID + "='" + type + "'";

		Cursor cursor = db.query(TABLE_LESSON, new String[]{LESSONID}, whereClause, null, null, null, null);
		ArrayList<String> classId=new ArrayList<>();

		while (cursor.moveToNext()) {
			try {
				Log.e("detaillss", "daaa" + cursor.getString(0));
				classId.add(cursor.getString(0));
				//Delivery delivery = Parse.toDelivery(object, null, TODAY);
				//delivery.setDelivery_status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
				//delivery.setCallingStatus(cursor.getString(cursor.getColumnIndex(CALLING_STATUS)));
				//deliveries.add(delivery);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return classId;

	}

	public ArrayList<String> fecthAllResource(int type)
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = 	CLASS_ID + "='" + type + "'";

		Cursor cursor = db.query(TABLE_RESOURCE, new String[]{RESOURCE_ID}, whereClause, null, null, null, null);
		ArrayList<String> classId=new ArrayList<>();

		while (cursor.moveToNext()) {
			try {
				Log.e("detaillss", "daaa" + cursor.getString(0));
				classId.add(cursor.getString(0));
				//Delivery delivery = Parse.toDelivery(object, null, TODAY);
				//delivery.setDelivery_status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
				//delivery.setCallingStatus(cursor.getString(cursor.getColumnIndex(CALLING_STATUS)));
				//deliveries.add(delivery);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return classId;

	}


	public ArrayList<String> getDeliveries(int type) {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String whereClause = COLUMN_STATUS + "='" + type + "'";
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { CLASS_ID, COLUMN_DATA, COLUMN_STATUS ,CALLING_STATUS}, whereClause, null, null, null,
				COLUMN_MODIFIED);

		ArrayList<String> deliveries = new ArrayList<String>();
		while (cursor.moveToNext()) {
			try {
				JSONObject object = new JSONObject(cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
				//Delivery delivery = Parse.toDelivery(object, null, TODAY);
				//delivery.setDelivery_status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
				//delivery.setCallingStatus(cursor.getString(cursor.getColumnIndex(CALLING_STATUS)));
				//deliveries.add(delivery);
			} catch (JSONException e) {
			 	e.printStackTrace();
			}
		}
		return deliveries;
	}

	public JSONObject getAllClasses(int deliveryId) {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		JSONObject object = null;
		//String whereClause = COLUMN_ID + "=" + deliveryId + "";
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { CLASS_ID, COLUMN_DATA, COLUMN_STATUS }, null, null, null, null,
				COLUMN_MODIFIED);

		if (cursor.moveToNext()) {
			try {
				object = new JSONObject(cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	public void updateStatus(int id, int status) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = CLASS_ID + "=" + id;
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { CLASS_ID }, whereClause, null, null, null, null);
		ContentValues values = new ContentValues();
		values.put(COLUMN_STATUS, status + "");

		if (cursor.moveToFirst()) {
			long rowid = db.update(TABLE_CLASSES, values, whereClause, null);
			Log.d(TAG, rowid + " updated delivery status" + id + ":id:status:" + status);
		}
	}

	public void deleteDelivery(String id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = CLASS_ID + "=" + id;
		long rowId = db.delete(TABLE_CLASSES, whereClause, null);
		Log.d(TAG, rowId + " delivery deleted");
	}

	public void deleteAll() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.delete(TABLE_CLASSES, null, null);
		db.close();
	}

	public void deleteLesson() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.delete(TABLE_LESSON, null, null);
		db.close();
	}
	public void deleteReSOURCE() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.delete(TABLE_RESOURCE, null, null);
		db.close();
	}

	public void close() {
		if (mHelper != null)
			mHelper.close();
	}

	/**
	 * Delete all the rows from table where Status as type
	 * 
	 * @param type
	 */
	public void deleteDelivery(int type) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = COLUMN_STATUS + "='" + type + "'";
		long rowId = db.delete(TABLE_CLASSES, whereClause, null);
		Log.d(TAG, rowId + " delivery deleted");
	}

	public void deleteLessonId(int type) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = LESSONID + "='" + type + "'";
		long rowId = db.delete(TABLE_LESSON, whereClause, null);
		Log.d(TAG, rowId + " delivery deleted");
	}

	public void deleteResourceId(int type) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = RESOURCE_ID + "='" + type + "'";
		long rowId = db.delete(TABLE_RESOURCE, whereClause, null);
		Log.d(TAG, rowId + " delivery deleted");
	}

	public void updateData(JSONObject tempDelivery, int id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = CLASS_ID + "=" + id;
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { CLASS_ID }, whereClause, null, null, null, null);
		ContentValues values = new ContentValues();
		values.put(COLUMN_DATA, tempDelivery.toString());

		if (cursor.moveToFirst()) {
			long rowid = db.update(TABLE_CLASSES, values, whereClause, null);
			Log.d(TAG, rowid + " updated delivery status");
		}
	}

	public void updateDeliveryStatus(int id, String status) {

		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, id);
		values.put(CALLING_STATUS, status);

		String whereClause = CLASS_ID + "=" + id;
		Cursor cursor = db.query(TABLE_CLASSES, new String[] { COLUMN_DATA }, whereClause, null, null, null, null);
		if (cursor.moveToFirst()) {
			try {
				String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
				JSONObject object = new JSONObject(data);
				object.put("member1_call_status", status);
				object.put("member2_call_status", status);
				values.put(COLUMN_DATA, object.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			long rowId = db.update(TABLE_CLASSES, values, whereClause, null);
			Log.d(TAG, rowId + " updated");
		}
	}
}
