package fi.android.spacify.activity.bubblespace;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import fi.android.spacify.R;
import fi.android.spacify.model.ControlAction;

public class PopupControlFragment extends Fragment {

	private ListView controlList;
	private ControlAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.popup_controls, container, false);
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		controlList = (ListView) v.findViewById(R.id.popup_control_list);
		adapter = new ControlAdapter(getActivity());
		
		ControlAction fullScreen = new ControlAction();
		fullScreen.setName(getString(R.string.control_fullscreen));
		ControlAction ca = new ControlAction();
		ca.setName("Control 2");
		
		adapter.add(fullScreen);
		adapter.add(ca);
		
		controlList.setAdapter(adapter);
		
		return v;
	}
}
