package fi.android.spacify.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fi.android.spacify.R;

import fi.android.spacify.gesture.GestureInterface;
import fi.android.spacify.gesture.SimpleTouchGesture;
import fi.android.spacify.model.Bubble;
import fi.android.spacify.service.WorkService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * 
 * @author Tommy
 * 
 */
public class BubbleSurface extends SurfaceView implements SurfaceHolder.Callback {

	private final String TAG = "BubbleSurface";
	private final WorkService ws = WorkService.getInstance();

	/**
	 * Maximum refresh rate is 60 frames per second.
	 */
	private final int MAX_REFRESH_RATE = 1000 / 45;

	private GraphicThread graphicThread;
	private MovementThread movementThread;
	private final HashMap<Integer, SimpleTouchGesture<Bubble>> gestureList = new HashMap<Integer, SimpleTouchGesture<Bubble>>();
	
	private final HashMap<String, GestureInterface> gestureMap = new HashMap<String, GestureInterface>();
	
	private Paint bubblePaint;
	
	private int maxX = 0;
	private int maxY = 0;

	private final List<Bubble> bubbles = new ArrayList<Bubble>();
	
	private final HashMap<Integer, Bubble> movingBubbles = new HashMap<Integer, Bubble>();

	public static class BubbleEvents {
		public static final String SINGLE_TOUCH = "singleTouch";
	}
	
	/**
	 * Constructor.
	 * 
	 * @param context
	 * @param background
	 */
	public BubbleSurface(Context context, AttributeSet attrs) {
		super(context, attrs);

		getHolder().addCallback(this);
		getHolder().setFormat(PixelFormat.RGBA_8888);
		
		bubblePaint = new Paint();
		bubblePaint.setColor(Color.WHITE);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		clearCanvas(canvas);

		for (Bubble b : bubbles) {
			canvas.drawCircle(b.x, b.y, b.radius, b.paint);
		}
	}

	private void calculateGridValues(Canvas c) {
		maxX = c.getWidth();
		maxY = c.getHeight();
	}

	private void clearCanvas(Canvas c) {
		c.drawColor(Color.BLACK);
	}

	private void startThreads() {
		if (graphicThread == null) {
			graphicThread = new GraphicThread(getHolder(), this);
			graphicThread.setRunning(true);
			graphicThread.start();
		}

		if (movementThread == null) {
			movementThread = new MovementThread();
			movementThread.setRunning(true);
			movementThread.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		holder.setFormat(PixelFormat.RGBA_8888);
		Canvas c = holder.lockCanvas();
		calculateGridValues(c);
		holder.unlockCanvasAndPost(c);

		startThreads();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopThreads();
	}

	private void stopThreads() {
		if (graphicThread != null) {
			graphicThread.setRunning(false);
			graphicThread = null;
		}

		if (movementThread != null) {
			movementThread.setRunning(false);
			movementThread = null;
		}
	}

	private final int THREAD_KILL_DELAY = 1000;

	class MovementThread extends Thread {

		private long lastUpdate = 0;
		private boolean run = false;

		public void setRunning(boolean run) {
			this.run = run;
		}

		public MovementThread() {
		}

		@Override
		public void run() {
			while (run) {
				long pulse = System.currentTimeMillis();
				lastUpdate = pulse;
			}
		}
	}

	class GraphicThread extends Thread {
		private SurfaceHolder holder;
		private BubbleSurface surface;
		private long lastUpdate = 0;

		private boolean run = false;

		public GraphicThread(SurfaceHolder surfaceHolder, BubbleSurface surface) {
			holder = surfaceHolder;
			this.surface = surface;
		}

		public void setRunning(boolean run) {
			this.run = run;
		}

		public SurfaceHolder getSurfaceHolder() {
			return holder;
		}

		@Override
		public void run() {
			Canvas c;
			while (run) {
				c = null;
				try {
					if (lastUpdate + MAX_REFRESH_RATE <= System.currentTimeMillis()) {
						if (holder != null) {
							c = holder.lockCanvas(null);
							if (c != null && surface != null) {
								surface.onDraw(c);
								lastUpdate = System.currentTimeMillis();
							}
						}
					}
				} catch (Exception e) {
					Log.e(TAG, "Error drawing!", e);
				} finally {
					// make sure to always release canvas
					if (c != null && holder != null) {
						holder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	private SurfaceTouchInterface callback;

	/**
	 * Add {@link GridTouchInterface} callback to this {@link BubbleSurface}.
	 * 
	 * @param callback
	 */
	public void addCallback(SurfaceTouchInterface callback) {
		this.callback = callback;
	}

	/**
	 * Set Gesture for event. Event corresponds to {@link BubbleEvents}.
	 * 
	 * @param name
	 * @param gesture
	 */
	public void setGesture(String event, GestureInterface gesture) {
		gestureMap.put(event, gesture);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(callback != null) {
			callback.onSurfaceTouch(event);
		}
		int pointerIndex = ((event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) 
			     >> MotionEvent.ACTION_POINTER_ID_SHIFT);
		int pointerID = event.getPointerId(pointerIndex);
		int action = (event.getAction() & MotionEvent.ACTION_MASK);
		   
		int x = (int) event.getX(pointerIndex);
		int y = (int) event.getY(pointerIndex);
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			Bubble bHit = hitBubble((int)event.getX(pointerIndex), (int)event.getY(pointerIndex));
			if(bHit != null) {
				GestureInterface singleTouch = gestureMap.get(BubbleEvents.SINGLE_TOUCH);
				if(singleTouch != null) {
					SimpleTouchGesture<Bubble> gesture = new SimpleTouchGesture<Bubble>(singleTouch);
					gesture.onTouchDown(bHit);
					gestureList.put(pointerIndex, gesture);
				}
				
				synchronized (movingBubbles) {
					movingBubbles.put(pointerIndex, bHit);
				}
			} else {
				Bubble b = new Bubble();
				b.x = x;
				b.y = y;
				
				bubbles.add(b);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			synchronized (movingBubbles) {
				for(Integer key : movingBubbles.keySet()) {
					Bubble b = movingBubbles.get(key);
					if(b != null && key < event.getPointerCount()) {
						b.x = (int) event.getX(key);
						b.y = (int) event.getY(key);
					}
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			final SimpleTouchGesture<Bubble> gesture = gestureList.remove(pointerIndex);
			final Bubble b;
			synchronized (movingBubbles) {
				b = movingBubbles.remove(pointerIndex);
			}
			if(b != null && gesture != null) {
				ws.postWork(new Runnable() {
					
					@Override
					public void run() {
						gesture.onTouchUp(b);
					}
				});
			}
			break;
		}
		
		return true;
	}
	
	private Bubble hitBubble(int x, int y) {
		for(Bubble b : bubbles) {
			if(distance(x, y, b.x, b.y) < b.radius) {
				return b;
			}
		}
		
		return null;
	}

	private int distance(int x, int y, int bx, int by) {
		double dx = Math.pow(x-bx, 2);
		double dy = Math.pow(y-by, 2);
		
		return (int) Math.sqrt(dx+dy);
	}
	
}
