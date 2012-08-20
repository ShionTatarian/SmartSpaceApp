package fi.android.spacify.activity.bubblespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fi.android.spacify.R;
import fi.android.spacify.model.ControlAction;

public class ControlAdapter extends ArrayAdapter<ControlAction> {

	public ControlAdapter(Context context) {
		super(context, 0);
	}
	
	protected class ViewHolder {
		ImageView image;
		TextView name;
		
		public ViewHolder(View v) {
			image = (ImageView) v.findViewById(R.id.popup_control_image);
			name = (TextView) v.findViewById(R.id.popup_control_name);
		}
		
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_control_row, parent, false);
			convertView.setTag(new ViewHolder(convertView));
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		
		ControlAction control = getItem(position);
		
		holder.name.setText(control.getName());
		
		return convertView;
	}

}
