package fi.android.spacify.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	protected boolean onTop = false;
	protected boolean started = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		started = true;
	}

	@Override
	protected void onResume() {
		onTop = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		onTop = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		started = false;
		super.onDestroy();
	}


}
