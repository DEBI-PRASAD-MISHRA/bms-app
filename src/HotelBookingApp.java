import java.util.*;
import java.io.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Welcome to Hotel Booking System");
        System.out.println("=========================================");
        System.out.println("1. View Room Options");
        System.out.println("2. Exit");
        System.out.println("Please select an option:");
        
        showRoomOptions();
    }

    private static void showRoomOptions() {
        System.out.println("\n--- Room Types & Availability ---");
        System.out.println("Single - Available");
        System.out.println("Double - Not Available");
        System.out.println("Suite  - Available");
    }
}
