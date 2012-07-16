package fi.android.smartspaceapp.adapter;

import fi.android.smartspaceapp.R;
import fi.android.smartspaceapp.model.Bubble;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SubBubbleAdapter extends ArrayAdapter<Bubble> {

	public SubBubbleAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.sub_bubble_row, parent, false);
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
			name = (TextView) v.findViewById(R.id.sub_bubble_name);
		}
	}
	
}
