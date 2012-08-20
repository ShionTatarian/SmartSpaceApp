package fi.android.spacify.service;

public class WorkService {

	private static final String TAG = "WorkService";
	private static WorkService instance;
	
	private WorkService() {
		
	}
	
	public static void init() {
		if(instance != null) {
			throw new IllegalStateException(TAG +" has already been initialized!");
		}
		
		instance = new WorkService();
	}
	
	//TODO: improve
	public void postWork(Runnable r) {
		new Thread(r).start();
	}

	public static WorkService getInstance() {
		if(instance == null) {
			throw new IllegalStateException(TAG + " not initialized!");
		}
		return instance;
	}
	
}
