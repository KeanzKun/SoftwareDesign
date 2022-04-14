package delivery.classes;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import delivery.UI.*;
import delivery.database.*;

public class BookingDAO {

	static ArrayList<Booking> bookingList = new ArrayList<Booking>();

	static Scanner scanner = new Scanner(System.in);

	static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

	static BookingFile bf = new BookingFile();

	public void createBooking(String email) throws Exception {
		ArrayList<Booking> bookingList = new ArrayList<Booking>();

		bookingList = bf.retrieve();
		PremisesList pl = new PremisesList();

		Scanner input = new Scanner(System.in);
		String userInput;
		int bookingID = 0;
		int noOfPerson, premiseID;
		double totalAmount, serviceFee;
		String checkInInput, checkOutInput;
		Date checkInDate, checkOutDate;
		Date today = new Date();

		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		UI.header("Create Booking");
		bookingID = bookingList.size() + 100001;

		// prompt permiseID
		System.out.println("Select permise:");
		Premises p = pl.selectPremiseList();

		if (p == null) {
			System.out.println("Please select a premise.");
			return;
		}

		premiseID = p.getPremiseID();

		double price = 0;

		ArrayList<Premises> premiseList = new ArrayList<Premises>();
		for (Premises premise : premiseList) {
			if (premise.equals(premiseID)) {
				if (premise.getActive()) {
					price = premise.getPrice();
				} else {
					System.out.println("Premise is not available to book.");
					return;
				}

			}
		}
		// System.out.println("permiseID: " + permiseID);

		// prompt check-in-date
		System.out.print("Enter Check-in-date(yyyy-mm-dd):");
		userInput = input.nextLine();
		checkInInput = userInput;
		checkInDate = ft.parse(checkInInput);
		// System.out.println("check in date: " + checkInDate);

		// prompt check-out-date
		System.out.print("Enter Check-out-date(yyyy-mm-dd):");
		userInput = input.nextLine();
		checkOutInput = userInput;
		checkOutDate = ft.parse(checkOutInput);
		// System.out.println("check out date: " + checkOutDate);

		if (checkInDate.compareTo(today) < 0) {
			System.out.println("\n\nCheck-In date must be after today. ");
			return;
		}

		// check if check in is before check out
		if (!(checkInDate.compareTo(checkOutDate) < 0)) {
			System.out.println("\n\nCheck-In date must be before Check-Out date. ");
			return;
		}

		// check for availability on dates
		if (!checkAvailability(bookingID, premiseID, checkInDate, checkOutDate)) {
			System.out.println("\n\nThere are existing booking during the input dates.");
			return;
		}

		// prompt number of person
		System.out.print("Enter number of person:");
		userInput = input.nextLine();
		noOfPerson = Integer.parseInt(userInput);
		if (noOfPerson > p.getCapacity()) {
			System.out.println("Number of People is more than the premise capacity.");
			return;
		}

		// System.out.println("Number of person: " + noOfPerson);

		int days = calculateDays(checkInDate, checkOutDate);

		totalAmount = price * days;
		serviceFee = totalAmount * 0.05;

		Booking newBooking = new Booking(bookingID, email, premiseID, today, checkInDate, checkOutDate, noOfPerson,
				totalAmount, serviceFee);

		bookingList.add(newBooking);

		bf.save(bookingList);
		System.out.println("Booking successfully recorded.");
		System.out.println("Press ENTER to go to menu.");

		input.nextLine();
	}

	public static void viewBooking(Booking booking) throws Exception {
		System.out.println("Booking ID: " + booking.getBookingID());
		System.out.println("Email: " + booking.getRegEmail());
		System.out.println("Premise ID: " + booking.getPremiseID());
		System.out.println("Booking Date: " + ft.format(booking.getBookingDate()));
		System.out.println("Check In Date " + ft.format(booking.getCheckInDate()));
		System.out.println("Check Out Date: " + ft.format(booking.getCheckOutDate()));
		System.out.println("No Of Person: " + booking.getNoOfPerson());
		System.out.println("Total Amount: " + booking.getTotalAmount());
		// System.out.println("Service Fee: " + booking.getServiceFee());
	}

