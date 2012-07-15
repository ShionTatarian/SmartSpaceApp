package fi.android.smartspaceapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class ListGallery extends Gallery {

	private boolean scrollingHorizontally;

	public ListGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return scrollingHorizontally;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (Math.abs(distanceX) > Math.abs(distanceY) || scrollingHorizontally == true) {
			scrollingHorizontally = true;
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		return scrollingHorizontally;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			scrollingHorizontally = false;
			break;
		default:
			break;
		}
		super.onTouchEvent(event);
		return scrollingHorizontally;
	}

}
