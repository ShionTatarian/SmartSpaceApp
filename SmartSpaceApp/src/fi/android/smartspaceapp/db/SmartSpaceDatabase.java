package fi.android.smartspaceapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import fi.android.smartspaceapp.model.Bubble;

public class SmartSpaceDatabase extends SQLiteOpenHelper {

	private final String TAG = "SmartSpaceDatabase";
	
	private static SmartSpaceDatabase instance;
	private Context ctx;
	
	private static final int VERSION = 1;
	private static final String DB_NAME = "smartspace.db";
	private static final String BUBBLE_TABLE = "bubble_tbl";
	
	public static class BubbleColumns {
		public static final String ID = "_id";
		public static final String NAME = "name";
		public static final String CONTENT = "content";
		public static final String LOCATION = "location";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String CREATOR = "creator";
		public static final String PARENT_ID = "parent_id";
	}
	
	private SmartSpaceDatabase(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.ctx = context;
	}
	
	public static void init(Context context) {
		if(instance != null) {
			throw new IllegalStateException("SmartSpaceDatabase is already initialized");
		} else {
			instance = new SmartSpaceDatabase(context);
		}
	}
	
	public static SmartSpaceDatabase getInstance() {
		if(instance == null) {
			throw new IllegalStateException("SmartSpaceDatabase is not initialized");
		} else {
			return instance;
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(BUBBLE_TABLE).append(" (");
		sql.append(BubbleColumns.ID).append(" INTEGER PRIMARY KEY,");
		sql.append(BubbleColumns.NAME).append(" TEXT,");
		sql.append(BubbleColumns.CONTENT).append(" TEXT,");
		sql.append(BubbleColumns.LOCATION).append(" TEXT,");
		sql.append(BubbleColumns.CREATOR).append(" TEXT,");
		sql.append(BubbleColumns.LATITUDE).append(" INTEGER,");
		sql.append(BubbleColumns.LONGITUDE).append(" INTEGER,");
		sql.append(BubbleColumns.PARENT_ID).append(" INTEGER DEFAULT -1");
		sql.append(")");
		
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public void storeBubble(Bubble bubble) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(BubbleColumns.ID, bubble.getId());
		values.put(BubbleColumns.NAME, bubble.getName());
		values.put(BubbleColumns.CONTENT, bubble.getContent());
		values.put(BubbleColumns.CREATOR, bubble.getCreator());
		values.put(BubbleColumns.LOCATION, bubble.getLocation());
		values.put(BubbleColumns.LATITUDE, bubble.getLatitude());
		values.put(BubbleColumns.LONGITUDE, bubble.getLongitude());
		values.put(BubbleColumns.PARENT_ID, bubble.getParentID());
		
		long change = -1;
		
		try {
			change = db.insertOrThrow(BUBBLE_TABLE, null, values);
		} catch (SQLException e) {
			String where = BubbleColumns.ID + " = " + bubble.getId();
			change = db.update(BUBBLE_TABLE, values, where, null);
		}
		
		if(change != -1) {
			Log.v(TAG, "Bubble ["+bubble.getName()+"] stored to database.");
		}
	}
	
	public Cursor getBubblesCursor(Long parentID) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = "";
		if(parentID != null) {
			selection = BubbleColumns.PARENT_ID + " = "+parentID;
		} else {
			selection = BubbleColumns.PARENT_ID +" = -1";
		}
		
		return db.query(BUBBLE_TABLE, null, selection, null, null, null, BubbleColumns.NAME);
	}

}
