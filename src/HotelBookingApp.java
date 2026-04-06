import java.util.*;
import java.io.*;

public class HotelBookingApp {
    static class BookingException extends Exception {
        BookingException(String message) {
            super(message);
        }
    }

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
        List<String> addOns;

        Booking(String bookingId, String guestName, String roomType, String checkInDate, String checkOutDate, int assignedRoomNumber) {
            this.bookingId = bookingId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.assignedRoomNumber = assignedRoomNumber;
            this.addOns = new ArrayList<>();
        }

        void printSummary() {
            System.out.println("\n--- Reservation Confirmation ---");
            System.out.println("Booking ID: " + bookingId);
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room No: " + assignedRoomNumber);
            System.out.println("Check-in: " + checkInDate);
            System.out.println("Check-out: " + checkOutDate);
            if (!addOns.isEmpty()) {
                System.out.println("Add-ons: " + String.join(", ", addOns));
            } else {
                System.out.println("Add-ons: None");
            }
        }
    }

    static List<Room> roomInventory = new ArrayList<>();
    static List<Booking> bookingHistory = new ArrayList<>();

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
            System.out.println("4. View Booking History");
            System.out.println("5. Cancel a Booking");
            System.out.println("6. Simulate Concurrent Booking");
            System.out.println("7. Exit");
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
                viewBookingHistory();
            } else if (choice == 5) {
                cancelBooking(scanner);
            } else if (choice == 6) {
                simulateConcurrentBooking();
            } else if (choice == 7) {
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
        try {
            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new BookingException("Guest name cannot be empty.");
            }

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String type = scanner.nextLine().trim();
            if (!type.equalsIgnoreCase("Single") && !type.equalsIgnoreCase("Double") && !type.equalsIgnoreCase("Suite")) {
                throw new BookingException("Invalid room type selected.");
            }

            System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
            String checkIn = scanner.nextLine().trim();
            if (!checkIn.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new BookingException("Invalid check-in date format. Use YYYY-MM-DD.");
            }

            System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
            String checkOut = scanner.nextLine().trim();
            if (!checkOut.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new BookingException("Invalid check-out date format. Use YYYY-MM-DD.");
            }

            List<String> chosenAddOns = new ArrayList<>();
            System.out.print("Do you want to add Breakfast? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                chosenAddOns.add("Breakfast");
            }
            System.out.print("Do you want to add Airport Transfer? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                chosenAddOns.add("Airport Transfer");
            }
            System.out.print("Do you want to add Spa? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                chosenAddOns.add("Spa");
            }

            processBooking(name, type, checkIn, checkOut, chosenAddOns);

        } catch (BookingException e) {
            System.out.println("Booking Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static synchronized void processBooking(String name, String type, String checkIn, String checkOut, List<String> addOns) throws BookingException {
        Room allocatedRoom = null;
        for (Room room : roomInventory) {
            if (room.type.equalsIgnoreCase(type) && room.isAvailable) {
                allocatedRoom = room;
                break;
            }
        }

        if (allocatedRoom == null) {
            System.out.println("Booking Failed for " + name + ": No '" + type + "' rooms are currently available.");
            return;
        }

        allocatedRoom.isAvailable = false;
        String bookingId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Booking booking = new Booking(bookingId, name, type, checkIn, checkOut, allocatedRoom.roomNumber);
        booking.addOns.addAll(addOns);
        
        bookingHistory.add(booking);
        System.out.println("Booking Succeeded for " + name + "! Room " + allocatedRoom.roomNumber + " has been allocated!");
        booking.printSummary();
    }

    private static void viewBookingHistory() {
        System.out.println("\n--- Booking History Report ---");
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings made in this session yet.");
            return;
        }

        for (Booking b : bookingHistory) {
            String addOnList = b.addOns.isEmpty() ? "None" : String.join(", ", b.addOns);
            System.out.printf("ID: %s | Guest: %s | Room: %d (%s) | Dates: %s to %s | Add-ons: %s%n",
                    b.bookingId, b.guestName, b.assignedRoomNumber, b.roomType, b.checkInDate, b.checkOutDate, addOnList);
        }
        System.out.println("------------------------------");
    }

    private static void cancelBooking(Scanner scanner) {
        System.out.print("Enter Booking ID to cancel: ");
        String targetId = scanner.nextLine().trim();

        Booking bookingToCancel = null;
        for (Booking b : bookingHistory) {
            if (b.bookingId.equalsIgnoreCase(targetId)) {
                bookingToCancel = b;
                break;
            }
        }

        if (bookingToCancel == null) {
            System.out.println("No booking found with ID: " + targetId);
            return;
        }

        bookingHistory.remove(bookingToCancel);

        for (Room r : roomInventory) {
            if (r.roomNumber == bookingToCancel.assignedRoomNumber) {
                r.isAvailable = true;
                break;
            }
        }

        System.out.println("Booking '" + targetId + "' has been successfully cancelled and Room " + bookingToCancel.assignedRoomNumber + " is available again.");
    }

    private static void simulateConcurrentBooking() {
        System.out.println("\n--- Simulating Concurrent Booking ---");
        // Ensure only ONE single room is available for the test
        int singleCount = 0;
        for (Room r : roomInventory) {
            if (r.type.equalsIgnoreCase("Single")) {
                if (singleCount == 0) {
                    r.isAvailable = true;
                } else {
                    r.isAvailable = false;
                }
                singleCount++;
            }
        }

        Thread t1 = new Thread(() -> {
            try {
                processBooking("Alice", "Single", "2026-05-01", "2026-05-05", new ArrayList<>());
            } catch (BookingException e) {
                System.out.println("Error for Alice: " + e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processBooking("Bob", "Single", "2026-05-01", "2026-05-05", new ArrayList<>());
            } catch (BookingException e) {
                System.out.println("Error for Bob: " + e.getMessage());
            }
        });

        System.out.println("Starting threads for Alice and Bob attempting to book the ONLY 'Single' room...");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Simulation complete.\n");
    }
}
