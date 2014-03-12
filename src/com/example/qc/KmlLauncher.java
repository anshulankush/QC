package com.example.qc;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;

public class KmlLauncher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_kml_launcher);
		Bundle extras = getIntent().getExtras();
		String selectedTitle="";
		if (extras != null) {
			selectedTitle = extras.getString("trail");
			//System.out.println("s: "+selectedTitle);
		}
//		Intent mapIntent = new Intent(Intent.ACTION_VIEW);
		Uri uri1 = Uri.parse("geo:0,0?q=http://www.public.asu.edu/~akchawl1/"+selectedTitle);
//		mapIntent.setData(uri1);
//		
//		startActivity(Intent.createChooser(mapIntent, "Sample"));

		
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri1);
		if (isAppInstalled("com.google.android.apps.maps")) {
		    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		}
		startActivity(intent);
	}
	// helper function to check if Maps is installed
	private boolean isAppInstalled(String uri) {
	    PackageManager pm = getApplicationContext().getPackageManager();
	    boolean app_installed = false;
	    try {
	        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	        app_installed = true;
	    } catch (PackageManager.NameNotFoundException e) {
	        app_installed = false;
	    }
	    return app_installed;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_atrail, menu);
		return true;
	}

}
