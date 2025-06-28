package com.github.ddehghani.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Nutrition {
    private Map<String, Double> entries;
    
    public Nutrition(Map<String, Double> entries) {
        this.entries = entries;
    }

    public Nutrition() {
        this.entries = new HashMap<>();
    }

    public double getNutrient(String nutrient) {
        return entries.getOrDefault(nutrient, 0.0);
    }

    public boolean containsNutrient(String nutrient) {
        return entries.containsKey(nutrient);
    }

    public Set<Map.Entry<String, Double>> entrySet() {
        return Collections.unmodifiableSet(entries.entrySet());
    }

    public Nutrition add(Nutrition other) {
        Map<String, Double> result = new HashMap<>();
        for (Entry<String, Double> entry : entries.entrySet()) {
            String key = entry.getKey();
            result.put(key, other.getNutrient(key) + entry.getValue());
        }

        // any remaining nutrients in the other object
        for (Map.Entry<String, Double> entry : other.entrySet()) {
            String key = entry.getKey();
            if (!containsNutrient(key)) {
                result.put(key,entry.getValue());
            }
        }
        
        return new Nutrition(result);
    }

    public Set<Map.Entry<String, Double>> getTopNEntries(int n) {
        return entries.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toSet());
    }

    public void multiplyBy(double factor) {
        for (Map.Entry<String, Double> entry : entries.entrySet()) {
            entry.setValue(entry.getValue() * factor);
        }
    }
}
