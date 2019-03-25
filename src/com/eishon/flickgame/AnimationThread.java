package com.eishon.flickgame;

import android.graphics.Point;

public class AnimationThread extends Thread{
	
	private boolean flag=false;
	
	float gravityX,gravityY;
	float time=0.3f;
	float reactionRatio=-0.7f;
	
	int width,height;
	int left,right,top,bottom;
	
	DrawingThread drawingThread;
	
	public AnimationThread(DrawingThread drawingThread) {
		super();
		this.drawingThread = drawingThread;
		updateDimension();
	}
	
	private void updateDimension() {
		width=drawingThread.allPossibleObjects.get(0).getWidth();
		height=drawingThread.allPossibleObjects.get(0).getHeight();
		
		left=width/2;
		top=height/2;
		right=drawingThread.displayX-(width/2);
		bottom=drawingThread.displayY-(height/2);
		
	}

	@Override
	public void run() {
		flag=true;
		
		while (flag) {
			
			updateAllPosition();
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private void updateAllPosition() {
		gravityX=GameActivity.getGravityY();
		gravityY=GameActivity.getGravityX();
		
		if (drawingThread.touchFlag) {
			for (int i = 0; i < drawingThread.allObjects.size()-1; i++) {
				updateAllObjectsPosition(drawingThread.allObjects.get(i),i);
				
			}
			
		}else {
			for (int i = 0; i < drawingThread.allObjects.size(); i++) {
				updateAllObjectsPosition(drawingThread.allObjects.get(i),i);
				
		}	
			
		}
		
	}

	private void updateAllObjectsPosition(Objects objects,int position) {
		objects.centerX+=objects.velocityX*time+0.5*gravityX*time*time;
		objects.centerY+=objects.velocityY*time+0.5*gravityY*time*time;
		
		objects.velocityX+=gravityX*time;
		objects.velocityY+=gravityY*time;
		
		reversePosition(objects,position);
		
	}
	
	private void reversePosition(Objects objects,int Id) {
		//x-axis
		if (objects.centerX<left) {
			objects.centerX=left;
			objects.velocityX*=reactionRatio;
			
		}else if (objects.centerX>right) {
			objects.centerX=right;
			objects.velocityX*=reactionRatio;
		}
		//y-axis
		if(objects.centerY<top){
			objects.centerY=top;
			objects.velocityY*=reactionRatio;
		}else if (objects.centerY>bottom) {
			drawingThread.allObjects.remove(Id);
		}
		
	}

	public void stopThread() {
		flag=false;

	}
	
	public boolean collition(Objects objects,Point ballPosition) {
		if (flag) {
			return true;
		}
		
		return false;
	}

}
