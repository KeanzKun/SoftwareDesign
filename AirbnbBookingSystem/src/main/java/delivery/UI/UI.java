package delivery.UI;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;
import delivery.classes.*;
import delivery.database.PersonFile;

public class UI {
	Scanner scanner = new Scanner(System.in);
	static AppsController ac = new AppsController();
	static Boolean exit = false;

	// login UI
	public static Person login() {
		Person loginUserDetail = null;
		boolean login_Next = false;
		int loginOption = 0;

		do {
			header("Welcome to the Airbnb Booking System");
			displayEnterMainMenu();
			loginOption = askEventNo(1, 2);
			if (loginOption == 1) {
				String email = InputValidation.readString("Email");
				String password = InputValidation.readString("Password");
				loginUserDetail = ac.findPerson(email, password);
				if (loginUserDetail != null) {
					login_Next = true;
				} else {
					System.out.println("Invalid Login Credential!!!");
				}
			} else if (loginOption == 2) {
				header("Sign Up");
				System.out.println("Enter New Details:");
				ac.createNewUser();
			}

		} while (!login_Next);

		return loginUserDetail;
	}

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

	public static void displayUserMainMenu(Person usingPerson) throws Exception {
		Scanner scanner = new Scanner(System.in);

		BookingDAO bd = new BookingDAO();

		do {
			System.out.println("Option 1: Add Booking");
			System.out.println("Option 2: Search Booking");
			System.out.println("Option 0: Exit");
			switch (ac.getMenuInput()) {
			case 1:
				bd.createBooking(usingPerson.getRegEmail());
				break;

			case 2:
				System.out.println("Enter year to search:");
				int year = scanner.nextInt();
				scanner.nextLine();
				Booking selected = bd.searchBookingUser(year, usingPerson.getRegEmail());
				displaySearchMenu(selected);
				break;
			case 0:
				exit = true;
				break;
			}
		} while (!exit);
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

	public static void displayAdminMainMenu(Person usingPerson) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String email;
		boolean check = false;
		do {
			PersonFile personArr = new PersonFile();
			System.out.println("Option 1: Generate Sales Report");
			System.out.println("Option 2: Search Booking");
			System.out.println("Option 0: Exit");
			switch (ac.getMenuInput()) {
			case 1:

				do {
					System.out.println("Please enter email you want to search, enter q to Quit");
					email = scanner.nextLine();

					for (Person personValue : personArr.retrieve()) {
						if (personValue.getRegEmail().equals(email)) {
							check = true;
						}
					}
					if (!check && !email.equals("q")) {
						System.out.println("Email Not found.");
					} else if (email.equals("q")) {
						System.out.println("Quitting...");
						break;
					}

				} while (!check && !email.equals("q"));

				GenerateSalesReport gsrAdmin = new GenerateSalesReport(email);
				gsrAdmin.printReport(email);
				break;

			case 2:
				BookingDAO fileSearch = new BookingDAO();
				System.out.println("Insert booking keyword to search: ");
				String keyword = scanner.nextLine();
				fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
				break;

			case 0:
				exit = true;
				break;
			default:
				System.out.println("Invalid input try again");
				break;
			}
		} while (!exit);

	}

	public static void displayHostMainMenu(Person usingPerson) throws Exception {
		Scanner scanner = new Scanner(System.in);
		BookingDAO bd = new BookingDAO();

		do {
			System.out.println("Option 1: Search Booking");
			System.out.println("Option 2: Generate Sales Report");
			System.out.println("Option 3: Add Premise");
			System.out.println("Option 0: Exit");
			switch (ac.getMenuInput()) {
			case 1:
				bd.searchBookingHost(usingPerson.getRegEmail());
				// bd.viewBooking(selected);

				break;

			case 2:
				GenerateSalesReport gsr = new GenerateSalesReport(usingPerson.getRegEmail());
				gsr.printReport(usingPerson.getRegEmail());

				break;

			case 3:
				ac.createNewPremises(usingPerson);
				break;

			case 0:
				exit = true;
			default:
				System.out.println("Invalid input try again");
				break;
			}
		} while (!exit);
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
