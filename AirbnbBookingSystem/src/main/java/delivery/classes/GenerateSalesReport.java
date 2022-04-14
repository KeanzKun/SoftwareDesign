package delivery.classes;

import java.util.ArrayList;

import delivery.database.BookingFile;

public class GenerateSalesReport {
	
	private double commission;
	private int premiseID;
	private double totalSales;
	private Booking booking;
	private PersonType personType;
	private String email;
	private ArrayList<Booking> bookingList;
	private BookingDAO bookingDAO = new BookingDAO();
	
	public GenerateSalesReport(String email, PersonType personType){
		this.email = email;
		this.personType = personType;
		this.totalSales = 0;
	}
	
	public void printReport() throws Exception
	{
		
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();
		
		//ask SiowYen how to type month to search
		for(Booking bookingValue : bookingList) {
			if(bookingValue.getRegEmail().equals(email)) {
				bookingDAO.viewBooking(bookingValue);
			}		
		}
		
		if(personType.equals("Host"))
		System.out.println("Total Commission to pay: " + commission);
		
		else
			System.out.println("Total Commission to receive: " + commission);
		
	}
	
	
	public double calculateCommission() throws Exception
	{
		int totalBookingInMonth = 0;
		
		//ask SiowYen How to type month to calculate
		for(Booking bookingValue : bookingList) {
			if(bookingValue.getRegEmail().equals(email)) {
				totalSales += bookingValue.getTotalAmount();
				break;
			}		
		}
		
		return commission;
	}
	

	
}
