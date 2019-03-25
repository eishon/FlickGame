package com.eishon.flickgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Ball {
	
	Bitmap ballBitmap;
	int ballWidth,ballHeight;
	
	Point topLeftPoint,ballPosition;
	
	DrawingThread drawingThread;
	
	public Ball(DrawingThread drawingThread,int resourceId,Point ballPosition) {
		this.drawingThread=drawingThread;
		Bitmap tempBitmap=BitmapFactory.decodeResource(drawingThread.context.getResources(), resourceId);
		tempBitmap=Bitmap.createScaledBitmap(tempBitmap,drawingThread.displayY/15, drawingThread.displayY/15, true);
		
		ballBitmap=tempBitmap;
		ballHeight=ballBitmap.getHeight();
		ballWidth=ballBitmap.getWidth();
		
		this.ballPosition=ballPosition;
		
	}

	
}
