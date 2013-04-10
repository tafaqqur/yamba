package com.tafaqqur.yamba;

import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity {
	
	static final String TAG="StatusActivity";
	EditText editStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		editStatus = (EditText) findViewById(R.id.edit_status);

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent updateIntent = new Intent(this,UpdaterService.class);
		Intent refreshIntent = new Intent(this,RefreshService.class);
		Intent preference = new Intent(this,PrefActivity.class);

		switch(item.getItemId()){

			case R.id.item_start_service:
				startService(updateIntent);
				return true;

			case R.id.item_stop_service:
				stopService(updateIntent);
				return true;
			
			case R.id.item_refresh_service:
				startService(refreshIntent);
				return true;

			case R.id.item_preference:
				startActivity(preference);
				return true;

			default:
				return false;
		}
		
	}

	public void onClick(View v){
		final String statusText = editStatus.getText().toString();
		Log.d(TAG, "Button Clicked with Text "+statusText);
		new postToTwitter().execute(statusText);
	}
	
	class postToTwitter extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				publishProgress("Updating");
				((YambaApp) getApplication()).twitter.setStatus(params[0]);
				return "Posted Successfully " + params[0];
			} catch (TwitterException e) {
				Log.e(TAG, "Failed", e);
				e.printStackTrace();
				return "Posting Failed for "+ params[0];
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();			
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			Toast.makeText(StatusActivity.this, values[0], Toast.LENGTH_LONG).show();			
		}
		
	}

}
