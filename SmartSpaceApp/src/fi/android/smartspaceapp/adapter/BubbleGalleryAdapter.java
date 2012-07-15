package fi.android.smartspaceapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.model.Bubble;

public class BubbleGalleryAdapter extends CursorAdapter {

	public BubbleGalleryAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public void bindView(View view, Context ctx, Cursor c) {
		ViewHolder holder = (ViewHolder) view.getTag();
		Bubble b = new Bubble(c);
		
		holder.name.setText(b.getName());
	}

	@Override
	public View newView(Context ctx, Cursor c, ViewGroup parent) {
		View v = LayoutInflater.from(ctx).inflate(R.layout.bubble_gallery_page, parent, false);
		v.setTag(new ViewHolder(v));
		bindView(v, ctx, c);
		
		return v;
	}

	private class ViewHolder {

		private TextView name;
		
		private ViewHolder(View v) {
			name = (TextView) v.findViewById(R.id.bubble_gallery_name);
		}
	}
	
}
