package recommendation;

import model.Property;
import model.UserPreference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class PropertyRecommender {
    public List<Property> recommend(List<Property> all, UserPreference preference) {
        List<Property> filtered = filter(all, preference);
        Comparator<Property> comparator = getComparator(preference);
        if (preference.getSortOption() == UserPreference.SortOption.BEST_MATCH) {
            return scoreAndSort(filtered, preference)
                    .stream()
                    .limit(preference.getMaxResults())
                    .collect(Collectors.toList());
        }
        return filtered.stream()
                .sorted(comparator)
                .limit(preference.getMaxResults())
                .collect(Collectors.toList());
    }

    private List<Property> filter(List<Property> all, UserPreference preference) {
        List<Property> result = new ArrayList<>();
        for (Property p : all) {
            if (!equalsIgnoreCaseSafe(p.getCity(), preference.getPreferredCity())) continue;
            if (preference.getPreferredNeighborhood() != null && !preference.getPreferredNeighborhood().isBlank()) {
                if (!equalsIgnoreCaseSafe(p.getNeighborhood(), preference.getPreferredNeighborhood())) continue;
            }
            if (preference.getDesiredPropertyType() != null && !preference.getDesiredPropertyType().isBlank()) {
                if (!equalsIgnoreCaseSafe(p.getPropertyType(), preference.getDesiredPropertyType())) continue;
            }
            if (p.getBedrooms() < preference.getMinBedrooms()) continue;
            if (p.getBathrooms() < preference.getMinBathrooms()) continue;
            if (preference.isNeedsPetFriendly() && !p.isPetFriendly()) continue;
            if (preference.isNeedsFurnished() && !p.isFurnished()) continue;
            if (p.getPricePerMonth() > preference.getMaxBudget()) continue;
            result.add(p);
        }
        return result;
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null || b == null) return false;
        return a.trim().toLowerCase(Locale.ROOT).equals(b.trim().toLowerCase(Locale.ROOT));
    }

    private List<Property> scoreAndSort(List<Property> properties, UserPreference preference) {
        // Weighted scoring: price fit (35%), size (15%), rating (35%), soft matches (15%)
        record Scored(Property property, double score) {}

        List<Scored> scored = new ArrayList<>();
        for (Property p : properties) {
            double priceFit = priceFitness(p.getPricePerMonth(), preference.getMaxBudget());
            double sizeScore = normalize(p.getSizeSqFt(), 300, 2000);
            double ratingScore = p.getRating() / 5.0;

            double softMatch = 0.0;
            if (preference.getDesiredPropertyType() != null && !preference.getDesiredPropertyType().isBlank()) {
                if (equalsIgnoreCaseSafe(p.getPropertyType(), preference.getDesiredPropertyType())) softMatch += 0.5;
            }
            if (preference.getPreferredNeighborhood() != null && !preference.getPreferredNeighborhood().isBlank()) {
                if (equalsIgnoreCaseSafe(p.getNeighborhood(), preference.getPreferredNeighborhood())) softMatch += 0.5;
            }

            double score = (priceFit * 0.35) + (sizeScore * 0.15) + (ratingScore * 0.35) + (softMatch * 0.15);
            scored.add(new Scored(p, score));
        }

        return scored.stream()
                .sorted(Comparator.comparingDouble((Scored s) -> s.score).reversed()
                        .thenComparing(s -> s.property.getPricePerMonth()))
                .map(s -> s.property)
                .collect(Collectors.toList());
    }

    private double priceFitness(double price, double budget) {
        if (price <= 0 || budget <= 0) return 0.0;
        if (price <= budget) return 1.0;
        // Linearly decay until 1.5x budget → 0
        double ratio = price / budget;
        double fitness = 1.0 - ((ratio - 1.0) / 0.5);
        return Math.max(0.0, Math.min(1.0, fitness));
    }

    private double normalize(double value, double min, double max) {
        if (max <= min) return 0.0;
        double clamped = Math.max(min, Math.min(max, value));
        return (clamped - min) / (max - min);
    }

    private Comparator<Property> getComparator(UserPreference preference) {
        if (preference.getSortOption() == null) {
            return Comparator.comparing(Property::getPricePerMonth);
        }
        switch (preference.getSortOption()) {
            case LOWEST_PRICE:
                return Comparator.comparing(Property::getPricePerMonth);
            case HIGHEST_RATING:
                return Comparator.comparing(Property::getRating).reversed()
                        .thenComparing(Property::getPricePerMonth);
            case LARGEST_SIZE:
                return Comparator.comparing(Property::getSizeSqFt).reversed()
                        .thenComparing(Property::getPricePerMonth);
            case BEST_MATCH:
            default:
                return Comparator.comparing(Property::getPricePerMonth);
        }
    }
}


