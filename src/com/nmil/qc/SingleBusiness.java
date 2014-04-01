package com.nmil.qc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nmil.qc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SingleBusiness extends FragmentActivity {
	String selectedTitle="";

	private static ProgressDialog pDialog;

	// URL to get oneNews JSON
	private static String url = "http://54.84.43.98/business/list";

	// JSON Node names
	private static final String TAG_BUSINESS = "business";
	private static final String TAG_GROUP = "group";
	private static final String TAG_WEB = "web";
	private static final String TAG_ADDRESS = "address1";
	private static final String TAG_CITY = "city";
	private static final String TAG_STATE = "state";
	private static final String TAG_ZIP = "zipcode";
	private static final String TAG_PHONE = "phone";
	String group;
	JSONArray oneBusiness = null;
	static StringBuilder stringBuilder = new StringBuilder();
	static String addr;
	static JSONObject jsonObject;


	// Hashmap for ListView
	ArrayList<HashMap<String, String>> businessList;

	
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(SingleBusiness.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(Void... arg0) {
			//ServiceHandler sh = new ServiceHandler();


			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			//			setListAdapter(adapter);
		}
	}

	public static JSONObject getLocationInfo() {

		addr = addr.replaceAll(" ","%20");    
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + addr + "&sensor=false");
					HttpClient client = new DefaultHttpClient();
					HttpResponse response;
					stringBuilder = new StringBuilder();
					//System.out.println("a");

					response = client.execute(httppost);
					HttpEntity entity = response.getEntity();
					InputStream stream = entity.getContent();
					int b;
					while ((b = stream.read()) != -1) {
						stringBuilder.append((char) b);
					}
					//System.out.println("b");
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}

				jsonObject = new JSONObject();
				try {
					jsonObject = new JSONObject(stringBuilder.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//System.out.println("c");
		thread.start(); 
		while(thread.isAlive()){

		}
		//System.out.println("d");
		return jsonObject;
	}
	public static double getLatLong(JSONObject jsonObject, String latOrLong) {

		try {
			//System.out.println();
			return ((JSONArray)jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble(latOrLong);
		} catch (JSONException e) {
			return -1;
		}
	}

	private GoogleMap mMap;

	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.single_business, menu);

		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_business);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			selectedTitle = extras.getString(TAG_BUSINESS);
		}
		businessList = new ArrayList<HashMap<String, String>>();
		new GetContacts().execute();

		ServiceHandler sh = new ServiceHandler();
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
		jsonStr= "{\"user\":"+jsonStr+"}";
		Log.d("Response: ", "> " + jsonStr);
		double lat = 0;//33.2631;
		double lng = 0;//-111.6347; 
		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				// Getting JSON Array node
				oneBusiness = jsonObj.getJSONArray("user");
				//System.out.println("1");
				// looping through All Contacts
				for (int i = 0; i < oneBusiness.length(); i++) {
					JSONObject c = oneBusiness.getJSONObject(i);
					String business = c.getString(TAG_BUSINESS);
					if(selectedTitle.equals(business)){
						//System.out.println("found!!!");
						group = c.getString(TAG_GROUP);
						String web = c.getString(TAG_WEB);
						String address = c.getString(TAG_ADDRESS);
						String city = c.getString(TAG_CITY);
						String state = c.getString(TAG_STATE);
						String zip = c.getString(TAG_ZIP);
						String phone = c.getString(TAG_PHONE);

						TextView dirName = (TextView) findViewById(R.id.directoryName);
						TextView dirAddress = (TextView) findViewById(R.id.directoryAddress);
						TextView dirPhone = (TextView) findViewById(R.id.directoryPhone);
						TextView dirWeb = (TextView) findViewById(R.id.directoryWeb);
						dirName.setText(business);
						dirAddress.setText(address+", "+city+", "+state+", "+zip);
						dirPhone.setText(phone);
						dirWeb.setText(web);

						dirWeb.setClickable(true);
						dirWeb.setMovementMethod(LinkMovementMethod.getInstance());
						String text = "<a href='"+web+"'>"+web+"</a>";
						dirWeb.setText(Html.fromHtml(text));
						//System.out.println("2");
						addr=address+", "+city+", "+state+", "+zip;
						JSONObject j=getLocationInfo();
						//System.out.println("3");
						//System.out.println("ll: "+j);
						lng=getLatLong(j, "lng");
						lat=getLatLong(j, "lat");
						//System.out.println(lat+", "+lng);

						Button more = (Button) findViewById(R.id.button_check_in);
						more.setOnClickListener(new View.OnClickListener() {
							@SuppressWarnings("deprecation")
							public void onClick(View view) {
								AlertDialog alertDialog = new AlertDialog.Builder(SingleBusiness.this).create(); //Read Update
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
								if(settings!=null){
									long all = settings.getLong("all", 0);

//									long Family = settings.getLong("Family", 0);
//									long Shopping = settings.getLong("Shopping", 0);
//									long Hike = settings.getLong("Hike", 0);
//									long Food = settings.getLong("Food", 0);
									String grp=group.replaceAll(" ","");
									if(grp.equals("Food&Dining")){
										//System.out.println("in food");
										grp="Food";
									}
									//System.out.println("grp:   "+grp);
									Editor edit = settings.edit();
									edit.putLong("all",all + 1);
									if(grp.equals("Family") ||grp.equals("Shopping") ||grp.equals("Hike") || grp.equals("Food"))
										edit.putLong(grp, settings.getLong(grp, 0) + 1);
									edit.apply();
								}
							}

						});

						//System.out.println("set map if needed"+lat+","+lng);
						setUpMapIfNeeded(lat,lng);
					}
				}
			} 
			catch (Exception e) {

			}
		}

	}	

	private void setUpMapIfNeeded(double lat, double lng) {
		LatLng HAMBURG = new LatLng(lat,lng);

		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (mMap!=null){
			Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
					.title(selectedTitle));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

			// Zoom in, animating the camera.
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		}
	}

}

//SHA1: 74:6D:DD:68:81:1F:72:E3:1B:FB:6D:E1:94:B5:58:8E:3C:88:80:59
//API key: AIzaSyDZM5V__z00xj6rLAmOxqlii3R60QDx7ug
