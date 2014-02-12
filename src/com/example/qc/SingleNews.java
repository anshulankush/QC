package com.example.qc;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SingleNews extends ListActivity {
	String selectedTitle="";

	private ProgressDialog pDialog;

	// URL to get oneNews JSON
	private static String url = "http://nmil.knsclients.com/rss/events";

	// JSON Node names
	private static final String TAG_TITLE = "title";
	private static final String TAG_DESC = "description";
	private static final String TAG_DATE = "pubDate";
	private static final String TAG_GUID = "guid";
	// oneNews JSONArray
	JSONArray oneNews = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> newsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_news);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			selectedTitle = extras.getString(TAG_TITLE);
			System.out.println(selectedTitle);
		}

		newsList = new ArrayList<HashMap<String, String>>();

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
			pDialog = new ProgressDialog(SingleNews.this);
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
					oneNews = jsonObj.getJSONArray("user");

					// looping through All Contacts
					for (int i = 0; i < oneNews.length(); i++) {
						JSONObject c = oneNews.getJSONObject(i);
						String title = c.getString(TAG_TITLE);
						if(selectedTitle.equals(title)){
							System.out.println("found!!!");
							String description = c.getString(TAG_DESC);
							String date = c.getString(TAG_DATE);
							String guid = c.getString(TAG_GUID);

							// tmp hashmap for single contact
							HashMap<String, String> oneNewsmap = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							oneNewsmap.put(TAG_TITLE, title);
							oneNewsmap.put(TAG_DESC, description);
							oneNewsmap.put(TAG_DATE, date);
							oneNewsmap.put(TAG_GUID, guid);
							// adding contact to contact list
							newsList.add(oneNewsmap);
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
					SingleNews.this, newsList,
					R.layout.list_item_1, new String[] { TAG_TITLE,TAG_DESC,TAG_DATE, TAG_GUID}, new int[] { R.id.title,
							R.id.description, R.id.pubDate, R.id.guid });

			setListAdapter(adapter);
		}

	}

}