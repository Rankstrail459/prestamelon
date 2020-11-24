package com.tomasdarioam.prestamelon;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String mItemName;
    private String mOwnerUserUid;
    private String mOwnerUserName;
    private String mDescription;

    private Subcategory mSubcategory;

    private ItemFeatures mItemFeatures;

    private float mRating = 0;
    private List<Integer> mRatings = new ArrayList<>();

    private int mBasePrice;
    private int mMaxPrice;
    private int mRealPrice;

    private int mPhotoCounter;

    public Item() {

    }

    public Item(String itemName, String ownerUserUid, String ownerUserName, String description, ItemFeatures itemFeatures, Subcategory subcategory) {
        setItemName(itemName);
        setOwnerUserUid(ownerUserUid);
        setOwnerUserName(ownerUserName);
        setDescription(description);

        setSubcategory(subcategory);

        calculateBasePrice();

        setFeatures(itemFeatures);

        calculateMaxPrice();

        setRealPrice(mMaxPrice);
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getOwnerUserUid() {
        return mOwnerUserUid;
    }

    public void setOwnerUserUid(String ownerUserUid) {
        mOwnerUserUid = ownerUserUid;
    }

    public String getOwnerUserName() {
        return mOwnerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        mOwnerUserName = ownerUserName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Subcategory getSubcategory() {
        return mSubcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.mSubcategory = subcategory;
    }

    public ItemFeatures getFeatures() {
        return mItemFeatures;
    }

    public void setFeatures(ItemFeatures itemFeatures) {
        mItemFeatures = itemFeatures;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public void calculateRating() {
        float sum = 0;

        for (int rating : mRatings) {
            sum += rating;
        }

        sum /= mRatings.size();

        setRating(sum);
    }

    public List<Integer> getRatings() {
        return mRatings;
    }

    public void addRating(int rating) {
        mRatings.add(rating);
        calculateRating();
    }

    public void calculateBasePrice() {
        int basePrice = 0;

        switch (mSubcategory.CategoryId) {
            case VEHICLE:
                basePrice = Categories.Vehicles.SUBCATEGORIES_PRICES.get(mSubcategory.SubcategoryId);
                break;
            case CLOTHING_AND_ACCESSORY:
                break;
        }

        mBasePrice = basePrice;
    }

    public void calculateMaxPrice() {
        int maxPrice = mBasePrice;

        maxPrice += mItemFeatures.calculateCommonBonuses(mBasePrice);

        switch (mSubcategory.CategoryId) {
            case VEHICLE:
                switch ((Categories.Vehicles.Subcategories) mSubcategory.SubcategoryId) {
                    case CAR:
                        maxPrice += ((CarFeatures) mItemFeatures).calculateCarBonuses(mBasePrice);
                        break;

                    case VAN:
                        break;
                }
                break;
            case CLOTHING_AND_ACCESSORY:
                break;
        }

        mMaxPrice = maxPrice;
    }

    public int getRealPrice() {
        return mRealPrice;
    }

    public void setRealPrice(int realPrice) {
        //Controlar que no sea ni menor que 0 ni mayor que el precio máximo
        mRealPrice = realPrice;
    }

    public int getPhotoCounter() {
        return mPhotoCounter;
    }

    public void setPhotoCounter(int photoCounter) {
        mPhotoCounter = photoCounter;
    }
}





