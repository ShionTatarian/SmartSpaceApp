package fi.android.smartspaceapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.adapter.BubbleAdapter;
import fi.android.smartspaceapp.adapter.SubBubbleAdapter;
import fi.android.smartspaceapp.db.SmartSpaceDatabase;
import fi.android.smartspaceapp.model.Bubble;
import fi.android.smartspaceapp.view.ListGallery;

public class GalleryActivity extends Activity {
	
	private final SmartSpaceDatabase ssd = SmartSpaceDatabase.getInstance();
	
	private ListGallery gallery;
	private ListView subBubbleList;
	private SubBubbleAdapter subBubbleAdapter;
	private BubbleAdapter galleryAdapter;
	private Long parentID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        
        gallery = (ListGallery) findViewById(R.id.main_bubble_gallery);
        gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				Intent intent = new Intent(GalleryActivity.this, BubbleActivity.class);
				intent.putExtra(Bubble.Intent, galleryAdapter.getItem(position));
				startActivity(intent);
				
				return true;
			}

		});
        galleryAdapter = new BubbleAdapter(this);
        gallery.setAdapter(galleryAdapter);
        
        subBubbleList = (ListView) findViewById(R.id.sub_bubble_list);
        subBubbleAdapter = new SubBubbleAdapter(this);
        subBubbleList.setAdapter(subBubbleAdapter);
        
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				parentID = galleryAdapter.getItem(position).getId();
				populateSubGallery();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				parentID = null;
			}
		});
    }

    private void populateGallery() {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Cursor c = ssd.getBubblesCursor(null);
				
				final ArrayList<Bubble> bubbleList = new ArrayList<Bubble>();
				c.moveToFirst();
				while(!c.isAfterLast()) {
					bubbleList.add(new Bubble(c));
					c.moveToNext();
				}
				c.close();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						galleryAdapter.clear();
						for(Bubble b : bubbleList) {
							galleryAdapter.add(b);
						}
						
						galleryAdapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
    }
    
    private void populateSubGallery() {
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
				c.close();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						subBubbleAdapter.clear();
						for(Bubble b : bubbleList) {
							subBubbleAdapter.add(b);
						}
						
						subBubbleAdapter.notifyDataSetChanged();
						
					}
				});
			}
		}).start();
    }
    
    @Override
    protected void onResume() {
    	populateGallery();
    	populateSubGallery();
    	
    	super.onResume();
    }
    
    public void onNewMainBubbleClick(View view) {
    	Intent intent = new Intent(this, BubbleActivity.class);
    	startActivity(intent);
    }
    
    public void onNewSubBubbleClick(View view) {
    	Intent intent = new Intent(this, BubbleActivity.class);
    	Bubble b = new Bubble();
    	b.setParentID(parentID);
    	intent.putExtra(Bubble.Intent, b);
    	startActivity(intent);
    }
}
