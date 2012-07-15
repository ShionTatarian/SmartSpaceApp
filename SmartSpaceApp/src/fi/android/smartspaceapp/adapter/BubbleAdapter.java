package fi.android.smartspaceapp.adapter;

import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.model.Bubble;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BubbleAdapter extends ArrayAdapter<Bubble> {

	private final int TYPE_COUNT = 2;
	
	
	public BubbleAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.bubble_gallery_page, parent);
			convertView.setTag(new ViewHolder(convertView));
		}
		
		Bubble b = getItem(position);
		
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.name.setText(b.getName());
		
		return convertView;
	}
	
	
	private class ViewHolder {

		private TextView name;
		
		private ViewHolder(View v) {
			name = (TextView) v.findViewById(R.id.bubble_gallery_name);
		}
	}
}
