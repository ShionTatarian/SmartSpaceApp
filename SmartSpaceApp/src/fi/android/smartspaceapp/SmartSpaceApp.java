package fi.android.smartspaceapp;

import fi.android.smartspaceapp.db.SmartSpaceDatabase;
import android.app.Application;

public class SmartSpaceApp extends Application {

	@Override
	public void onCreate() {
		SmartSpaceDatabase.init(this);
		
		super.onCreate();
	}
	
}
