package com.example.qc;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AllBusiness extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get events JSON
	private static String url = "http://nmil.knsclients.com/business/list";
	// JSON Node names
	private static final String TAG_BUSINESS = "business";
	
	// events JSONArray
	JSONArray businessJsonArray = null;
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> businessList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_business);

		businessList = new ArrayList<HashMap<String, String>>();

		ListView listView = getListView();

		// Listview on item click listener
		        listView.setOnItemClickListener(new OnItemClickListener() {
		 
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                    int position, long id) {
		                // getting values from selected ListItem
		                String name = ((TextView) view.findViewById(R.id.business))
		                        .getText().toString();
		                System.out.println(name);
		                //Starting single contact activity
		                Intent in = new Intent(getApplicationContext(),
		                        SingleBusiness.class);
		                in.putExtra(TAG_BUSINESS, name);
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
			pDialog = new ProgressDialog(AllBusiness.this);
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

			try {
				JSONObject jsonObj = new JSONObject(jsonStr);

				// Getting JSON Array node
				businessJsonArray = jsonObj.getJSONArray("user");

				// looping through All Contacts
				for (int i = 0; i < businessJsonArray.length(); i++) {
					JSONObject c = businessJsonArray.getJSONObject(i);

					String name = c.getString(TAG_BUSINESS);

					// tmp hashmap for single contact
					HashMap<String, String> contact = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					contact.put(TAG_BUSINESS, name);

					// adding contact to contact list
					businessList.add(contact);
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
					AllBusiness.this, businessList,
					R.layout.list_row_allbusiness, new String[] { TAG_BUSINESS  }, new int[] { R.id.business});
			setListAdapter(adapter);
		}
	}
}