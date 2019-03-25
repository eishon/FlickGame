package com.eishon.flickgame;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.VelocityTracker;

public class GameSurfaceView extends SurfaceView implements Callback{
	
	Context context;
	SurfaceHolder surfaceHolder;
	DrawingThread drawingThread;
	VelocityTracker velocityTracker;
	
	int displayX,displayY;
	int i=0;
	
	int score=0;
	int level=1;
	float time=0.05f;

	public GameSurfaceView(Context context) {
		super(context);
		
		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(this, context);
		velocityTracker=VelocityTracker.obtain();
		
	} 

	public GameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(this, context);
		velocityTracker=VelocityTracker.obtain();
		
	}

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(this, context);
		velocityTracker=VelocityTracker.obtain();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			drawingThread.start();
		} catch (Exception e) {
			restartThread();
		}
		
	}

	public void restartThread() {
		drawingThread.stopThread();
		drawingThread=null;
		drawingThread=new DrawingThread(this, context);
		drawingThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawingThread.stopThread();
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (i<3) {
			if (drawingThread.pauseFlag) {
				return true;
			}
			Point touchPoint = new Point((int) event.getX(), (int) event.getY());
			updateTouchInfo(touchPoint);
			
			if (drawingThread.touchPositionFlag) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					drawingThread.touchFlag = true;

					drawingThread.allObjects.add(new Objects(drawingThread.allPossibleObjects.get(7), touchPoint));

					break;

				case MotionEvent.ACTION_UP:
					drawingThread.touchFlag = false;

					drawingThread.allObjects.get(drawingThread.allObjects.size() - 1).setVelocity(velocityTracker);

					drawingThread.touchPositionFlag = false;

					velocityTracker.computeCurrentVelocity(40);
					i++;

					break;

				case MotionEvent.ACTION_MOVE:
					velocityTracker.addMovement(event);

					updateTouchInfo(touchPoint);

					if (drawingThread.touchPositionFlag) {drawingThread.allObjects.get(drawingThread.allObjects.size() - 1).setCenter(touchPoint);
					}
					break;

				default:
					break;
				}
			}
		}
		return true;
	}

	private void updateTouchInfo(Point point) {
		if ((point.y>drawingThread.lDisplay)&&(point.y<drawingThread.hDisplay)&&(point.x<drawingThread.rDisplay)) {
			drawingThread.touchPositionFlag=true;
		}else {
			drawingThread.touchPositionFlag=false;
		}
		
	}
	
}
