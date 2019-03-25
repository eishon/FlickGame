package com.eishon.flickgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_menu);
		
		final Button newGameButton=(Button) findViewById(R.id.newGameButton);
		final Button scoreButton=(Button) findViewById(R.id.scoreButton);
		final Button helpButton=(Button) findViewById(R.id.helpButton);
		final Button aboutButton=(Button) findViewById(R.id.aboutButton);
		
		newGameButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					newGameButton.getBackground().setAlpha(200);
					
					break;
					
				case MotionEvent.ACTION_UP:
					newGameButton.getBackground().setAlpha(255);
					
					Intent intent=new Intent(MenuActivity.this, GameActivity.class);
					startActivity(intent);
					
					break;

				default:
					break;
				}


				return false;
			}
		});
		
		scoreButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					scoreButton.getBackground().setAlpha(200);
					
					break;
					
				case MotionEvent.ACTION_UP:
					scoreButton.getBackground().setAlpha(255);
					
					Intent intent=new Intent(MenuActivity.this, ScoreActivity.class);
					startActivity(intent);
					
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		helpButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					helpButton.getBackground().setAlpha(200);

					break;

				case MotionEvent.ACTION_UP:
					helpButton.getBackground().setAlpha(255);

					Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
					startActivity(intent);

					break;

				default:
					break;
				}

				return false;
			}
		});

		aboutButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					aboutButton.getBackground().setAlpha(200);

					break;

				case MotionEvent.ACTION_UP:
					aboutButton.getBackground().setAlpha(255);

					Intent intent = new Intent(MenuActivity.this,AboutActivity.class);
					startActivity(intent);

					break;

				default:
					break;
				}

				return false;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
