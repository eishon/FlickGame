package com.eishon.flickgame;

public class CollisionDetectionThread extends Thread{
	
	boolean flag=false;
	
	boolean detectFlag=false;
	
	DrawingThread drawingThread;
	
	int targetWidth,targetHeight,ballWidth,ballHeight;
	
	float targetX,targetY,ballX,ballY;
	
	public CollisionDetectionThread(DrawingThread drawingThread) {
		this.drawingThread=drawingThread;
		updateData();
		
	}
	
	
	
	
	private void updateData() {
		
		targetWidth=drawingThread.allPossibleObjects.get(0).getWidth();
		targetHeight=drawingThread.allPossibleObjects.get(0).getHeight();
		ballWidth=drawingThread.allPossibleObjects.get(7).getWidth();
		ballHeight=drawingThread.allPossibleObjects.get(7).getHeight();
	}




	@Override
	public void run() {
		flag=true;
		
		while (flag) {
			for (int i = 0; i <drawingThread.allObjects.size(); i++) {
				for (int j = 0; j <drawingThread.allTargetObjects.size(); j++) {
					
					Objects tempBallObjects=drawingThread.allObjects.get(i);
					Objects tempTargetObjects=drawingThread.allTargetObjects.get(j);
					
					detectCollision(tempBallObjects,tempTargetObjects);
					
					if(detectFlag){
						drawingThread.animationThread.stopThread();
						drawingThread.autoAnimationThread.stopThread();
						drawingThread.collisionDetectionThread.stopThread();
						drawingThread.gameSurfaceView.i=0;
						drawingThread.gameSurfaceView.score+=25;
						drawingThread.gameSurfaceView.level+=1;
						drawingThread.gameSurfaceView.time+=0.0175;
						drawingThread.gameSurfaceView.restartThread();
					}
				}
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}




	private void detectCollision(Objects ballObjects,Objects targetObjects) {
		
				ballX = ballObjects.centerX+ballWidth/2;
				ballY =  ballObjects.centerY+ballHeight/2;
				targetX = targetObjects.centerX;
				targetY = targetObjects.centerY;
				
				detectMethod();
		}
				
		
	
	private void detectMethod() {
		if ((ballX>targetX)&&(ballX<(targetX+targetWidth))) {
			if ((ballY>targetY)&&(ballY<(targetY+targetHeight))) {
				detectFlag = true;
			}
		} else {
			detectFlag=false;
		}
		
	}
	
	public void stopThread() {
		flag=false;
		
	}

}
