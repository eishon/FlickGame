package com.eishon.flickgame;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.VelocityTracker;

public class Objects {
	
	float centerX,centerY;
	float velocityX,velocityY;
	int height,width;
	int topLeft,topRight,bottomLeft,bottomRight;
	Bitmap objectsBitmap;
	Paint objectPaint;
	
	public Objects(Bitmap bitmap) {
		
		objectsBitmap=bitmap;
		centerX=centerY=0;
		
		height=objectsBitmap.getHeight();
		width=objectsBitmap.getWidth();
		
		velocityX=velocityY=0;
		
		objectPaint=new Paint();

	}

	public Objects(Bitmap bitmap,int cX,int cY) {
		this(bitmap);
		centerX=cX;
		centerY=cY;
		
		
	}

	public Objects(Bitmap bitmap,Point center) {
		this(bitmap, center.x, center.y);

	}
	
	public void setCenter(Point centerPoint) {
		centerX=centerPoint.x;
		centerY=centerPoint.y;
	}
	
	
	public void setVelocity(VelocityTracker velocityTracker) {
		velocityX=velocityTracker.getXVelocity();
		velocityY=velocityTracker.getYVelocity();
		
	}
	
	public void setCVelocity(int vX,int vY) {
		velocityX=(float)vX;
		velocityY=(float)vY;
		
	}

}
