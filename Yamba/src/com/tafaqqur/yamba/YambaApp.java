package com.tafaqqur.yamba;

import winterwell.jtwitter.Twitter;
import android.R;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application implements OnSharedPreferenceChangeListener{
	
	static final String TAG = "YambaApp";
	Twitter twitter;
	static int DELAY;
	SharedPreferences preference;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		preference = PreferenceManager.getDefaultSharedPreferences(this);
		preference.registerOnSharedPreferenceChangeListener(this);

		String username = preference.getString("username", "");
		String password = preference.getString("password", "");
		String URL = preference.getString("server", "");
		DELAY = Integer.parseInt(preference.getString("delay", "0"));
		twitter = new Twitter(username, password);
		twitter.setAPIRootUrl(URL);
		Log.d(TAG, "onCreate"+username+" "+URL+" Testing");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		String username = preference.getString("username", "");
		String password = preference.getString("password", "");
		String URL = preference.getString("server", "");
		DELAY = Integer.parseInt(preference.getString("delay", "0"));
		twitter = new Twitter(username, password);
		twitter.setAPIRootUrl(URL);		
	}


}
