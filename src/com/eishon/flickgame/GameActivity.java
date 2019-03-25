package com.eishon.flickgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View.OnTouchListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class GameActivity extends Activity {
	
	GameSurfaceView gameSurfaceView;
	
	Sensor accelerometerSensor;
	SensorManager sensorManager;
	SensorEventListener sensorEventListener;

	private static float gravityX,gravityY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		initializeSensors();
		
		setContentView(R.layout.activity_game);
		
		gameSurfaceView=(GameSurfaceView) findViewById(R.id.myGameView);
		
		final Button pauseButton=(Button) findViewById(R.id.pauseButton);
		final Button restartButton=(Button) findViewById(R.id.restartButton);
		final Button exitButton=(Button) findViewById(R.id.exitButton);
		
		pauseButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(gameSurfaceView.drawingThread.pauseFlag==false){
						gameSurfaceView.drawingThread.animationThread.stopThread();
						gameSurfaceView.drawingThread.autoAnimationThread.stopThread();
						gameSurfaceView.drawingThread.pauseFlag=true;
						pauseButton.setBackgroundResource(R.drawable.play_button);
						
					}else {
						gameSurfaceView.drawingThread.animationThread=new AnimationThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.autoAnimationThread=new AutoAnimationThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.animationThread.start();
						gameSurfaceView.drawingThread.autoAnimationThread.start();
						gameSurfaceView.drawingThread.pauseFlag=false;
						pauseButton.setBackgroundResource(R.drawable.pause_button);
						
					}
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		restartButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					restartGame();
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		exitButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopGame();
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		
	}
	
	private void restartGame() {
		gameSurfaceView.drawingThread.allObjects.clear();
		gameSurfaceView.i=0;
		gameSurfaceView.score=0;
		gameSurfaceView.level=1;
		gameSurfaceView.time=0.05f;
		
	}
	
	private void stopGame() {
		this.finish();
		gameSurfaceView.i=0;
	}

	private void initializeSensors() {
		sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		
		sensorEventListener=new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
					gravityX=event.values[0];
					gravityY=event.values[1];
					
				}
				
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};
		
		accelerometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		startUsingSensors();
		
	}

	private void startUsingSensors() {
		sensorManager.registerListener(sensorEventListener, accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	private void stopUsingSensors(){
		sensorManager.unregisterListener(sensorEventListener);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		stopUsingSensors();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		startUsingSensors();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		stopUsingSensors();
		super.onStop();
	}

	public static float getGravityX() {
		return gravityX;
	}

	public static void setGravityX(float gravityX) {
		GameActivity.gravityX = gravityX;
	}

	public static float getGravityY() {
		return gravityY;
	}

	public static void setGravityY(float gravityY) {
		GameActivity.gravityY = gravityY;
	}

}
