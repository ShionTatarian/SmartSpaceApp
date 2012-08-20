package fi.android.spacify.activity.bubblespace;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import fi.android.spacify.R;
import fi.android.spacify.activity.BaseActivity;
import fi.android.spacify.gesture.GestureInterface;
import fi.android.spacify.view.BubbleSurface;
import fi.android.spacify.view.BubbleSurface.BubbleEvents;

/**
 * Activity to show bubbles.
 * 
 * @author Tommy
 *
 */
public class BubbleSpaceActivity extends BaseActivity {
	
	private BubbleSurface bSurface;
	private PopupControlFragment controlPopup;
	private List<Fragment> visibleFragments = new ArrayList<Fragment>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bubble_space);
		
		bSurface = (BubbleSurface) findViewById(R.id.bubblespace_surface);
		bSurface.setGesture(BubbleEvents.SINGLE_TOUCH, singleTouchUp);
		
		controlPopup = new PopupControlFragment();
	}

	private GestureInterface singleTouchUp = new GestureInterface() {
		
		@Override
		public void onGestureDetected() {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.bubblespace_control_popup, controlPopup);
			ft.commit();
			
			visibleFragments.add(controlPopup);
		}
	};
	
	@Override
	public void onBackPressed() {
		if(visibleFragments.size() > 0) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			for(Fragment f : visibleFragments) {
				ft.remove(f);
			}
			visibleFragments.clear();
			ft.commit();
		} else {
			super.onBackPressed();
		}
	};
	
}
