package delivery.UI;

import java.io.FileNotFoundException;
import java.util.Scanner;
import delivery.classes.*;
public class UI {
	Scanner scanner = new Scanner(System.in);
	//display header
	public static void header(String heading) {
		System.out.printf("%n%n");
        System.out.print("=======================================");
        System.out.printf("%n%n");
        System.out.print("            ");
        System.out.print(heading);
        System.out.printf("%n%n");
        System.out.print("=======================================");
        System.out.printf("%n%n");
	}
	
	public static void displayEnterMainMenu() {
		System.out.println("Option 1: Login");
		System.out.println("Option 2: Sign up");
	}
	
	public static void displayUserMainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: Add Booking");
		System.out.println("Option 2: View Booking");
		System.out.println("Option 3: Search Booking");
		System.out.println("Option 4: Update Booking");
		System.out.println("Option 5: Delete Booking");
		
		int input = scanner.nextInt();
		
	}
	
	public static void displayAdminMainMenu() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: View Booking");
		System.out.println("Option 2: Search Booking");
		System.out.println("Option 3: Generate Sales Report");
		int input = scanner.nextInt();
		
		switch(input) {
			case 1:break;
			case 2:
				BookingDAO fileSearch = new BookingDAO();
		    	System.out.println("Insert booking keyword to search: ");
		    	String keyword = scanner.nextLine();
		        fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
				break;
			case 3:break;
			default: System.out.println("Invalid input try again");
				break;
		}
			
			
	}

	public static void displayHostMainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: View Booking");
		System.out.println("Option 2: Search Booking");
		System.out.println("Option 3: Generate Sales Report");
		int input = scanner.nextInt();
		
	}
	
	public static int askEventNo(int beginEventNo, int endEventNo) {
        if (beginEventNo > endEventNo) {
            System.out.printf("%nBug at UI.askEventNo(int, int): the beginEventNo should not greater than the endEventNo.");
            System.exit(1);
        }

        int eventNo;
        final String errorMessage = "Sorry, input failed. Please enter the number of option you want to perform.\n";

        while (true) {
            try {
                System.out.printf("%nPlease select> ");
                Scanner sc = new Scanner(System.in);
                eventNo = Integer.parseInt(sc.nextLine());

                if (eventNo >= beginEventNo && eventNo <= endEventNo) {
                    break;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
        return eventNo;
    }
}
