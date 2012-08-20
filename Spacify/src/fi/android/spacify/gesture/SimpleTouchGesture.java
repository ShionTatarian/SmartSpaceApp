package fi.android.spacify.gesture;

import java.lang.ref.WeakReference;

import android.view.MotionEvent;

/**
 * Gesture detector for single clicking an object that requires both up and down click. 
 */
public class SimpleTouchGesture<T> {

	private final int DEFAULT_TOUCH_DELAY = 300; 
	
	private long touchDown;
	private int touchDelay = DEFAULT_TOUCH_DELAY;
	private WeakReference<T> weakObject;
	private WeakReference<GestureInterface> weakInterface;
	
	/**
	 * Constructor. Create this gesture when touching object T on ACTION_DOWN.
	 * 
	 * @param object
	 */
	public SimpleTouchGesture(GestureInterface gIf) {
		weakInterface = new WeakReference<GestureInterface>(gIf);
	}
	
	/**
	 * 
	 * 
	 * @param object
	 */
	public void onTouchDown(T object) {
		touchDown = System.currentTimeMillis();
		weakObject = new WeakReference<T>(object);
	}
	
	/**
	 * Change touch delay. If this method has not been called the default delay is 500 milli seconds.
	 * 
	 * @param delay
	 */
	public void setTouchDelay(int delay) {
		this.touchDelay = delay;
	}
	
	/**
	 * On touch up. Compares passed object with the original and that onTouchDown 
	 * event has not happened more than given touch delay before this event.
	 * 
	 * @param object
	 */
	public void onTouchUp(T object) {
		T tapDownObject = weakObject.get();
		if(tapDownObject != null && System.currentTimeMillis() < touchDown+touchDelay && object.equals(tapDownObject)) {
			GestureInterface gIf = weakInterface.get();
			if(gIf != null) {
				gIf.onGestureDetected();
			}
		}
	}
}
