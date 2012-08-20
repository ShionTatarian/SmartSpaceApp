package fi.android.spacify.model;

import android.graphics.Color;
import android.graphics.Paint;

public class Bubble{

	public long id;
	public int x, y;
	public float radius = 50;
	public Paint paint = new Paint();;
	
	public Bubble() {
		id = System.currentTimeMillis();
		
		int red = (int)(Math.random() * 255);
		int green = (int)(Math.random() * 255);
		int blue = (int)(Math.random() * 255);
		paint.setColor(Color.rgb(red, green, blue));
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Bubble) {
			Bubble b = (Bubble) o;
			if(b.id == id) {
				return true;
			} else {
				return false;
			}
		}
		return super.equals(o);
	}
	
}
