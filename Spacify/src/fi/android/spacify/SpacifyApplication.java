package fi.android.spacify;

import fi.android.spacify.service.WorkService;
import android.app.Application;

public class SpacifyApplication extends Application {

	@Override
	public void onCreate() {
		WorkService.init();
		
		super.onCreate();
	}
	
}
