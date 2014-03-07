package com.example.qc;

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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SingleBusiness extends FragmentActivity {
	String selectedTitle="";

	private ProgressDialog pDialog;

	// URL to get oneNews JSON
	private static String url = "http://nmil.knsclients.com/business/list";

	// JSON Node names
	private static final String TAG_BUSINESS = "business";
	private static final String TAG_GROUP = "group";
	private static final String TAG_WEB = "web";
	private static final String TAG_ADDRESS = "address1";
	private static final String TAG_CITY = "city";
	private static final String TAG_STATE = "state";
	private static final String TAG_ZIP = "zipcode";
	private static final String TAG_PHONE = "phone";
	// oneNews JSONArray
	JSONArray oneBusiness = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> businessList;

	//	@Override
	//	public void onCreate(Bundle savedInstanceState) {
	//
	//		super.onCreate(savedInstanceState);
	//		setContentView(R.layout.activity_single_business);
	//		Bundle extras = getIntent().getExtras();
	//		if (extras != null) {
	//			selectedTitle = extras.getString(TAG_BUSINESS);
	//			System.out.println("s: "+selectedTitle);
	//		}
	//		businessList = new ArrayList<HashMap<String, String>>();
	//		ListView listView = getListView();
	//		// Calling async task to get json
	//		new GetContacts().execute();
	//	}
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
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					SingleBusiness.this, businessList,
					R.layout.list_item_2, new String[] { TAG_BUSINESS,TAG_GROUP,TAG_WEB, TAG_ADDRESS,
							TAG_CITY, TAG_STATE, TAG_ZIP, TAG_PHONE}, new int[] { R.id.business,
							R.id.group, R.id.web, R.id.address, R.id.city, R.id.state, R.id.zip, R.id.phone });

			//			setListAdapter(adapter);
		}
	}

	public static JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			address = address.replaceAll(" ","%20");    

			HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();


			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}
	public static double getLatLong(JSONObject jsonObject, String latOrLong) {

		try {

			return ((JSONArray)jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble(latOrLong);


		} catch (JSONException e) {
			return -1;

		}
	}




	private GoogleMap mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_business);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			selectedTitle = extras.getString(TAG_BUSINESS);
			System.out.println("s: "+selectedTitle);
		}
		businessList = new ArrayList<HashMap<String, String>>();
		//ListView listView = getListView();
		// Calling async task to get json
		new GetContacts().execute();

		ServiceHandler sh = new ServiceHandler();
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
		jsonStr= "{\"user\":"+jsonStr+"}";
		Log.d("Response: ", "> " + jsonStr);
		double lat = 0;
		double lng = 0;
		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				// Getting JSON Array node
				oneBusiness = jsonObj.getJSONArray("user");

				// looping through All Contacts
				for (int i = 0; i < oneBusiness.length(); i++) {
					JSONObject c = oneBusiness.getJSONObject(i);
					String business = c.getString(TAG_BUSINESS);
					if(selectedTitle.equals(business)){
						System.out.println("found!!!");
						String group = c.getString(TAG_GROUP);
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
//						
						dirName.setText(business);
						dirAddress.setText(address+", "+city+", "+state+", "+zip);
						dirPhone.setText(phone);
						dirWeb.setText(web);
						
						dirWeb.setClickable(true);
						dirWeb.setMovementMethod(LinkMovementMethod.getInstance());
						String text = "<a href='"+web+"'>"+web+"</a>";
						dirWeb.setText(Html.fromHtml(text));

						// tmp hashmap for single contact
//						HashMap<String, String> oneNewsmap = new HashMap<String, String>();
//
//						// adding each child node to HashMap key => value
//						oneNewsmap.put(TAG_BUSINESS, business);
//						oneNewsmap.put(TAG_GROUP, group);
//						oneNewsmap.put(TAG_WEB, web);
//						oneNewsmap.put(TAG_ADDRESS, address);
//						oneNewsmap.put(TAG_CITY, city);
//						oneNewsmap.put(TAG_STATE, state);
//						oneNewsmap.put(TAG_ZIP, zip);
//						oneNewsmap.put(TAG_PHONE, phone);
//						businessList.add(oneNewsmap);
						JSONObject j=getLocationInfo(address+", "+city+", "+state+", "+zip);
						lng=getLatLong(j, "lng");
						lat=getLatLong(j, "lat");
						System.out.println(lat+", "+lng);


					}
				}
			} 
			catch (Exception e) {

			}
		}
		
		setUpMapIfNeeded(lat,lng);
	}	

	private void setUpMapIfNeeded(double lat, double lng) {
		//        if (mMap != null) {
		//            return;
		//        }
//		double lat=33.45427;
//		double lng=-112.073057;
		LatLng HAMBURG = new LatLng(lat,lng);

		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (mMap!=null){
			Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
					.title(selectedTitle));
			////			Marker kiel = map.addMarker(new MarkerOptions()
			////			.position(KIEL)
			////			.title("Kiel")
			////			.snippet("Kiel is cool")
			////			.icon(BitmapDescriptorFactory
			////					.fromResource(R.drawable.ic_launcher)));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

			// Zoom in, animating the camera.
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		}

		//        if (mMap == null) {
		//            return;
		//        }
		// Initialize map options. For example:
		// mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}

}

//SHA1: 74:6D:DD:68:81:1F:72:E3:1B:FB:6D:E1:94:B5:58:8E:3C:88:80:59
//API key: AIzaSyDZM5V__z00xj6rLAmOxqlii3R60QDx7ug
