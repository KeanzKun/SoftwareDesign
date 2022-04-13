package delivery.classes;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import delivery.database.*;

public class BookingDAO {

	ArrayList<Booking> bookingList = new ArrayList<Booking>();
	Scanner scanner = new Scanner(System.in);
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

	public void viewBooking(Booking booking) throws Exception {
		BookingFile BF = new BookingFile();

		bookingList = BF.retrieve();

		String checkInDate = "2022-10-21";
		String checkOutDate = "2022-10-22";
		String todate = "2022-10-20";

		Date checkIn = ft.parse(checkInDate);
		Date checkOut = ft.parse(checkOutDate);
		Date today = ft.parse(todate);

		Payment pay1 = new Payment("P001", "Card");
		Booking bookingDummy = new Booking("kean@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		Booking book1 = new Booking("kean@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		Booking book2 = new Booking("koonqi@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		Booking book3 = new Booking("siowyen@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		Booking book4 = new Booking("jorian@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		Booking book5 = new Booking("shawn@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);

		bookingList.add(book1);
		bookingList.add(book2);
		bookingList.add(book3);
		bookingList.add(book4);
		bookingList.add(book5);

		for (Booking bookingValue : bookingList) {

			if (bookingValue.equals(bookingDummy)) {
				bookingValue.toString();
			}
		}

	}

	public void updateBooking(Booking booking) throws Exception {
		// check in, check out, numPeople

		int choice = 0;
		String dateInput;

		System.out.println("Update Booking Details");
		System.out.println("======================");
		System.out.println("1. Check-In Date Update");
		System.out.println("2. Check-Out Date Update");
		System.out.println("3. Number of People Update");
		System.out.println("0. Back");
		System.out.print("Enter choice to update:");
		choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
			case 1:
				System.out.print("Enter new Check-In Date: ");
				dateInput = scanner.nextLine();
				Date newCheckIn = ft.parse(dateInput);

				booking.setCheckInDate(newCheckIn);

				if (newCheckIn != null) {
					System.out.print("\n\nCheck-In Date has updated.");
				} else {
					System.out.print("\n\nUpdate unsuccessful.");
				}

				break;

			case 2:
				System.out.print("Enter new Check-Out Date: ");
				dateInput = scanner.nextLine();
				Date newCheckOut = ft.parse(dateInput);

				booking.setCheckOutDate(newCheckOut);

				if (newCheckOut != null) {
					System.out.print("\n\nCheck-Out Date has updated.");
				} else {
					System.out.print("\n\nUpdate unsuccessful.");
				}

				break;

			case 3:
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

	public void cancelBooking(Booking booking) throws Exception {

		String checkInDate = "2022-10-21";
		String checkOutDate = "2022-10-22";
		String todate = "2022-10-20";

		Date checkIn = ft.parse(checkInDate);
		Date checkOut = ft.parse(checkOutDate);
		Date today = ft.parse(todate);

		checkIn.setHours(13);
		
		Payment pay1 = new Payment("P001", "Card");
		Booking bookingDummy = new Booking("kean@gmail.com", "001", today, checkIn, checkOut, 3, 120.60, pay1, 20.60);
		
		
		if(checkBookingHour(checkOut, today))
		{
			booking
		}

		for (Booking bookingValue : bookingList) {

			if (bookingValue.equals(bookingDummy)) {
				
			}
		}
	}

	public boolean checkBookingHour(Date checkOut, Date today)
	{
		long difference = checkOut.getTime() - today.getTime();
        long differenceInHours = (difference/ (1000 * 60 * 60)) % 24;

		if(differenceInHours >= 24){
			return true;
		}
		else
			return false;
	}
}
