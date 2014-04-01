package com.example.qc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

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

				if(item.equals("2")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "paved_paths.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Paved Paths");

					startActivity(intent);
				}
				else if(item.equals("3")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "town_unpaved_trails.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Town Unpaved Paths");



					startActivity(intent);
				}
				else if(item.equals("4")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "wash_equestrian_trails.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Wash Equestrian Trails");



					startActivity(intent);
				}
				else if(item.equals("6")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "desert_mountain.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Desert Trail");



					startActivity(intent);
				}
				else if(item.equals("7")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "founders_park.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Founders Park");



					startActivity(intent);
				}
				else if(item.equals("8")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "horseshoe_park.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Horseshoe Park");



					startActivity(intent);
				}
				
				else if(item.equals("10")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "dynamite_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Dynamite Trail");



					startActivity(intent);
				}
				else if(item.equals("11")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "goldmine_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Goldmine Trail");



					startActivity(intent);
				}
				else if(item.equals("12")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "hedgehog_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Hedgehog Trail");



					startActivity(intent);
				}
				else if(item.equals("13")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "littleleaf_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "LittleLeaf Trail");



					startActivity(intent);
				}
				else if(item.equals("14")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "malpais_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Malpais Trail");



					startActivity(intent);
				}
				else if(item.equals("15")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "moonlight_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Moonlight Trail");



					startActivity(intent);
				}
				else if(item.equals("16")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "rock_peak_wash_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Rock Peak Wash Trail");



					startActivity(intent);
				}
				else if(item.equals("17")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "san_tan_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "SanTan Trail");



					startActivity(intent);
				}
				else if(item.equals("18")){
					Intent intent= new Intent(FindATrailActivity.this,KmlLauncher.class);
					intent.putExtra("trail", "stargazer_trail.txt");
					intent.putExtra("trailnameHike", "Hike");
					intent.putExtra("trailTitle", "Stargazer Trail");



					startActivity(intent);
				}
			}
		});
	}
 }
