package delivery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import delivery.classes.Booking;
import delivery.classes.Payment;
import delivery.classes.Person;
import delivery.classes.PersonType;

public class BookingFile extends AbstractFile{
	final static String fileName = "Booking.txt";
	public BookingFile() {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	public void save(ArrayList<Booking> bookingList) {
        try {
            fileWriter = new FileWriter(fileName, false);
            printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            for (Booking i : bookingList) {
                printWriter.write(i.toString());
                printWriter.write("\n");
            }

            fileWriter.close();
            printWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Booking> retrieve() {
    	ArrayList<Booking> bookingList = new ArrayList<Booking>();
        try {
            file = new File(fileName);
            fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String str = fileScanner.nextLine();
                bookingList.add(readBookingFile(str));
            }
        } catch (FileNotFoundException e) {
            System.out.print("Can't read file");
        }
        
        return bookingList;
    }

    private Booking readBookingFile(String str) {
        String email = null, premiseid = null, paymentID;
        Date bookingDate = null, checkInDate = null, checkOutDate = null;
        int noOfPerson = 0, bookingid = 0;
        double totalAmount = 0, serviceFee = 0;
        Payment payment = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd");
        Scanner sc = new Scanner(str);
        sc.useDelimiter(AbstractFile.getDELIMITER());
        
        while (sc.hasNext()) {
            bookingid = Integer.parseInt(sc.next());
        	email = sc.next();
        	premiseid = sc.next();
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
         	paymentID = sc.next();
         	payment = new Payment(paymentID);
         	serviceFee = Double.parseDouble(sc.next());
      
        }
       
        Booking readBooking = new Booking(bookingid, email, premiseid, bookingDate, checkInDate, checkOutDate, noOfPerson, totalAmount, payment, serviceFee);
        sc.close();
        return readBooking;

        
    }
    
}
