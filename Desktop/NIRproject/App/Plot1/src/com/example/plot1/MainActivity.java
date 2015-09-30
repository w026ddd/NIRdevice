package com.example.plot1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graph.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.achartengine.GraphicalView;

public class MainActivity extends Activity {


		private static GraphicalView view;
		private LineGraph line = new LineGraph();
		private static Thread thread;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			thread = new Thread() {
				public void run()
				{
					for (int i = 0; i < 20; i++) 
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Point p = MockData.getDataFromReceiver(1); // We got new data!
						line.addNewPoints(p); // Add it to our graph
						view.repaint();
					}
				}
			};
			thread.start();
		}

		@Override
		protected void onStart() {
			super.onStart();
			view = line.getView(this);
			setContentView(view);
		}

	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void lineGraphHandler (View view)
    {
    	LineGraph line = new LineGraph();
    	Intent lineIntent = line.getIntent(this);
        startActivity(lineIntent);
    }
    
    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
