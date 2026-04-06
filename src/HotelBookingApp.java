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

    static class Booking {
        String bookingId;
        String guestName;
        String roomType;
        String checkInDate;
        String checkOutDate;
        int assignedRoomNumber;

        Booking(String bookingId, String guestName, String roomType, String checkInDate, String checkOutDate, int assignedRoomNumber) {
            this.bookingId = bookingId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.assignedRoomNumber = assignedRoomNumber;
        }

        void printSummary() {
            System.out.println("\n--- Reservation Confirmation ---");
            System.out.println("Booking ID: " + bookingId);
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room No: " + assignedRoomNumber);
            System.out.println("Check-in: " + checkInDate);
            System.out.println("Check-out: " + checkOutDate);
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
            System.out.println("3. Make a Booking Request");
            System.out.println("4. Exit");
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
                makeBookingRequest(scanner);
            } else if (choice == 4) {
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

    private static void makeBookingRequest(Scanner scanner) {
        System.out.print("Enter Guest Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String type = scanner.nextLine();
        System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
        String checkIn = scanner.nextLine();
        System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
        String checkOut = scanner.nextLine();

        Room allocatedRoom = null;
        for (Room room : roomInventory) {
            if (room.type.equalsIgnoreCase(type) && room.isAvailable) {
                allocatedRoom = room;
                break;
            }
        }

        if (allocatedRoom == null) {
            System.out.println("Sorry, no '" + type + "' rooms are currently available.");
            return;
        }

        allocatedRoom.isAvailable = false;
        String bookingId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Booking booking = new Booking(bookingId, name, type, checkIn, checkOut, allocatedRoom.roomNumber);
        booking.printSummary();
    }
}
