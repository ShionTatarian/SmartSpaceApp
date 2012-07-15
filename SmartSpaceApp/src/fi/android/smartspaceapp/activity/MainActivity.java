package fi.android.smartspaceapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.adapter.BubbleAdapter;
import fi.android.smartspaceapp.db.SmartSpaceDatabase;
import fi.android.smartspaceapp.model.Bubble;
import fi.android.smartspaceapp.view.ListGallery;

public class MainActivity extends Activity {
	
	private final SmartSpaceDatabase ssd = SmartSpaceDatabase.getInstance();
	
	private ListGallery gallery;
	private BubbleAdapter galleryAdapter;
	private Long parentID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gallery = (ListGallery) findViewById(R.id.main_bubble_gallery);
        galleryAdapter = new BubbleAdapter(this);
        
    }

    private void populateAdapter() {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Cursor c = ssd.getBubblesCursor(parentID);
				
				final ArrayList<Bubble> bubbleList = new ArrayList<Bubble>();
				for(int i = 0; i < c.getCount(); i++) {
					bubbleList.add(new Bubble(c));
				}
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						galleryAdapter.clear();
						galleryAdapter.addAll(bubbleList);
						galleryAdapter.notifyDataSetChanged();
						
					}
				});
			}
		}).start();
    }
    
    @Override
    protected void onResume() {
    	populateAdapter();
    	
    	super.onResume();
    }
    
    public void onNewBubbleClick(View view) {
    	Intent intent = new Intent(this, BubbleActivity.class);
    	startActivity(intent);
    }
    
}
