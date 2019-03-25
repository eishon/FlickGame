package com.eishon.flickgame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DrawingThread extends Thread {
	
	private Canvas canvas;
	
	GameSurfaceView gameSurfaceView;
	AnimationThread animationThread;
	AutoAnimationThread autoAnimationThread;
	CollisionDetectionThread collisionDetectionThread;
	Random random = new Random();
	Context context;
	
	boolean threadFlag=false;
	boolean touchFlag=false;
	boolean pauseFlag=false;
	boolean touchPositionFlag=false;
	
	Bitmap backgroundBitmap;
	
	int displayX,displayY;
	int lDisplay,hDisplay,rDisplay;
	
	ArrayList<Objects> allObjects;
	ArrayList<Objects> allTargetObjects;
	ArrayList<Bitmap> allPossibleObjects;
	
	Point targetObjectAPoint,targetObjectBPoint,targetObjectCPoint;
	
	public DrawingThread(GameSurfaceView gameSurfaceView,
			Context context) {
		super();
		this.gameSurfaceView = gameSurfaceView;
		this.context = context;
		
		initialize();
		
	}



	private void initialize() {
		WindowManager windowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display=windowManager.getDefaultDisplay();
		
		Point displayDimensionPoint=new Point();
		display.getSize(displayDimensionPoint);
		
		displayX=displayDimensionPoint.x;
		displayY=displayDimensionPoint.y;
				
		lDisplay=displayY/4;
		hDisplay=(displayY/4)*3;
		rDisplay=displayY/2;
		
		backgroundBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.gameactivty_background);
		backgroundBitmap=Bitmap.createScaledBitmap(backgroundBitmap, displayX, displayY, true);
		
		targetObjectAPoint=new Point(displayX/2+(random.nextInt(displayX/2)), random.nextInt(displayY));
		targetObjectBPoint=new Point(displayX/2+(random.nextInt(displayX/2)), random.nextInt(displayY));
		targetObjectCPoint=new Point(displayX/2+(random.nextInt(displayX/2)), random.nextInt(displayY));
		
		initializeObjects();
		
	}



 	private void initializeObjects() {
		
		allObjects=new ArrayList<Objects>();
		allPossibleObjects=new ArrayList<Bitmap>();
		allTargetObjects=new ArrayList<Objects>();
		
		allPossibleObjects.add(resizedBitmap(R.drawable.basket_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.blue_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.poke_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.red_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.soccer_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.tennis_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.ultra_ball));
		allPossibleObjects.add(resizedBitmap(R.drawable.aqua_ball));
		
		allTargetObjects.add(new Objects(allPossibleObjects.get(random.nextInt(7)),targetObjectAPoint));
		
	}
	
	private Bitmap resizedBitmap(int resourceId) {
		
		Bitmap tempBitmap=BitmapFactory.decodeResource(context.getResources(), resourceId);
		tempBitmap=Bitmap.createScaledBitmap(tempBitmap,displayY/10,displayY/10, true);
		
		return tempBitmap;
	}



	@Override
	public void run() {
		threadFlag=true;
		
		animationThread=new AnimationThread(this);
		autoAnimationThread=new AutoAnimationThread(this);
		collisionDetectionThread=new CollisionDetectionThread(this);
		animationThread.start();
		autoAnimationThread.start();
		collisionDetectionThread.start();
		
		while (threadFlag) {
			canvas=gameSurfaceView.surfaceHolder.lockCanvas();
			
			try {
				synchronized (gameSurfaceView.surfaceHolder) {
					updateDisplay();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if (canvas!=null) {
					gameSurfaceView.surfaceHolder.unlockCanvasAndPost(canvas);
					
				}
				
			}
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		animationThread.stopThread();
		autoAnimationThread.stopThread();
		collisionDetectionThread.stopThread();

	}
	
	private void updateDisplay() {
		
		canvas.drawBitmap(backgroundBitmap, 0, 0, null);
		
		for (int i = 0; i < allObjects.size(); i++) {
			
			Objects tempObject=allObjects.get(i);
			canvas.drawBitmap(tempObject.objectsBitmap, tempObject.centerX-(tempObject.width/2), tempObject.centerY-(tempObject.height/2), tempObject.objectPaint);
			
		}
		
		for (int i = 0; i <allTargetObjects.size(); i++) {
			Objects tempTargetObject=allTargetObjects.get(i);
			canvas.drawBitmap(tempTargetObject.objectsBitmap, tempTargetObject.centerX-(tempTargetObject.width/2), tempTargetObject.centerY-(tempTargetObject.height/2), tempTargetObject.objectPaint);
			
		}
		
		if (pauseFlag) {
			pauseStateDrawing();
		}
		
		drawScoreData(gameSurfaceView.score,gameSurfaceView.level);
	}
	
	private void pauseStateDrawing() {
		
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);
		
		canvas.drawARGB(150, 0, 0, 0);
		canvas.drawText("PAUSED", displayX/2, displayY/2, paint);
	}



	public void drawScoreData(int scr,int lvl) {
		Paint paint=new Paint();
		paint.setTextSize(30);
		paint.setColor(Color.GREEN);
		canvas.drawText("Level : "+lvl,0,20, paint);
		canvas.drawText("Score : "+scr,0,70, paint);
		
		
	}



	public void stopThread() {
		threadFlag=false;
		
	}
	

}
