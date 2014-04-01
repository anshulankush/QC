package com.nmil.qc;
import java.util.ArrayList;

public class ModelKML {

    public static ArrayList<ItemKML> Items;

    public static void LoadModel() {

        Items = new ArrayList<ItemKML>();
        Items.add(new ItemKML(1, "", "QC Trail"));
        Items.add(new ItemKML(2, "arrow.png", "Paved Paths"));
        Items.add(new ItemKML(3, "arrow.png", "Town Unpaved Trails"));
        Items.add(new ItemKML(4, "arrow.png", "Wash Equestrian Trails"));
        
        Items.add(new ItemKML(5, "", "QC Parks"));

        Items.add(new ItemKML(6, "arrow.png", "Desert Trail"));
        Items.add(new ItemKML(7, "arrow.png","Founders Park"));
        Items.add(new ItemKML(8, "arrow.png", "Horseshoe Park"));
        
        Items.add(new ItemKML(9, "", "San Tan Trail"));

        Items.add(new ItemKML(10, "arrow.png", "Dynamite Trail"));
        Items.add(new ItemKML(11, "arrow.png","Goldmine Trail"));
        Items.add(new ItemKML(12, "arrow.png", "Hedgehog Trail"));
        Items.add(new ItemKML(13, "arrow.png","LittleLeaf Trail"));
        Items.add(new ItemKML(14, "arrow.png","Malpais Trail"));
        Items.add(new ItemKML(15, "arrow.png","Moonlight Trail"));
        Items.add(new ItemKML(16, "arrow.png","Rock Peak Wash Trail"));
        Items.add(new ItemKML(17, "arrow.png","SanTan Trail"));
        Items.add(new ItemKML(18, "arrow.png","Stargazer Trail"));

    }

    public static ItemKML GetbyId(int id){

        for(ItemKML item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }

}