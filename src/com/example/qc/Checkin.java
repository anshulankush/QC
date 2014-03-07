package com.example.qc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Checkin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		String text="Think you're the ultimate Queen Creek connoisseur? Show off your shopping and dining prowess by checking in using our <b>Shop QC</b> & <b>parks and trails</b> features! Earn badges that showcase your enthusiasm for all things Queen Creek and show your friends how it's done! Get started by visiting Shop QC and Trails.";
		TextView dirName = (TextView) findViewById(R.id.editTextcheckIn);
		dirName.setText(Html.fromHtml(text));
	}

	public void clickFunction(View v)
	{
		Intent intent = new Intent(this,LocalBusiness.class);
		startActivity(intent);
	}



}
