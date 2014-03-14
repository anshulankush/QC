package com.example.qc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class FindATrailActivity extends Activity {


	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_atrail);

		ModelKML.LoadModel();
		listView = (ListView) findViewById(R.id.listView);
		String[] ids = new String[ModelKML.Items.size()];
		for (int i= 0; i < ids.length; i++){

			ids[i] = Integer.toString(i+1);
		}

		final ItemAdapterKML adapter = new ItemAdapterKML(this,R.layout.row_trail, ids);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				//System.out.println("item Selected: "+item);

				if(item.equals("1")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "paved_paths.txt");
					intent.putExtra("trailnameHike", "Hike");
					startActivity(intent);
				}
				else if(item.equals("2")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "town_unpaved_trails.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("3")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "wash_equestrian_trails.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("4")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "dynamite_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("5")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "goldmine_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("6")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "hedgehog_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("7")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "littleleaf_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("8")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "malpais_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("9")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "moonlight_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("10")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "rock_peak_wash_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("11")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "san_tan_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
				else if(item.equals("12")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "stargazer_trail.txt");
					intent.putExtra("trailnameHike", "Hike");


					startActivity(intent);
				}
			}
		});
	}
 }
