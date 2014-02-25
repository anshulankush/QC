package com.example.qc;
import java.util.ArrayList;

public class ModelBusiness {

	public static ArrayList<ItemBusiness> ItemBusiness;

	public static void LoadModel() {

		ItemBusiness = new ArrayList<ItemBusiness>();
		ItemBusiness.add(new ItemBusiness(1, "bullet_point.gif", "All Businesses"));
		ItemBusiness.add(new ItemBusiness(2, "bullet_point.gif", "Agriculture"));
		//ItemBusiness.add(new ItemBusiness(3, "bullet_point.gif", "Economic Development"));
		ItemBusiness.add(new ItemBusiness(3, "bullet_point.gif", "Family"));
		ItemBusiness.add(new ItemBusiness(4, "bullet_point.gif","Food &  Dining"));
		ItemBusiness.add(new ItemBusiness(5, "bullet_point.gif","Health & Fitness"));
		ItemBusiness.add(new ItemBusiness(6, "bullet_point.gif","Realty"));
		ItemBusiness.add(new ItemBusiness(7, "bullet_point.gif","Services"));
		ItemBusiness.add(new ItemBusiness(8, "bullet_point.gif","Shopping"));

	}

	public static ItemBusiness GetbyId(int id){

		for(ItemBusiness item : ItemBusiness) {
			if (item.Id == id) {
				return item;
			}
		}
		return null;
	}

}