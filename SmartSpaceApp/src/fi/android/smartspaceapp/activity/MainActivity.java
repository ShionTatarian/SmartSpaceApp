package fi.android.smartspaceapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
        gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
        galleryAdapter = new BubbleAdapter(this);
        gallery.setAdapter(galleryAdapter);
        
    }

    private void populateAdapter() {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Cursor c = ssd.getBubblesCursor(parentID);
				
				final ArrayList<Bubble> bubbleList = new ArrayList<Bubble>();
				c.moveToFirst();
				while(!c.isAfterLast()) {
					bubbleList.add(new Bubble(c));
					c.moveToNext();
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
