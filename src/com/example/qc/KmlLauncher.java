package com.example.qc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("NewApi")
public class KmlLauncher extends FragmentActivity {
	String group;
	String groupName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kml_launcher);
		
		
		
		Bundle extras = getIntent().getExtras();
		String selectedTitle="";
		String selectedMainTitle="";
		
		if (extras != null) {
			groupName = extras.getString("trailnameHike");
			selectedTitle = extras.getString("trail");
			selectedMainTitle = extras.getString("trailTitle");
			System.out.println("t"+selectedTitle);
		}

		
		String  thisLine = null;
		String listOfLatLong = null;
		String ltln = "";
		String trailName1="";
		try{
			// open input stream test.txt for reading purpose.
			AssetManager assetManager = getAssets();
			String[] files = assetManager.list("");  // files from 'assets' directory
//			for(int i=0; i<files.length; i++){
//				System.out.println("\n File :"+i+" Name => "+files[i]);
//			}
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(selectedTitle), "UTF-8"));
			int i=0;
			System.out.println("in buffer");
			while ((thisLine = br.readLine()) != null) {
				if(i==0){
					trailName1=thisLine;
					System.out.println(trailName1);
				}
				else if (i==1){
					ltln=thisLine;
					System.out.println(ltln);
				}
				else{
					listOfLatLong=thisLine;
					System.out.println(listOfLatLong);
					DrawLatLong(listOfLatLong);
				}
				i++;

			}       
		}catch(Exception e){
			e.printStackTrace();
		}
		setTitle(selectedMainTitle);
		String[] trailname=trailName1.split(",");
		ltln = ltln.replaceAll("0.0 ", "");
		System.out.println(ltln);
		int trailNameCount=0;
		String[] l2=ltln.split(",");
		for(int i=0;i<l2.length-1;i+=2){
			System.out.println("in loop");
			System.out.println(l2.length);
			System.out.println(i);
			System.out.println(i+1);
			setUpMapIfNeededMarker(Double.parseDouble(l2[i+1]),Double.parseDouble(l2[i]), trailname[trailNameCount++]);
		}

		
		Button more = (Button) findViewById(R.id.button_check_in1);
		more.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(KmlLauncher.this).create(); //Read Update
				//								alertDialog.setTitle("hi");
				Pref();
				alertDialog.setMessage("Check In Successful!");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int which) {

					}
				});

				alertDialog.show();  //<-- See This!
			}
			private void Pref() {
				SharedPreferences settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
				//System.out.println("settings: "+settings);
				Editor edit = settings.edit();
				if(settings!=null){
						edit.putLong("Hike", settings.getLong("Hike", 0) + 1);
					edit.apply();
				}
			}

		});


	}
	private void DrawLatLong(String listOfLatLong) {
		listOfLatLong=listOfLatLong.replaceAll("0.0 ", "");
		String[] ll=listOfLatLong.split(",");
		prev=null;
				for(int i=0;i<ll.length-1;i+=2){
					setUpMapIfNeededLine(Double.parseDouble(ll[i+1]),Double.parseDouble(ll[i]));
				}		
	}
	LatLng prev=null;

	private void setUpMapIfNeededLine(double lat, double lng) {
		LatLng HAMBURG = new LatLng(lat,lng);
		GoogleMap mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (mMap!=null){


			if(prev!=null){
				Polyline line = mMap.addPolyline(new PolylineOptions()
				.add(prev, HAMBURG).
				width(2)
				.color(Color.RED));
			}
			prev=HAMBURG;
			//			String trailname="Dynamite Trailhead";
			//
			//			Marker trailMarker = mMap.addMarker(new MarkerOptions().position(trail).title(trailname));
		}
		//		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 13));
		//
		//		mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
	}

	private void setUpMapIfNeededMarker(double lat, double lng,String selectedTitle) {
		LatLng trail1 = new LatLng(lat,lng);
		GoogleMap mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (mMap!=null){
			String trailname=selectedTitle;

			Marker trailMarker = mMap.addMarker(new MarkerOptions().position(trail1).title(trailname));
		}
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trail1, 13));

		mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
	}

}
