package com.nmil.qc;

import com.nmil.qc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Activities extends Activity {
	WebView mWebView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activities);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://apm.activecommunities.com/qcparksandrecreation/Home");
	}
	@Override
	public void onBackPressed() {
		Intent intent= new Intent(Activities.this,MenuPage.class);
		startActivity(intent);
	}

//	public void openBrowser(){
//		mWebView.loadUrl(text.getText().toString());
//	}
	
}
