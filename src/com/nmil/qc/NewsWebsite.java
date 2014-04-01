package com.nmil.qc;

import com.nmil.qc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsWebsite extends Activity {
	WebView mWebView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_web_view);
		Bundle extras = getIntent().getExtras();
		String guid = null;
		if (extras != null) {
			guid = extras.getString("guid");
			//System.out.println(selectedTitle);
		}
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl(guid);
	}
	

//	public void openBrowser(){
//		mWebView.loadUrl(text.getText().toString());
//	}
	
}
