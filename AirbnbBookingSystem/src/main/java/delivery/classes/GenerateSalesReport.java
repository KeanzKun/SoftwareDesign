package delivery.classes;

import java.util.ArrayList;

import delivery.UI.AppsController;
import delivery.database.BookingFile;

public class GenerateSalesReport {
	
	private double commission;
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
	
	public void printReport(String dateMonth) throws Exception
	{
		
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();
		
		//ask SiowYen how to type month to search
		for(Booking bookingValue : bookingList) {
			if(bookingValue.getRegEmail().equals(email)) {
				if(bookingValue.getCheckInDate().getMonth()+1 == Integer.parseInt(dateMonth))
				bookingDAO.viewBooking(bookingValue);
				
			}		
		}
		
		if(personType.equals("Host"))
		System.out.println("Total Commission to pay: " + commission);
		
		else
			System.out.println("Total Commission to receive: " + commission);
		
		AppsController AC = new AppsController();
		AC.enterToContinue();
	}
	
	
	public double calculateCommission(String dateMonth) throws Exception
	{
		//ask SiowYen How to type month to calculate
		for(Booking bookingValue : bookingList) {
			if(bookingValue.getRegEmail().equals(email)) {
				if(bookingValue.getCheckInDate().getMonth()+1 == Integer.parseInt(dateMonth))
				totalSales += bookingValue.getTotalAmount();
				break;
			}		
		}
		
		commission = totalSales * 5/100;
		return commission;
	}
	
	

	
}
