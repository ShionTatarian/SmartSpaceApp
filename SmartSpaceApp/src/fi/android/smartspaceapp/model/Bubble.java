package fi.android.smartspaceapp.model;

import java.io.Serializable;

import fi.android.smartspaceapp.db.SmartSpaceDatabase;

import android.database.Cursor;

public class Bubble implements Serializable{

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -3642361975594065000L;
	
	public static final String Intent = "bubble_intent";
	
	private long id = -1;
	private String name = "";
	private String content = "";
	private String location = "";
	private String creator = "";
	private long latitude;
	private long longitude;
	private long parentID = -1;
	
	public Bubble() {
		id = System.currentTimeMillis();
	}
	
	public Bubble(long id) {
		this.id = id;
	}
	
	public Bubble(Cursor c) {
		int idIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.ID);
		int nameIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.NAME);
		int contentIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.CONTENT); 
		int locationIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.LOCATION);
		int creatorIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.CREATOR);
		int latitudeIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.LATITUDE);
		int longitudeIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.LOCATION);
		int parentIndex = c.getColumnIndex(SmartSpaceDatabase.BubbleColumns.PARENT_ID);
		
		id = c.getLong(idIndex);
		
		if(nameIndex != -1) {
			name = c.getString(nameIndex);
		}
		if(contentIndex != -1) {
			content = c.getString(contentIndex);
		}
		if(locationIndex != -1) {
			location = c.getString(locationIndex);
		}
		if(creatorIndex != -1) {
			creator = c.getString(creatorIndex);
		}
		if(latitudeIndex != -1) {
			latitude = c.getLong(latitudeIndex);
		}
		if(longitudeIndex != -1) {
			longitude = c.getLong(longitudeIndex);
		}
		if(parentIndex != -1) {
			parentID = c.getLong(parentIndex);
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentID) {
		this.parentID = parentID;
	}

}
