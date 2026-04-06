import java.util.*;
import java.io.*;

public class HotelBookingApp {
    static class Room {
        int roomNumber;
        String type;
        boolean isAvailable;

        Room(int roomNumber, String type, boolean isAvailable) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.isAvailable = isAvailable;
        }
    }

    static List<Room> roomInventory = new ArrayList<>();

    public static void main(String[] args) {
        initializeInventory();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=========================================");
            System.out.println("   Welcome to Hotel Booking System");
            System.out.println("=========================================");
            System.out.println("1. View Room Options");
            System.out.println("2. Search Room Availability");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (choice == 1) {
                showRoomOptions();
            } else if (choice == 2) {
                searchRoomAvailability(scanner);
            } else if (choice == 3) {
                System.out.println("Exiting System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeInventory() {
        roomInventory.add(new Room(101, "Single", true));
        roomInventory.add(new Room(102, "Single", true));
        roomInventory.add(new Room(201, "Double", false));
        roomInventory.add(new Room(202, "Double", true));
        roomInventory.add(new Room(301, "Suite", true));
    }

    private static void showRoomOptions() {
        System.out.println("\n--- Room Inventory ---");
        for (Room room : roomInventory) {
            System.out.println("Room " + room.roomNumber + " (" + room.type + ") - " + (room.isAvailable ? "Available" : "Not Available"));
        }
    }

    private static void searchRoomAvailability(Scanner scanner) {
        System.out.print("Enter room type to search (Single/Double/Suite): ");
        String type = scanner.nextLine();
        System.out.println("\n--- Available " + type + " Rooms ---");
        boolean found = false;
        for (Room room : roomInventory) {
            if (room.type.equalsIgnoreCase(type) && room.isAvailable) {
                System.out.println("Room " + room.roomNumber);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms of this type.");
        }
    }
}
