package com.example.qc;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
public class SingleBusiness extends ListActivity {
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

		ListView listView = getListView();



		// Calling async task to get json
		new GetContacts().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
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

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			jsonStr= "{\"user\":"+jsonStr+"}";
			Log.d("Response: ", "> " + jsonStr);

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

							// tmp hashmap for single contact
							HashMap<String, String> oneNewsmap = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							oneNewsmap.put(TAG_BUSINESS, business);
							oneNewsmap.put(TAG_GROUP, group);
							oneNewsmap.put(TAG_WEB, web);
							oneNewsmap.put(TAG_ADDRESS, address);
							oneNewsmap.put(TAG_CITY, city);
							oneNewsmap.put(TAG_STATE, state);
							oneNewsmap.put(TAG_ZIP, zip);
							oneNewsmap.put(TAG_PHONE, phone);

							// adding contact to contact list
							businessList.add(oneNewsmap);
							break;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

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

			setListAdapter(adapter);
		}
	}
}
