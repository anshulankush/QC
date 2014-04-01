package com.nmil.qc;
import java.util.ArrayList;

public class Model {

    public static ArrayList<Item> Items;

    public static void LoadModel() {

        Items = new ArrayList<Item>();
        Items.add(new Item(1, "news.png", "NEWS"));
        Items.add(new Item(2, "informatics.png", "CALENDAR"));
        Items.add(new Item(3, "date.png", "REGISTRATION"));
        Items.add(new Item(4, "trekking.png", "OUTDOORS"));
        Items.add(new Item(5, "banknote.png","SHOP QC"));
        Items.add(new Item(6, "photo.png", "POSTCARDS"));
        Items.add(new Item(7, "location.png","CHECK IN"));
        Items.add(new Item(8, "trophy.png","YOUR BADGES"));

    }

    public static Item GetbyId(int id){

        for(Item item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }

}