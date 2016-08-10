package com.example.database;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DbHandler extends SQLiteOpenHelper {

	public static final String TAG = "com.example.database.DbHandleoijn-09r";
	// All Static Variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "multicloud.db";

	// Tables Name
	public static final String TABLE_USERS = "users";
	public static final String TABLE_DOCTORS="doctors";
	
	// Column Names - Doctor table
	public static final String KEY_DNAME="dname";
	public static final String KEY_DATE="date";
	public static final String KEY_TIME="time";
	
	// Column Names - Users table
	public static final String KEY_ID = "id";
	//public static final String KEY_CLOUD = "cloud";
	public static final String KEY_UNAME = "uname";
	public static final String KEY_PASS = "passwd";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PHONE = "phone";

	// Create Statement - Users table
	private static final String CREATE_USER_TABLE = "CREATE TABLE "
			+ TABLE_USERS + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_UNAME + " TEXT, " + KEY_PASS
			+ " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PHONE + " TEXT " + ")";

	
	private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE "
			+ TABLE_DOCTORS + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DNAME + " TEXT, " + KEY_DATE
			+ " TEXT, " + KEY_TIME + " TEXT " + ")";
	
	private SQLiteDatabase db;
	
	
	
	
	
	
	public DbHandler(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
		
	}
  public void addDoctor(Doctordb doctordb)
  {
	  ContentValues values = new ContentValues();
	  values.put(KEY_DNAME, doctordb.getDoctorName());
	  values.put(KEY_DATE, doctordb.getDate());
	  values.put(KEY_TIME, doctordb.getTime());
	  db.insert(TABLE_DOCTORS, null, values);
	  
  }
	// Add new User
	public void addUser(User user) {
		ContentValues values = new ContentValues();
		//values.put(KEY_CLOUD, user.getCloud());
		values.put(KEY_UNAME, user.getUserName());
		values.put(KEY_PASS, user.getPassword());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_PHONE, user.getPhone());
		db.insert(TABLE_USERS, null, values);
		Log.d(TAG, user.getUserName() + " created...");
	}

	// Getting Single User
	public User getUser(String uname) {
		User user = new User();

		try {
			Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID,KEY_UNAME, KEY_PASS, KEY_EMAIL,
					KEY_PHONE }, KEY_UNAME + "=?", new String[] { uname },
					null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				user = new User(cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));
			} else{
				user = new User();
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		return user;
	}
//public List<Doctordb> getAllDoctors()
//{
	
//List <Doctordb> doclist = new ArrayList<Doctordb>();
//	String query = "SELECT * FROM " + TABLE_DOCTORS;
//	try
	//{
		//Cursor c = db.rawQuery(query, null);
	
	//if (c.moveToFirst()){
	//	do
	//	{
		//	Doctordb doctordb = new Doctordb(c.getString(0),c.getString(1),c.getString(2));
		//doclist.add(doctordb);
		//}while(c.moveToNext());
	//}
	//}catch (CursorIndexOutOfBoundsException ex) {
		//Log.d(TAG, "No Values " + ex.getMessage());
//	}
	//return doclist;

	// Getting All Contacts
	public List<User> getAllUsers() {
		List<User> userlist = new ArrayList<User>();
		// Select all users
		String selectQuery = "SELECT * FROM " + TABLE_USERS;

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					User user = new User(cursor.getInt(0),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor.getString(4));
					// Adding Users to List
					userlist.add(user);
				} while (cursor.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException ex) {
			Log.d(TAG, "No Values " + ex.getMessage());
		}
		// return users list
		return userlist;
	}

	// Deleting Single User
	public void deleteUser(User user) {
		db.delete(TABLE_USERS, KEY_UNAME + "=?",
				new String[] { String.valueOf(user.getUserName()) });
		
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		// Creating Tables
		_db.execSQL(CREATE_USER_TABLE);
		_db.execSQL(CREATE_DOCTOR_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		// Upgrading Table
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
		// CREATE TABLE
		onCreate(_db);
	}
	public Cursor getInformation(DbHandler dop)
	{
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String [] columns = {KEY_DNAME,KEY_DATE,KEY_TIME};
		Cursor CR = SQ.query(TABLE_DOCTORS,columns,null,null,null,null,null);
		return CR;
	}
}


