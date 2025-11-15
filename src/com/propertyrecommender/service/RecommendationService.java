package com.propertyrecommender.service;

import com.propertyrecommender.model.Property;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simple recommendation engine (rule-based)
 */
public class RecommendationService {
    private final PropertyService propertyService;

    public RecommendationService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Filter by locality and budget and then sort by price asc (simple ranking)
    public List<Property> recommend(String locality, double maxPrice, int bhkPreference) {
        List<Property> candidates = propertyService.search(locality, maxPrice);
        // Score properties: match bhk gives extra score, lower price better rank.
        Map<Property, Integer> score = new HashMap<>();
        for (Property p : candidates) {
            int s = 0;
            if (p.getBhk() == bhkPreference) s += 10;
            // price-based score (lower price higher score)
            s += (int)(1000000 / (1 + p.getPrice()));
            score.put(p, s);
        }
        return score.entrySet().stream()
                .sorted(Map.Entry.<Property,Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
