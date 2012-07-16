package fi.android.smartspaceapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import fi.android.smartspaceapp.R;

public class WelcomeActivity extends BaseActivity {

	private View events;
	private View people;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		events = findViewById(R.id.welcome_events_layout);
		people = findViewById(R.id.welcome_people_layout);
		
	}

	public void onBowseBubblesClick(View view) {
		Intent intent = new Intent(this, GalleryActivity.class);
		startActivity(intent);
	}
	
	public void onSmartMeetingClick(View view) {
		Animation inFromRight = AnimationUtils.loadAnimation(this, R.anim.in_from_right);
		Animation outToLeft = AnimationUtils.loadAnimation(this, R.anim.out_to_left);
		
		events.startAnimation(outToLeft);
		events.setVisibility(View.GONE);
		people.startAnimation(inFromRight);
		people.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onBackPressed() {
		if(people.getVisibility() == View.VISIBLE) {
			showEvents();
			return;
		}
		super.onBackPressed();
	}
	
	private void showEvents() {
		Animation inFromLeft = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
		Animation outToRight = AnimationUtils.loadAnimation(this, R.anim.out_to_right);
		
		people.startAnimation(outToRight);
		people.setVisibility(View.GONE);
		events.startAnimation(inFromLeft);
		events.setVisibility(View.VISIBLE);
	}
	
}
