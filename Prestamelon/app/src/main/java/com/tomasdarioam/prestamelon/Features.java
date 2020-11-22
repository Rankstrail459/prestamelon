package com.tomasdarioam.prestamelon;

import java.util.EnumMap;
import java.util.Map;

public class Features {
    public enum Grades {
        LOW, MEDIUM, HIGH
    }

    public enum CarTypes {
        FAMILY, SPORTS
    }

    public static final Map<Grades, Integer> QUALITY_BONUSES;

    public static final Map<CarTypes, Integer>CAR_TYPES_BONUSES;

    static {
        QUALITY_BONUSES = new EnumMap<Grades, Integer>(Grades.class);
        QUALITY_BONUSES.put(Grades.LOW, 0);
        QUALITY_BONUSES.put(Grades.MEDIUM, 5);
        QUALITY_BONUSES.put(Grades.HIGH, 10);

        CAR_TYPES_BONUSES = new EnumMap<CarTypes, Integer>(CarTypes.class);
        CAR_TYPES_BONUSES.put(CarTypes.FAMILY, 0);
        CAR_TYPES_BONUSES.put(CarTypes.SPORTS, 20);
    }
}

class Feature {
    public Object Feature;
    public int Bonus;

    public Feature(Object feature) {
        Feature = feature;
    }
}

class IncorrectBonusException extends Exception {
    public IncorrectBonusException() {
        super();
    }
}

class ItemFeatures {
    private Feature mQuality;
    //private Feature Antiquity;

    public ItemFeatures() {

    }

    public ItemFeatures(Features.Grades quality) {
        setQuality(quality);
    }

    public Features.Grades getQuality() {
        return (Features.Grades) mQuality.Feature;
    }

    public void setQuality(Features.Grades quality) {
        mQuality = new Feature(quality);

        mQuality.Bonus = Features.QUALITY_BONUSES.get(quality);
    }

    public float calculateQualityBonus() {
        return mQuality.Bonus / 100F;
    }

    public int calculateCommonBonuses(int basePrice) {
        int bonuses = 0;

        bonuses += basePrice * calculateQualityBonus();

        return bonuses;
    }

}

class CarFeatures extends ItemFeatures{
    private Feature mCarType;

    public CarFeatures(Features.Grades quality, Features.CarTypes carType) {
        super(quality);
        setCarType(carType);
    }

    public Features.CarTypes getCarType() {
        return (Features.CarTypes) mCarType.Feature;
    }

    public void setCarType(Features.CarTypes carType) {
        mCarType = new Feature(carType);

        mCarType.Bonus = Features.CAR_TYPES_BONUSES.get(mCarType.Feature);
    }

    public float calculateCarTypeBonus() {
        return mCarType.Bonus / 100F;
    }

    public int calculateCarBonuses(int basePrice) {
        int bonuses = 0;

        bonuses += basePrice * calculateCarTypeBonus();

        return bonuses;
    }

    //Calculate bonus by basePrice
}
