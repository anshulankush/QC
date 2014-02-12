package com.example.qc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class MenuPage extends Activity {


	ListView listView;
	@Override
	public void onBackPressed() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_page);

		Model.LoadModel();
		listView = (ListView) findViewById(R.id.listView);
		String[] ids = new String[Model.Items.size()];
		for (int i= 0; i < ids.length; i++){

			ids[i] = Integer.toString(i+1);
		}

		final ItemAdapter adapter = new ItemAdapter(this,R.layout.row, ids);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				System.out.println("item Selected: "+item);
				if(item.equals("1")){
					Intent intent= new Intent(MenuPage.this,NewsActivity.class);
					startActivity(intent);
				}
				else if(item.equals("2")){
					Intent intent= new Intent(MenuPage.this,EventsActivity.class);
					startActivity(intent);
				}
				else if(item.equals("3")){
					Intent intent= new Intent(MenuPage.this,PostcardActivity.class);
					startActivity(intent);
				}
				else if(item.equals("4")){
					Intent intent= new Intent(MenuPage.this,FindATrailActivity.class);
					startActivity(intent);
				}
				else if(item.equals("5")){
					Intent intent= new Intent(MenuPage.this,LocalBusiness.class);
					startActivity(intent);
				}
			}
		});
	}
}
