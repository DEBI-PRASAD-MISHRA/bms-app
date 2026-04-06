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

        System.out.println("=========================================");
        System.out.println("   Welcome to Hotel Booking System");
        System.out.println("=========================================");
        System.out.println("1. View Room Options");
        System.out.println("2. Exit");
        System.out.println("Please select an option:");
        
        showRoomOptions();
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
}
