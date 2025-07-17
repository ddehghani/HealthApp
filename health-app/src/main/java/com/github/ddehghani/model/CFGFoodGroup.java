package com.github.ddehghani.model;

import java.util.List;

public class CFGFoodGroup {
    private double vegtablesAndFruits, grainProducts, milkAndAlternatives, meatAndAlternatives, oilsAndFat;

    public CFGFoodGroup(double vegtablesAndFruits, double grainProducts, double milkAndAlternatives,
            double meatAndAlternatives, double oilsAndFat) {
        this.vegtablesAndFruits = vegtablesAndFruits;
        this.grainProducts = grainProducts;
        this.milkAndAlternatives = milkAndAlternatives;
        this.meatAndAlternatives = meatAndAlternatives;
        this.oilsAndFat = oilsAndFat;
    }

    public double getVegtablesAndFruits() {
        return vegtablesAndFruits;
    }

    public double getGrainProducts() {
        return grainProducts;
    }

    public double getMilkAndAlternatives() {
        return milkAndAlternatives;
    }

    public double getMeatAndAlternatives() {
        return meatAndAlternatives;
    }

    public double getOilsAndFat() {
        return oilsAndFat;
    }

    public CFGFoodGroup add(CFGFoodGroup other) {
        return new CFGFoodGroup(
                this.vegtablesAndFruits + other.getVegtablesAndFruits(),
                this.grainProducts + other.getGrainProducts(),
                this.milkAndAlternatives + other.getMilkAndAlternatives(),
                this.meatAndAlternatives + other.getMeatAndAlternatives(),
                this.oilsAndFat + other.getOilsAndFat()
        );
    }

    public static CFGFoodGroup average(List<CFGFoodGroup> foodGroups){
        if (foodGroups == null || foodGroups.isEmpty()) {
            return new CFGFoodGroup(0, 0, 0, 0, 0);
        }

        double totalVegtablesAndFruits = 0;
        double totalGrainProducts = 0;
        double totalMilkAndAlternatives = 0;
        double totalMeatAndAlternatives = 0;
        double totalOilsAndFat = 0;

        for (CFGFoodGroup group : foodGroups) {
            totalVegtablesAndFruits += group.getVegtablesAndFruits();
            totalGrainProducts += group.getGrainProducts();
            totalMilkAndAlternatives += group.getMilkAndAlternatives();
            totalMeatAndAlternatives += group.getMeatAndAlternatives();
            totalOilsAndFat += group.getOilsAndFat();
        }

        int size = foodGroups.size();
        return new CFGFoodGroup(
                totalVegtablesAndFruits / size,
                totalGrainProducts / size,
                totalMilkAndAlternatives / size,
                totalMeatAndAlternatives / size,
                totalOilsAndFat/ size
        );
    }
}
