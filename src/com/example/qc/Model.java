package com.example.qc;
import java.util.ArrayList;

public class Model {

    public static ArrayList<Item> Items;

    public static void LoadModel() {

        Items = new ArrayList<Item>();
        Items.add(new Item(1, "news.png", "News"));
        Items.add(new Item(2, "date.png", "Events"));
        Items.add(new Item(3, "photo.png", "Send a Post Card"));
        Items.add(new Item(4, "trekking.png", "Find a Trail"));
        Items.add(new Item(5, "location.png","Local Businesses"));

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