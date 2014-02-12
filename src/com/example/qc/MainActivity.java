package com.example.qc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	@Override
	public void onBackPressed() {
	}

	Thread timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timer= new Thread(){
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
				Intent intent= new Intent(MainActivity.this,MenuPage.class);
				startActivity(intent);
				}
			}
		};
		timer.start();
	}
}
 