import data.PropertyRepository;
import model.Property;
import model.UserPreference;
import recommendation.PropertyRecommender;
import util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==== Property Recommendation System ====");

        Scanner scanner = new Scanner(System.in);
        InputUtils input = new InputUtils(scanner);

        String city = input.promptString("Preferred City (e.g., Metropolis/Gotham/Star City)", true);
        String neighborhood = input.promptString("Preferred Neighborhood (optional, press Enter to skip)", false);
        String propertyType = input.promptString("Desired Property Type (apartment/house/studio) - optional", false);
        int minBedrooms = input.promptInt("Minimum Bedrooms", 0, 10);
        int minBathrooms = input.promptInt("Minimum Bathrooms", 1, 10);
        boolean petFriendly = input.promptBoolean("Require Pet Friendly?");
        boolean furnished = input.promptBoolean("Require Furnished?");
        double maxBudget = input.promptDouble("Maximum Monthly Budget", 300, 10000);

        System.out.println("Sort Options: 1) Best Match  2) Lowest Price  3) Highest Rating  4) Largest Size");
        int sortChoice = input.promptInt("Choose sort option", 1, 4);
        UserPreference.SortOption sortOption = switch (sortChoice) {
            case 2 -> UserPreference.SortOption.LOWEST_PRICE;
            case 3 -> UserPreference.SortOption.HIGHEST_RATING;
            case 4 -> UserPreference.SortOption.LARGEST_SIZE;
            default -> UserPreference.SortOption.BEST_MATCH;
        };

        int maxResults = input.promptInt("How many results to display", 1, 50);

        UserPreference preference = new UserPreference(
                city,
                neighborhood,
                propertyType,
                minBedrooms,
                minBathrooms,
                petFriendly,
                furnished,
                maxBudget,
                sortOption,
                maxResults
        );

        PropertyRepository repository = new PropertyRepository();
        PropertyRecommender recommender = new PropertyRecommender();

        List<Property> recommendations = recommender.recommend(repository.getAll(), preference);

        if (recommendations.isEmpty()) {
            System.out.println("No properties matched your criteria. Try adjusting your filters.");
        } else {
            System.out.println();
            System.out.println("Top Recommendations:");
            System.out.println("---------------------");
            int idx = 1;
            for (Property p : recommendations) {
                System.out.println(idx + ". " + p.getTitle());
                System.out.println("   City: " + p.getCity() + "  | Neighborhood: " + p.getNeighborhood());
                System.out.println("   Type: " + p.getPropertyType() + "  | Beds: " + p.getBedrooms() + "  | Baths: " + p.getBathrooms());
                System.out.println("   Pet Friendly: " + (p.isPetFriendly() ? "Yes" : "No") + "  | Furnished: " + (p.isFurnished() ? "Yes" : "No"));
                System.out.println("   Size: " + p.getSizeSqFt() + " sqft  | Rating: " + p.getRating());
                System.out.println("   Price: $" + String.format("%.0f", p.getPricePerMonth()) + "/month");
                System.out.println();
                idx++;
            }
        }

        System.out.println("Thank you for using the Property Recommendation System!");
    }
}



