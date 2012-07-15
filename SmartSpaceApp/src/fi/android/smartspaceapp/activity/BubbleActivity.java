package fi.android.smartspaceapp.activity;

import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.db.SmartSpaceDatabase;
import fi.android.smartspaceapp.model.Bubble;
import android.os.Bundle;
import android.widget.EditText;

public class BubbleActivity extends BaseActivity {

	private Bubble bubble;
	private EditText name;
	private EditText content;
	private EditText location;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bubble_layout);
		
		bubble = (Bubble) getIntent().getSerializableExtra(Bubble.Intent);
		if(bubble == null) {
			bubble = new Bubble();
		}
		
		name = (EditText) findViewById(R.id.bubble_name);
		content = (EditText) findViewById(R.id.bubble_content);
		location = (EditText) findViewById(R.id.bubble_location);
	}
	
	@Override
	public void onBackPressed() {
		SmartSpaceDatabase ssd = SmartSpaceDatabase.getInstance();
		
		bubble.setName(name.getText().toString());
		bubble.setContent(content.getText().toString());
		bubble.setLocation(location.getText().toString());
		
		ssd.storeBubble(bubble);
		
		super.onBackPressed();
	}
	
}
