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

public class NewsActivity extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get events JSON
	private static String url = "http://54.84.43.98/rss/news"; 
			//"http://nmil.knsclients.com/rss/news";
	// JSON Node names
	private static final String TAG_TITLE = "title";
	// events JSONArray
	JSONArray news = null;
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> newsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		newsList = new ArrayList<HashMap<String, String>>();

		ListView listView = getListView();

		// Listview on item click listener
		        listView.setOnItemClickListener(new OnItemClickListener() {
		 
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                    int position, long id) {
		                // getting values from selected ListItem
		                String name = ((TextView) view.findViewById(R.id.title))
		                        .getText().toString();
		                //Starting single contact activity
		                Intent in = new Intent(getApplicationContext(),
		                        SingleNews.class);
		                in.putExtra(TAG_TITLE, name);
		                startActivity(in);		 
		            }
		        });

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
			pDialog = new ProgressDialog(NewsActivity.this);
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
			System.out.println("json str: "+jsonStr);
			jsonStr= "{\"user\":"+jsonStr+"}";
			Log.d("Response: ", "> " + jsonStr);

			try {
				JSONObject jsonObj = new JSONObject(jsonStr);

				// Getting JSON Array node
				news = jsonObj.getJSONArray("user");

				// looping through All Contacts
				for (int i = 0; i < news.length(); i++) {
					JSONObject c = news.getJSONObject(i);

					String id = c.getString(TAG_TITLE);
					// tmp hashmap for single contact
					HashMap<String, String> contact = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					contact.put(TAG_TITLE, id);
					// adding contact to contact list
					newsList.add(contact);
				}
			} catch (JSONException e) {
				e.printStackTrace();
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
					NewsActivity.this, newsList,
					R.layout.list_row, new String[] { TAG_TITLE }, new int[] { R.id.title});
			setListAdapter(adapter);
		}
	}
}
