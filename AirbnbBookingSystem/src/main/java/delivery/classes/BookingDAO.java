package delivery.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import delivery.database.*;

public class BookingDAO {
	
	
	public void viewBooking()throws Exception{
		BookingFile BF = new BookingFile();
		
		ArrayList<Booking> bookingList = new ArrayList<Booking>();
		
		bookingList = BF.retrieve();
		
		String checkInDate = "2022-10-21";
		String checkOutDate = "2022-10-22";
		String todate = "2022-10-20";
		
		
	     SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	     
	     Date checkIn = ft.parse(checkInDate);
	     Date checkOut = ft.parse(checkOutDate);
	     Date today = ft.parse(todate);
	     
		Payment pay1 = new Payment("P001", "Card"); 
		Booking book1 = new Booking("kean@gmail.com","001",today,checkIn,checkOut,3,120.60,pay1,20.60);
		Booking book2 = new Booking("KQ@gmail.com","001",today,checkIn,checkOut,3,120.60,pay1,20.60);
		Booking book3 = new Booking("kean@gmail.com","001",today,checkIn,checkOut,3,120.60,pay1,20.60);
		Booking book4 = new Booking("kean@gmail.com","001",today,checkIn,checkOut,3,120.60,pay1,20.60);
		Booking book5 = new Booking("kean@gmail.com","001",today,checkIn,checkOut,3,120.60,pay1,20.60);
		
		bookingList.add(book1);
		bookingList.add(book2);
		bookingList.add(book3);
		bookingList.add(book4);
		bookingList.add(book5);
		
	}
}
