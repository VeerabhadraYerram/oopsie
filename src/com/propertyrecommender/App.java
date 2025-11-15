package com.propertyrecommender;

import com.propertyrecommender.model.Property;
import com.propertyrecommender.model.User;
import com.propertyrecommender.service.*;
import com.propertyrecommender.util.InputUtil;

import java.util.List;

public class App {

    private static final AuthService authService = new AuthService();
    private static final PropertyService propertyService = new PropertyService();
    private static final RecommendationService recService = new RecommendationService(propertyService);
    private static final BookingService bookingService = new BookingService();
    private static final AdminService adminService = new AdminService();
    private static final UserService userService = new UserService();
    private static final GuestService guestService = new GuestService();

    private static User currentUser = null;

    public static void main(String[] args) {

        while (true) {
            if (currentUser == null)
                showWelcomeMenu();
            else {
                switch (currentUser.getRole()) {
                    case "ADMIN" -> showAdminMenu();
                    case "SELLER" -> showSellerMenu();
                    case "BUYER" -> showBuyerMenu();
                    default -> System.out.println("Unknown role. Contact admin.");
                }
            }
        }
    }

    // ------------------------------------------------------------
    //  WELCOME MENU  (Guest / Register / Login)
    // ------------------------------------------------------------
    private static void showWelcomeMenu() {
        System.out.println("\n=== Property Recommender ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Continue as Guest");
        System.out.println("0. Exit");

        int c = InputUtil.readInt("Choose: ", -1);

        switch (c) {
            case 1 -> handleLogin();
            case 2 -> handleRegister();
            case 3 -> handleGuest();
            case 0 -> {
                System.out.println("Bye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleLogin() {
        String username = InputUtil.readLine("Username: ");
        String password = InputUtil.readLine("Password: ");

        User u = authService.login(username, password);

        if (u == null) {
            System.out.println("Login failed.");
        } else {
            currentUser = u;
            System.out.println("Welcome " + u.getFullName());
        }
    }

    private static void handleRegister() {
        String username = InputUtil.readLine("Choose username: ");
        String pwd = InputUtil.readLine("Password: ");
        String fullName = InputUtil.readLine("Full name: ");
        String email = InputUtil.readLine("Email: ");

        // By default, role = BUYER (set in DB)
        User u = new User(null, username, pwd, fullName, email, "BUYER");

        try {
            authService.register(u);
            System.out.println("Registered successfully. Please login.");
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void handleGuest() {
        System.out.println("Browsing as guest...");
        guestService.showLatestProperties();
    }

    // ------------------------------------------------------------
    //   BUYER MENU
    // ------------------------------------------------------------
    private static void showBuyerMenu() {
        System.out.println("\n=== Buyer Menu ===");
        System.out.println("1. Search / Recommend Properties");
        System.out.println("2. Book a Property");
        System.out.println("3. My Profile");
        System.out.println("4. Logout");

        int c = InputUtil.readInt("Choose: ", -1);

        switch (c) {
            case 1 -> searchAndRecommend();
            case 2 -> bookProperty();
            case 3 -> viewProfile();
            case 4 -> currentUser = null;
            default -> System.out.println("Invalid choice.");
        }
    }

    // ------------------------------------------------------------
    //   SELLER MENU
    // ------------------------------------------------------------
    private static void showSellerMenu() {
        System.out.println("\n=== Seller Menu ===");
        System.out.println("1. Add Property");
        System.out.println("2. Update My Property");
        System.out.println("3. Delete My Property");
        System.out.println("4. View My Properties");
        System.out.println("5. Logout");

        int c = InputUtil.readInt("Choose: ", -1);

        switch (c) {
            case 1 -> addPropertyForSeller();
            case 2 -> updatePropertyForSeller();
            case 3 -> deletePropertyForSeller();
            case 4 -> viewMyProperties();
            case 5 -> currentUser = null;
            default -> System.out.println("Invalid choice.");
        }
    }

    // ------------------------------------------------------------
    //   ADMIN MENU
    // ------------------------------------------------------------
    private static void showAdminMenu() {
        System.out.println("\n=== Admin Dashboard ===");
        System.out.println("1. Manage Properties");
        System.out.println("2. Manage Users");
        System.out.println("3. View All Bookings");
        System.out.println("4. Logout");

        int c = InputUtil.readInt("Choose: ", -1);

        switch (c) {
            case 1 -> manageProperties();
            case 2 -> manageUsers();
            case 3 -> viewBookings();
            case 4 -> currentUser = null;
            default -> System.out.println("Invalid choice.");
        }
    }

    // ------------------------------------------------------------
    //   BUYER FUNCTIONS
    // ------------------------------------------------------------
    private static void searchAndRecommend() {
        String locality = InputUtil.readLine("Preferred locality (partial match): ");
        double maxPrice = InputUtil.readDouble("Max price: ", Double.MAX_VALUE);
        int bhk = InputUtil.readInt("Preferred BHK (0 to ignore): ", 0);

        List<Property> recs = recService.recommend(locality, maxPrice, bhk);

        if (recs.isEmpty()) {
            System.out.println("No properties found.");
            return;
        }

        System.out.println("Recommended Properties:");
        recs.forEach(System.out::println);
    }

    private static void bookProperty() {
        int pid = InputUtil.readInt("Property ID to book: ", -1);
        Property p = propertyService.findById(pid);

        if (p == null || !p.isAvailable()) {
            System.out.println("Property unavailable.");
            return;
        }

        double amt = InputUtil.readDouble("Enter payment amount: ", p.getPrice());

        try {
            bookingService.bookProperty(pid, currentUser.getUserId(), amt);
            System.out.println("Booked successfully!");
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------
    //   SELLER FUNCTIONS
    // ------------------------------------------------------------
    private static void addPropertyForSeller() {
        Property p = new Property();

        p.setOwnerId(currentUser.getUserId());
        p.setTitle(InputUtil.readLine("Title: "));
        p.setDescription(InputUtil.readLine("Description: "));
        p.setLocality(InputUtil.readLine("Locality: "));
        p.setBhk(InputUtil.readInt("BHK: ", 1));
        p.setPrice(InputUtil.readDouble("Price: ", 0));
        p.setAvailable(true);

        propertyService.addProperty(p);
        System.out.println("Property added successfully!");
    }

    private static void viewMyProperties() {
        int sellerId = currentUser.getUserId();

        List<Property> list =
                propertyService.listAll()
                        .stream()
                        .filter(p -> p.getOwnerId() == sellerId)
                        .toList();

        if (list.isEmpty()) {
            System.out.println("You have no properties listed.");
            return;
        }

        list.forEach(System.out::println);
    }

    private static void updatePropertyForSeller() {
        viewMyProperties();

        int id = InputUtil.readInt("Property ID to update: ", -1);
        Property p = propertyService.findById(id);

        if (p == null || p.getOwnerId() != currentUser.getUserId()) {
            System.out.println("Invalid or unauthorized property.");
            return;
        }

        p.setTitle(InputUtil.readLine("Title (" + p.getTitle() + "): "));
        p.setDescription(InputUtil.readLine("Description: "));
        p.setLocality(InputUtil.readLine("Locality: "));
        p.setBhk(InputUtil.readInt("BHK: ", p.getBhk()));
        p.setPrice(InputUtil.readDouble("Price: ", p.getPrice()));

        propertyService.updateProperty(p);
        System.out.println("Property updated successfully!");
    }

    private static void deletePropertyForSeller() {
        viewMyProperties();

        int id = InputUtil.readInt("Property ID to delete: ", -1);
        Property p = propertyService.findById(id);

        if (p == null || p.getOwnerId() != currentUser.getUserId()) {
            System.out.println("Invalid or unauthorized property.");
            return;
        }

        propertyService.remove(id);
        System.out.println("Property deleted.");
    }

    // ------------------------------------------------------------
    //   ADMIN FUNCTIONS
    // ------------------------------------------------------------
    private static void manageProperties() {
        System.out.println("\n1. Add Property");
        System.out.println("2. Update Property");
        System.out.println("3. Delete Property");
        System.out.println("4. List All Properties");

        int c = InputUtil.readInt("Choose: ", -1);

        switch (c) {
            case 1 -> addPropertyForSeller(); // admin can also add
            case 2 -> updatePropertyForSeller();
            case 3 -> deletePropertyForSeller();
            case 4 -> propertyService.listAll().forEach(System.out::println);
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageUsers() {
        adminService.listUsers().forEach(System.out::println);

        int id = InputUtil.readInt("Delete user ID (0 to skip): ", 0);

        if (id > 0) {
            adminService.deleteUser(id);
            System.out.println("User deleted (if existed).");
        }
    }

    private static void viewBookings() {
        bookingService.listAllBookings().forEach(System.out::println);
    }

    // ------------------------------------------------------------
    //   PROFILE
    // ------------------------------------------------------------
    private static void viewProfile() {
        System.out.println(currentUser);

        String change = InputUtil.readLine("Change full name? (y/N): ");

        if (change.equalsIgnoreCase("y")) {
            currentUser.setFullName(InputUtil.readLine("Enter new name: "));
            userService.updateProfile(currentUser);
            System.out.println("Profile updated!");
        }
    }
}
