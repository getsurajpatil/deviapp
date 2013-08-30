package com.algosys.devi.data;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class Data {
	public Context context;
	public DatabaseHelper dbHelper;
	public SQLiteDatabase db;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Parent_Records";
	
	
	
	public static final String CREATE_RECORD_QUERY = "create table ParentRecords (_id integer primary key, VoucherId text not null, ValidityPeriod text not null, ParentId text not null,ParentName text not null," +
			"ParentAddress text not null,ContactDetails text not null,EmailID text not null);";
	private static final String PARENT_RECORDS = "ParentRecords";
	private static final String _ID = "_id";
	private static final String VOUCHER_ID = "VoucherId";
	private static final String VOUCHER_VALIDITY = "ValidityPeriod";
	private static final String PARENT_ID = "ParentId";
	private static final String PARENT_ADDRESS = "ParentAddress";
	private static final String PARENT_CONTACT = "ContactDetails";
	private static final String PARENT_EMAIL = "EmailID";
	private static final String PARENT_NAME = "ParentName";
	
	public static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			//Log.i("uni_info", "In DBhelper context...");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//Log.i("uni_info", "In onCreate db...");
			db.execSQL(CREATE_RECORD_QUERY);
			
			Log.i("log_info", "Database created...");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			//Log.i("log_info", "DB Update: " );
			
		}
		
	}
	public  Data(Context ctx){
		this.context = ctx;
		dbHelper = new DatabaseHelper(context);
		//Log.i("uni_info", "In Model context...");
	}
	 public void open() throws SQLException{
	    	/*db = dbHelper.getWritableDatabase();  */ 
	    	if (db == null) {    		
	    		db = dbHelper.getWritableDatabase();    		
	    	} else if (!db.isOpen()) {
	    		db = dbHelper.getWritableDatabase();
	    	}
		}
		
		public void close(){
			//if (db.isOpen()) {
				dbHelper.close();
			//}
		}
		
		public void insertParentRecord(String voucherid, String voucherValidity,String parentId,String name,String parentAddress,String parentContact,String parentEmail){
			//db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			//values.put(_ID, id);
			values.put(VOUCHER_ID, voucherid);
			values.put(VOUCHER_VALIDITY, voucherValidity);
			values.put(PARENT_ID, parentId);
			values.put(PARENT_NAME, name);
			values.put(PARENT_ADDRESS, parentAddress);
			values.put(PARENT_CONTACT, parentContact);
			values.put(PARENT_EMAIL, parentEmail);
			db.insert(PARENT_RECORDS, null, values);
			Log.i("log_info", "data inserted successfully");
			//dbHelper.close();
		}	
		
		public Cursor getAllParentRecords(){
			Cursor cursor;
			////db = dbHelper.getWritableDatabase();
			cursor = db.query(PARENT_RECORDS, new String[] {PARENT_NAME,PARENT_ADDRESS,PARENT_CONTACT,PARENT_EMAIL}, null, null, null, null, null);
			////dbHelper.close();
			
			//dbHelper.close();
			return cursor;
		}
}
