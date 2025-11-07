package model;

public class UserPreference {
    private final String preferredCity;
    private final String preferredNeighborhood; // optional
    private final String desiredPropertyType; // optional
    private final int minBedrooms;
    private final int minBathrooms;
    private final boolean needsPetFriendly;
    private final boolean needsFurnished;
    private final double maxBudget;
    private final SortOption sortOption;
    private final int maxResults;

    public enum SortOption {
        BEST_MATCH,
        LOWEST_PRICE,
        HIGHEST_RATING,
        LARGEST_SIZE
    }

    public UserPreference(
            String preferredCity,
            String preferredNeighborhood,
            String desiredPropertyType,
            int minBedrooms,
            int minBathrooms,
            boolean needsPetFriendly,
            boolean needsFurnished,
            double maxBudget,
            SortOption sortOption,
            int maxResults
    ) {
        this.preferredCity = preferredCity;
        this.preferredNeighborhood = preferredNeighborhood;
        this.desiredPropertyType = desiredPropertyType;
        this.minBedrooms = minBedrooms;
        this.minBathrooms = minBathrooms;
        this.needsPetFriendly = needsPetFriendly;
        this.needsFurnished = needsFurnished;
        this.maxBudget = maxBudget;
        this.sortOption = sortOption;
        this.maxResults = maxResults;
    }

    public String getPreferredCity() {
        return preferredCity;
    }

    public String getPreferredNeighborhood() {
        return preferredNeighborhood;
    }

    public String getDesiredPropertyType() {
        return desiredPropertyType;
    }

    public int getMinBedrooms() {
        return minBedrooms;
    }

    public int getMinBathrooms() {
        return minBathrooms;
    }

    public boolean isNeedsPetFriendly() {
        return needsPetFriendly;
    }

    public boolean isNeedsFurnished() {
        return needsFurnished;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public SortOption getSortOption() {
        return sortOption;
    }

    public int getMaxResults() {
        return maxResults;
    }
}


