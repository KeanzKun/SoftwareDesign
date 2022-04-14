package delivery.classes;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import delivery.UI.UI;
import delivery.database.*;

public class BookingDAO {

	static ArrayList<Booking> bookingList = new ArrayList<Booking>();

	static Scanner scanner = new Scanner(System.in);

	static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	
	public void createBooking() throws Exception {
		ArrayList<Booking> bookingList = new ArrayList<Booking>();
		BookingFile bf = new BookingFile();
		bookingList = bf.retrieve();
		
		Scanner input = new Scanner(System.in);
		String userInput;
		int bookingID = 0;
		int noOfPerson, premiseID;
		double totalAmount,serviceFee;
		String regEmail,checkInInput,checkOutInput;
		Date checkInDate, checkOutDate;
	
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		
		UI.header("Create Booking");
		bookingID = bookingList.size() + 100001;
		
		// prompt regEmail
		System.out.print("Enter email:");
		regEmail = input.nextLine();
		
		// prompt check-in-date
		System.out.print("Enter Check-in-date(yyyy-mm-dd):");
		userInput = input.nextLine();
		checkInInput = userInput;
		checkInDate = ft.parse(checkInInput);
		//System.out.println("check in date: " + checkInDate); 

		// prompt check-out-date
		System.out.print("Enter Check-out-date(yyyy-mm-dd):");
		userInput = input.nextLine();
		checkOutInput = userInput;
		checkOutDate = ft.parse(checkOutInput);
		//System.out.println("check out date: " + checkOutDate);
		
		

		// prompt permiseID
		System.out.print("Select permise:");
		premiseID = input.nextInt();
		input.nextLine();
		//System.out.println("permiseID: " + permiseID);
		
		// check if check in is before check out		
		if  (!(checkInDate.compareTo(checkOutDate) < 0)) {
			System.out.print("\n\nCheck-In date must be before Check-Out date. ");
			return;			
		}

		// check for availability on dates
		if (!checkAvailability(bookingID, premiseID, checkInDate, checkOutDate)){
			System.out.print("\n\nThere are existing booking during the input dates.");
			return;
		}
		

		// prompt number of person
		System.out.print("Enter number of person:");
		userInput = input.nextLine();
		noOfPerson = Integer.parseInt(userInput);
		//System.out.println("Number of person: " + noOfPerson);		
		
		int days = calculateDays(checkInDate, checkOutDate);
		System.out.print(days);

		double price = 0;

		ArrayList<Premises> premiseList = new ArrayList<Premises>();
		for(Premises premise : premiseList) {
			if(premise.equals(premiseID)) {
				price = premise.getPrice();
			}		
		}

		totalAmount = price*days;
		serviceFee = totalAmount*0.05;
		
		Date today = new Date();
		
		Booking newBooking = new Booking(bookingID, regEmail, premiseID, 
				today, checkInDate, checkOutDate, noOfPerson, totalAmount, serviceFee);
		
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
		System.out.println("Service Fee: " + booking.getServiceFee());
		System.out.println("Service Fee: " + booking.getStatus());
	}

	public static void updateBooking(Booking booking) throws Exception {
		// check in & check out, numPeople

		UI ui = new UI();

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
						booking.setCheckInDate(newCheckIn);
						booking.setCheckOutDate(newCheckOut);
						System.out.print("\n\nCheck-In & Check-Out date has been updated.");
					} else {
						System.out.print("\n\nThere are existing booking during the input dates.");
						return;
					}
				} else {
					System.out.print("\n\nCheck-In date must be before Check-Out date. ");
					return;
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
		int day = ((int)difference / 86400000);

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

	public static void searchAdmin(String[] args) throws FileNotFoundException {
		BookingDAO fileSearch = new BookingDAO();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Insert booking keyword to search: ");
		String keyword = scanner.nextLine();
		fileSearch.parseFile("../AirbnbBookingSystem/Booking.txt", (keyword));
	}

	private Booking searchBooking(String str, String dateMonth) {
    	//System.out.println("hey"+str);
        String email = null;
        Date bookingDate = null, checkInDate = null, checkOutDate = null;
        int noOfPerson = 0, bookingid = 0, premiseid = 0;
        double totalAmount = 0, serviceFee = 0;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Scanner sc = new Scanner(str);
        sc.useDelimiter(AbstractFile.getDELIMITER());
        
        Booking readBooking = null;
        while (sc.hasNext()) {
            bookingid = Integer.parseInt(sc.next());
        	email = sc.next();
        	premiseid = Integer.parseInt(sc.next());
         	try {
         		bookingDate = dateFormatter.parse(sc.next());		
            	checkInDate = dateFormatter.parse(sc.next());
            	
				checkOutDate = dateFormatter.parse(sc.next());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         	noOfPerson = Integer.parseInt(sc.next());
         	totalAmount = Double.parseDouble(sc.next());
         	serviceFee = Double.parseDouble(sc.next());
      
        }
        if(checkInDate.getMonth()+1 == Integer.parseInt(dateMonth)) {
        	readBooking = new Booking(bookingid, email, premiseid, bookingDate, checkInDate, checkOutDate, noOfPerson, totalAmount, serviceFee);
    	}
        
        sc.close();
        
        return readBooking;    
    }
}
