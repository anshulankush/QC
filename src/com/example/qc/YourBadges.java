package com.example.qc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class YourBadges extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_badges);
		SharedPreferences settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
		System.out.println("settings: "+settings);
		long all = settings.getLong("all", 0);
		long Family = settings.getLong("Family", 0);
		long Shopping = settings.getLong("Shopping", 0);
		long Hike = settings.getLong("Hike", 0);
		long Food = settings.getLong("Food", 0);
		int res;
		if(all>=25){
			res = getResources().getIdentifier("qcexpert", "drawable", this.getPackageName());
		}
		else{
			res = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview = (ImageView)findViewById(R.id.qc_expert_badge);
		imageview.setImageResource(res);
		
		int res1;
		if(Family>=3){
			res1 = getResources().getIdentifier("golocal", "drawable", this.getPackageName());
		}
		else{
			res1 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview1 = (ImageView)findViewById(R.id.go_local_badge);
		imageview1.setImageResource(res1);
		
		int res2;
		if(Family>=5){
			res2 = getResources().getIdentifier("communitychampion", "drawable", this.getPackageName());
		}
		else{
			res2 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview2 = (ImageView)findViewById(R.id.community_champion_badge);
		imageview2.setImageResource(res2);
		
		int res3;
		if(Shopping>=3){
			res3 = getResources().getIdentifier("savvyshopper", "drawable", this.getPackageName());
		}
		else{
			res3 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview3 = (ImageView)findViewById(R.id.savvy_shopper_badge);
		imageview3.setImageResource(res3);
		
		int res4;
		if(Shopping>=5){
			res4 = getResources().getIdentifier("bargainhunter", "drawable", this.getPackageName());
		}
		else{
			res4 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview4 = (ImageView)findViewById(R.id.bargain_hunter_badge);
		imageview4.setImageResource(res4);
		
		int res5;
		if(Shopping>=11){
			res5 = getResources().getIdentifier("shopaholic", "drawable", this.getPackageName());
		}
		else{
			res5 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview5 = (ImageView)findViewById(R.id.shopaholic_badge);
		imageview5.setImageResource(res5);
		
		int res6;
		if(Hike>=3){
			res6 = getResources().getIdentifier("trailblazer", "drawable", this.getPackageName());
		}
		else{
			res6 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview6 = (ImageView)findViewById(R.id.trailblazer_badge);
		imageview6.setImageResource(res6);
		
		int res7;
		if(Hike>=5){
			res7 = getResources().getIdentifier("localadventurer", "drawable", this.getPackageName());
		}
		else{
			res7 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview7 = (ImageView)findViewById(R.id.local_adventurer_badge);
		imageview7.setImageResource(res7);
		
		int res8;
		if(Hike>=9){
			res8 = getResources().getIdentifier("kingofqcmtn", "drawable", this.getPackageName());
		}
		else{
			res8 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview8 = (ImageView)findViewById(R.id.king_of_qc_mtn_badge);
		imageview8.setImageResource(res8);
		
		int res9;
		if(Food>=3){
			res9 = getResources().getIdentifier("savorbadge", "drawable", this.getPackageName());
		}
		else{
			res9 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview9 = (ImageView)findViewById(R.id.savour_badge);
		imageview9.setImageResource(res9);
		
		int res10;
		if(Food>=7){
			res10 = getResources().getIdentifier("finediner", "drawable", this.getPackageName());
		}
		else{
			res10 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview10 = (ImageView)findViewById(R.id.fine_diner_badge);
		imageview10.setImageResource(res10);
		
		int res11;
		if(Food>=11){
			res11 = getResources().getIdentifier("localfoodie", "drawable", this.getPackageName());
		}
		else{
			res11 = getResources().getIdentifier("unearned_badges", "drawable", this.getPackageName());
		}
		ImageView imageview11 = (ImageView)findViewById(R.id.local_foodie);
		imageview11.setImageResource(res11);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.your_badges, menu);
		return true;
	}

}
