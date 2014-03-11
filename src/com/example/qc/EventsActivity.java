package com.example.qc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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

@SuppressLint("NewApi")
public class EventsActivity extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get events JSON
	private static String url = "http://54.84.43.98/rss/events";
	// JSON Node names
	private static final String TAG_TITLE = "title";
	private static final String TAG_SDATE = "sdate";
	private static final String TAG_EDATE = "edate";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_E = "start";
	private static final String TAG_S = "end";


	// events JSONArray
	JSONArray events = null;
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> eventsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		eventsList = new ArrayList<HashMap<String, String>>();

		ListView listView = getListView();

		// Listview on item click listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.title))
						.getText().toString();
				String sdate = ((TextView) view.findViewById(R.id.sdate))
						.getText().toString();
				String edate = ((TextView) view.findViewById(R.id.edate))
						.getText().toString();
				String location = ((TextView) view.findViewById(R.id.location))
						.getText().toString();
				//Starting single contact activity
				
                
//                String str_date="2014-02-05T19:00-07:00:00";
//                SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DDThh:mmTZD");

                String str_date=sdate;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");

                Date startDate = null;
				try {
					startDate = (Date)formatter.parse(str_date);
	                System.out.println("Today is sdate " +str_date);
	                System.out.println("Today is sdate " +startDate.getTime()/1000);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				str_date=edate;
                Date endDate=null;
				try {
					endDate = (Date)formatter.parse(str_date);
	                System.out.println("Today is edate " +endDate.getTime()/1000);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				Calendar cal = Calendar.getInstance();              
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
               // intent.putExtra("beginTime", startDate.getTime()/1000);
                System.out.println(sdate);
                System.out.println(cal.getTimeInMillis());
                intent.putExtra("allDay", false);
                //intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("beginTime", startDate.getTime());
                
                System.out.println("dtend  "+ startDate.getTime());

                intent.putExtra("endTime", endDate.getTime());
                intent.putExtra("title", name);
                intent.putExtra("eventLocation",location.substring(location.indexOf(" ")+1));
                startActivity(intent);
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
			pDialog = new ProgressDialog(EventsActivity.this);
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
				events = jsonObj.getJSONArray("user");

				// looping through All Contacts
				for (int i = 0; i < events.length(); i++) {
					JSONObject c = events.getJSONObject(i);

					String id = c.getString(TAG_TITLE);
					String sdate = c.getString(TAG_SDATE);
					String edate = c.getString(TAG_EDATE);
					String location = c.getString(TAG_LOCATION);

					// tmp hashmap for single contact
					HashMap<String, String> contact = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					contact.put(TAG_TITLE, id);
					contact.put(TAG_SDATE, sdate);
					contact.put(TAG_EDATE, edate);
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
					SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
					Date e1 = (Date)formatter.parse(sdate);
					String date = sdf.format(e1);
					contact.put(TAG_S, "Start Time: "+date);
					e1 = (Date)formatter.parse(edate);
					date = sdf.format(e1);
					contact.put(TAG_E, "End Time: "+date);
					contact.put(TAG_LOCATION, "Location: "+location);

					// adding contact to contact list
					eventsList.add(contact);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
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
					EventsActivity.this, eventsList,
					R.layout.list_row_events, new String[] { TAG_TITLE,TAG_SDATE,TAG_EDATE,TAG_LOCATION,TAG_S,TAG_E }, new int[] { R.id.title,R.id.sdate,R.id.edate,R.id.location,R.id.s,R.id.e});
			setListAdapter(adapter);
		}
	}
}