	public static void updateBooking(Booking booking) throws Exception {
		// check in & check out, numPeople

		UI ui = new UI();
		ArrayList<Booking> bookingList = new ArrayList<Booking>();
		bookingList = bf.retrieve();

		int choice = ui.displayUpdateMenu();
		String dateInput;

		switch (choice) {
		case 1:
			System.out.print("Enter new Check-In Date: ");
			dateInput = scanner.nextLine();
			Date newCheckIn = ft.parse(dateInput);

			System.out.print("Enter new Check-Out Date: ");
			dateInput = scanner.nextLine();
			Date newCheckOut = ft.parse(dateInput);

			// check if check in is before check out
			if (newCheckIn.compareTo(newCheckOut) < 0) {

				// check for availability on new dates
				if (checkAvailability(booking.getBookingID(), booking.getPremiseID(), newCheckIn, newCheckOut)) {				
									
					for(Booking b:bookingList) {

						if(b.getBookingID() == booking.getBookingID()) {

							b.setCheckInDate(newCheckIn);
							b.setCheckOutDate(newCheckOut);
							
							bf.save(bookingList);
						}	
					}
					System.out.println("\n\nCheck-In & Check-Out date has been updated.");
				} else {
					System.out.println("\n\nThere are existing booking during the input dates.");
					return;
				}
			} else {
				System.out.println("\n\nCheck-In date must be before Check-Out date. ");
				return;
			}
			
			
			

			break;

		case 2:
			int cap = 0;

			System.out.print("Enter new Number of People: ");
			int newNum = scanner.nextInt();
			scanner.nextLine();

			ArrayList<Premises> premiseList = new ArrayList<Premises>();

			for (Premises premise : premiseList) {
				if (premise.getPremiseID() == booking.getPremiseID()) {
					cap = premise.getCapacity();
				}
			}			

			if (newNum > 0 && newNum <= cap) {
				
				for(Booking b:bookingList) {

					if(b.getBookingID() == booking.getBookingID()) {

						b.setNoOfPerson(newNum);					
						bf.save(bookingList);
					}	
				}
				
				System.out.println("\n\nNumber of people has updated.");
			} else {
				System.out.println("\n\nUpdate unsuccessful.");
			}

			break;

		default:

			break;
		}

	}

	public static void cancelBooking(Booking booking) throws Exception {

		if (checkBookingHour(booking.getCheckInDate())) {
			booking.cancelBooking();

			if (!booking.getStatus()) {
				System.out.println("Booking cancelled");
			}

		} else {
			System.out.println("Can't cancel within 24 hours");
		}

	}

	public static boolean checkBookingHour(Date checkIn) {
		Date today = new Date();

		long difference = checkIn.getTime() - today.getTime();
		long differenceInHours = (difference / 3600000);

		if (differenceInHours >= 24) {
			return true;
		} else
			return false;
	}

	public static int calculateDays(Date checkIn, Date checkOut) {

		long difference = checkOut.getTime() - checkIn.getTime();
		int day = ((int) difference / 86400000);

		return day;
	}

	public static boolean checkAvailability(int bookingID, int premiseID, Date checkIn, Date checkOut)
			throws Exception {

		boolean status = false;
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();

		// go through all bookings
		for (Booking booking : bookingList) {
			// match the same premise that is not current booking
			if (premiseID == booking.getPremiseID() && bookingID != booking.getBookingID()) {
				// check if check in date got clash
				int compareStart1 = checkIn.compareTo(booking.getCheckInDate());
				int compareEnd1 = checkIn.compareTo(booking.getCheckOutDate());

				// check if check out date got clash
				int compareStart2 = checkOut.compareTo(booking.getCheckInDate());
				int compareEnd2 = checkOut.compareTo(booking.getCheckOutDate());

				// if no clash return true
				if (compareStart1 < 0 || compareEnd1 > 0) {

					if (compareStart2 < 0 || compareEnd2 > 0) {

						status = true;
					}
				}
			}
		}

		return status;
	}

	public void parseFile(String fileName, String searchStr) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(fileName));
		while (scan.hasNext()) {
			String line = scan.nextLine().toLowerCase().toString();
			if (line.contains(searchStr)) {
				System.out.println(line);
			}
		}
	}

	public Booking searchBookingUser(int year, String email) throws Exception {
		bookingList = bf.retrieve();
		ArrayList<Booking> searchList = new ArrayList<Booking>();
		Booking selectedBooking;

		for (Booking booking : bookingList) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			int year1 = Integer.parseInt(df.format(booking.getCheckInDate()));

			if (year1 == year && booking.getRegEmail().equals(email)) {
				searchList.add(booking);
			}
		}

		int i = 1;

		for (Booking search : searchList) {
			System.out.println("\nOption " + i);
			System.out.println("=========================");
			viewBooking(search);
			i++;
		}

		System.out.println("Select booking: ");
		int choice = scanner.nextInt() - 1;
		scanner.nextLine();
		selectedBooking = searchList.get(choice);

		return selectedBooking;
	}

	public static void searchAdmin(String[] args) throws FileNotFoundException {
		BookingDAO fileSearch = new BookingDAO();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Insert booking keyword to search: ");
		String keyword = scanner.nextLine();
		fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
	}

	public void searchBookingHost(String email) throws Exception {
		PremisesList pl = new PremisesList();
		Premises p = pl.selectPremise(email);

		bookingList = bf.retrieve();
		ArrayList<Booking> searchList = new ArrayList<Booking>();
		Booking selectedBooking;

		for (Booking booking : bookingList) {
			if (booking.getPremiseID() == p.getPremiseID()) {
				searchList.add(booking);
			}
		}

		int i = 1;

		for (Booking search : searchList) {
			System.out.println("Option " + i);
			viewBooking(search);
		}

		// System.out.println("Select booking: ");
		// int choice = scanner.nextInt() - 1;
		// scanner.nextLine();
		// selectedBooking = searchList.get(choice);

		// return selectedBooking;
	}
}
