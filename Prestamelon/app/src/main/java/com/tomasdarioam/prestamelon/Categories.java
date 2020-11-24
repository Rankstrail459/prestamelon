package com.tomasdarioam.prestamelon;

import java.util.EnumMap;
import java.util.Map;

public class Categories {

    public enum CategoriesIds {
        VEHICLE, CLOTHING_AND_ACCESSORY, READING, SUPPLIES_AND_TOOLS, SPACES, GAMES
    }
    
    static class Vehicles {

        public enum Subcategories {
            BIKE, CAR, VAN
        }

        public static final Map<Subcategories, Integer> SUBCATEGORIES_PRICES;

        static {
            SUBCATEGORIES_PRICES = new EnumMap<Subcategories, Integer>(Subcategories.class);
            SUBCATEGORIES_PRICES.put(Subcategories.BIKE, 100);
            SUBCATEGORIES_PRICES.put(Subcategories.CAR, 1000);
            SUBCATEGORIES_PRICES.put(Subcategories.VAN, 2000);
        }
    }
}

class Subcategory {
    public Categories.CategoriesIds CategoryId;
    public Object SubcategoryId;

    public Subcategory(Categories.CategoriesIds categoryId, Object subcategoryId) {
        CategoryId = categoryId;
        SubcategoryId = subcategoryId;
    }
}

class CarSubcategory extends Subcategory {
    public CarSubcategory() {
        super(Categories.CategoriesIds.VEHICLE, Categories.Vehicles.Subcategories.CAR);
    }
}


