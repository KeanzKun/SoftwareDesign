package delivery.classes;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import delivery.database.*;

public class BookingDAO {

	static ArrayList<Booking> bookingList = new ArrayList<Booking>();
	Scanner scanner = new Scanner(System.in);
	static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

	public static void viewBooking(Booking booking) throws Exception {
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();

		for (Booking bookingValue : bookingList) {

			if (bookingValue.equals(booking)) {
				bookingValue.toString();
			}
		}
	}

	public void updateBooking(Booking booking) throws Exception {
		// check in & check out, numPeople

		int choice = 0;
		String dateInput;

		System.out.println("Update Booking Details");
		System.out.println("======================");
		System.out.println("1. Check-In Date & Check-Out Date");
		System.out.println("2. Number of People Update");
		System.out.println("0. Back");
		System.out.print("Enter choice to update:");
		choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
			case 1:
				System.out.print("Enter new Check-In Date: ");
				dateInput = scanner.nextLine();
				Date newCheckIn = ft.parse(dateInput);

				System.out.print("Enter new Check-Out Date: ");
				dateInput = scanner.nextLine();
				Date newCheckOut = ft.parse(dateInput);

				// check for availability on new dates
				if (checkAvailability(booking.getBookingID(), booking.getPremiseID(), newCheckIn, newCheckOut)) {

					// check if check in is before check out
					if (newCheckIn.compareTo(newCheckOut) < 0) {
						booking.setCheckInDate(newCheckIn);
						booking.setCheckOutDate(newCheckOut);

						System.out.print("\n\nCheck-In & Check-Out date has been updated.");

					} else {
						System.out.print("\n\nCheck-In date must be before Check-Out date. ");

					}
				} else {
					System.out.print("\n\nThere are existing booking during the input dates.");

				}

				break;

			case 2:
				System.out.print("Enter new Number of People: ");
				int newNum = scanner.nextInt();
				scanner.nextLine();

				booking.setNoOfPerson(newNum);

				if (newNum > 0) {
					System.out.print("\n\nNumber of people has updated.");
				} else {
					System.out.print("\n\nUpdate unsuccessful.");
				}

				break;

			default:

				break;
		}

	}

	public static void cancelBooking(Booking booking) throws Exception {

		LocalDate lt = LocalDate.now();
		String todate = lt.toString();

		System.out.println("Test todate to String: " + todate);
		Date today = ft.parse(todate);

		if (checkBookingHour(booking.getCheckInDate(), today )) {
			booking.cancelBooking();
		} else {
			System.out.println("Can't cancel within 24 hours");
		}

	}

	public boolean checkBookingHour(Date checkOut, Date today) {
		long difference = checkOut.getTime() - today.getTime();
		long differenceInHours = (difference / (1000 * 60 * 60)) % 24;

		if (differenceInHours >= 24) {
			return true;
		} else
			return false;
	}

	public boolean checkAvailability(int bookingID, String premiseID, Date checkIn, Date checkOut) throws Exception {

		boolean status = false;
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();

		// go through all bookings
		for (Booking booking : bookingList) {
			// match the same premise that is not current booking
			if (premiseID.equals(booking.getPremiseID()) && bookingID != booking.getBookingID()) {
				// check if check in date got clash
				int compareStart1 = checkIn.compareTo(booking.getCheckInDate());
				int compareEnd1 = checkIn.compareTo(booking.getCheckOutDate());
				// check if check out date got clash
				int compareStart2 = checkOut.compareTo(booking.getCheckInDate());
				int compareEnd2 = checkOut.compareTo(booking.getCheckOutDate());
				// if no clash return true
				if (compareStart1 <= 0 && compareEnd1 >= 0 && compareStart2 <= 0 && compareEnd2 >= 0) {
					status = true;
				}
			}
		}

		return status;
	}
	
	public void parseFile(String fileName,String searchStr) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(fileName));
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase().toString();
            if(line.contains(searchStr)){
                System.out.println(line);
            }
        }
    }


    public static void searchAdmin(String[] args) throws FileNotFoundException{
    	BookingDAO fileSearch = new BookingDAO();    	
    	Scanner scanner = new Scanner(System.in);

    	System.out.println("Insert booking keyword to search: ");
    	String keyword = scanner.nextLine();
        fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
    }
    public static void main(String[] args) throws Exception {
    	Scanner scanner = new Scanner(System.in);
		LocalDate lt = LocalDate.now();

		String checkInDate = "2022-10-21";
		String checkOutDate = "2022-10-22";
		String todate = lt.toString();
		
		Date checkIn = ft.parse(checkInDate);
		Date checkOut = ft.parse(checkOutDate);
		Date today = ft.parse(todate);

    	Payment pay1 = new Payment("P001");
		Booking bookingDummy = new Booking(100006, "kean@gmail.com", "001", 
			today, checkIn, checkOut, 3, 120.60, pay1, 20.60);

    	BookingDAO fileSearch = new BookingDAO();
    	System.out.println("Insert booking keyword to search: ");
    	String keyword = scanner.nextLine();
        fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));

		System.out.println("===============================================VIEW BOOKING");
		viewBooking(bookingDummy);
		System.out.println("===============================================VIEW BOOKING");
		System.out.println("===============================================CANCEL BOOKING");
		cancelBooking(bookingDummy);
		System.out.println("===============================================CANCEL BOOKING");
    }
}

