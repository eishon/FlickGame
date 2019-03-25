package com.eishon.flickgame;

public class AutoAnimationThread extends Thread{
	
	private boolean flag=false;
	
	float gravityX,gravityY;
	float time;
	float reactionRatio=-1f;
	
	int width,height;
	int left,right,top,bottom;
	
	DrawingThread drawingThread;
	
	public AutoAnimationThread(DrawingThread drawingThread) {
		super();
		this.drawingThread = drawingThread;
		time=drawingThread.gameSurfaceView.time;
		updateDimension();
	}
	
	private void updateDimension() {
		width=drawingThread.allPossibleObjects.get(0).getWidth();
		height=drawingThread.allPossibleObjects.get(0).getHeight();
		
		left=drawingThread.displayX/2+width/2;
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
		
		
		for (int i = 0; i < drawingThread.allTargetObjects.size(); i++) {
			updateAllObjectsPosition(drawingThread.allTargetObjects.get(i));
			
		}
		
	}

	private void updateAllObjectsPosition(Objects objects) {
		objects.centerX+=objects.velocityX*time+0.5*gravityX*time*time;
		objects.centerY+=objects.velocityY*time+0.5*gravityY*time*time;
		
		objects.velocityX+=gravityX*time;
		objects.velocityY+=gravityY*time;
		
		reversePosition(objects);
		
	}
	
	private void reversePosition(Objects objects) {
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
			objects.centerY=bottom;
			objects.velocityY*=reactionRatio;
		}
		
	}

	public void stopThread() {
		flag=false;

	}


}
