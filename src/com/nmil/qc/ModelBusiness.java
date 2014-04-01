package com.nmil.qc;
import java.util.ArrayList;

public class ModelBusiness {

	public static ArrayList<ItemBusiness> ItemBusiness;

	public static void LoadModel() {

		ItemBusiness = new ArrayList<ItemBusiness>();
		ItemBusiness.add(new ItemBusiness(1, "folder.png", "All Businesses"));
		ItemBusiness.add(new ItemBusiness(2, "sun.png", "Agriculture"));
		//ItemBusiness.add(new ItemBusiness(3, "bullet_point.gif", "Economic Development"));
		ItemBusiness.add(new ItemBusiness(3, "cottage.png", "Family"));
		ItemBusiness.add(new ItemBusiness(4, "champagne.png","Food &  Dining"));
		ItemBusiness.add(new ItemBusiness(5, "barbell.png","Health & Fitness"));
		ItemBusiness.add(new ItemBusiness(6, "key.png","Realty"));
		ItemBusiness.add(new ItemBusiness(7, "help2.png","Services"));
		ItemBusiness.add(new ItemBusiness(8, "banknote.png","Shopping"));

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