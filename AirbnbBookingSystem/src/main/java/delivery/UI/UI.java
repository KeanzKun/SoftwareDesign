package delivery.UI;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;
import delivery.classes.*;

public class UI {
	Scanner scanner = new Scanner(System.in);
	static AppsController ac = new AppsController();

	// display header
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

	public static void displayUserMainMenu() throws ParseException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: Add Booking");
		System.out.println("Option 2: View Booking");

		BookingDAO bd = new BookingDAO();

		switch (ac.getMenuInput()) {
			case 1:
				bd.createBooking();
				break;

			case 2:
				// bd.searchAdmin(args);
				// displaySearchMenu(booking);
				break;
		}
	}

	public static void displaySearchMenu(Booking booking) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: View Booking");
		System.out.println("Option 2: Update Booking");
		System.out.println("Option 3: Cancel Booking");

		BookingDAO bd = new BookingDAO();

		switch (ac.getMenuInput()) {
			case 1:
				bd.viewBooking(booking);

				break;

			case 2:
				bd.updateBooking(booking);

				break;

			case 3:
				bd.cancelBooking(booking);

				break;
		}
	}

	public static int displayUpdateMenu() throws Exception {
		System.out.println("Update Booking Details");
		System.out.println("======================");
		System.out.println("1. Check-In Date & Check-Out Date");
		System.out.println("2. Number of People Update");
		System.out.println("0. Back");

		return ac.getMenuInput();
	}

	public static void displayAdminMainMenu() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: Generate Sales Report");
		System.out.println("Option 2: Search Booking");

		switch (ac.getMenuInput()) {
			case 1:
				break;

			case 2:
				BookingDAO fileSearch = new BookingDAO();
				System.out.println("Insert booking keyword to search: ");
				String keyword = scanner.nextLine();
				fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
				break;

			default:
				System.out.println("Invalid input try again");
				break;
		}

	}

	public static void displayHostMainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Option 1: Search Booking");
		System.out.println("Option 2: Generate Sales Report");
		System.out.println("Option 3: Add Premise");
		
		switch (ac.getMenuInput()) {
			case 1:
				break;

			case 2:
				BookingDAO fileSearch = new BookingDAO();
				System.out.println("Insert booking keyword to search: ");
				String keyword = scanner.nextLine();
				//fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
				break;
				
			case 3:
				break;

			default:
				System.out.println("Invalid input try again");
				break;
		}
	}

	public static int askEventNo(int beginEventNo, int endEventNo) {
		if (beginEventNo > endEventNo) {
			System.out.printf(
					"%nBug at UI.askEventNo(int, int): the beginEventNo should not greater than the endEventNo.");
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
