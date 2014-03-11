package com.example.qc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class LocalBusiness extends Activity {


	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_local_business);

		ModelBusiness.LoadModel();
		listView = (ListView) findViewById(R.id.listView1
				);
		String[] ids = new String[ModelBusiness.ItemBusiness.size()];
		for (int i= 0; i < ids.length; i++){

			ids[i] = Integer.toString(i+1);
		}
		final ItemAdapterBusiness adapter = new ItemAdapterBusiness(this,R.layout.row_business, ids);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id){
				final String item = (String) parent.getItemAtPosition(position);
				System.out.println("item Selected: "+item);
				if(item.equals("1")){
					Intent intent= new Intent(LocalBusiness.this,AllBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("2")){
					Intent intent= new Intent(LocalBusiness.this,AgricultureBusiness.class);
					startActivity(intent);
				}
//				else if(item.equals("3")){
//					Intent intent= new Intent(LocalBusiness.this,EconomicBusiness.class);
//					startActivity(intent);
//				}
				else if(item.equals("3")){
					Intent intent= new Intent(LocalBusiness.this,FamilyBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("4")){
					Intent intent= new Intent(LocalBusiness.this,FoodBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("5")){
					Intent intent= new Intent(LocalBusiness.this,HealthBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("6")){
					Intent intent= new Intent(LocalBusiness.this,RealtyBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("7")){
					Intent intent= new Intent(LocalBusiness.this,ServicesBusiness.class);
					startActivity(intent);
				}
				else if(item.equals("8")){
					Intent intent= new Intent(LocalBusiness.this,ShoppingBusiness.class);
					startActivity(intent);
				}
			}
		});
	}
}
