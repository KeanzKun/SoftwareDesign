package delivery.classes;

import java.util.ArrayList;
import java.util.Scanner;

import delivery.database.*;

public class GenerateSalesReport {
	
	private double commission;
	private double totalSales;
	private String email;
	private ArrayList<Booking> bookingList;
	private BookingDAO bookingDAO = new BookingDAO();
	
	public GenerateSalesReport(String email){
		this.email = email;
		this.totalSales = 0;
	}
	
	public void printReport(String email) throws Exception
	{
		
		BookingFile BF = new BookingFile();
		bookingList = BF.retrieve();

		PremisesFile pf = new PremisesFile();
		ArrayList<Premises> premiseList = new ArrayList<Premises>();
		ArrayList<Premises> hostPremiseList = new ArrayList<Premises>();;
		premiseList = pf.retrieve();
		
		int year = getUserInput();
		int month = getUserInput();
		
		for(Premises premise:premiseList){
			if(premise.getRegEmail().equals(email)){
				hostPremiseList.add(premise);
			}
		}

		for(Booking bookingValue : bookingList) {
			for(Premises premise:hostPremiseList){
				if(bookingValue.getPremiseID() == premise.getPremiseID()){
						if(bookingValue.getCheckInDate().getYear() == year)
							if(bookingValue.getCheckInDate().getMonth() == month) {
								bookingDAO.viewBooking(bookingValue);
								System.out.println("================================");
					}		
		}
				}
			}
		System.out.println("Total Commission to pay: " + commission);
		
	}
	
	public double calculateCommission() throws Exception
	{
		int year = getUserInput();
		int month = getUserInput();

		for(Booking bookingValue : bookingList) {
			if(bookingValue.getRegEmail().equals(email)) {
				if(bookingValue.getCheckInDate().getYear() == year)
					if(bookingValue.getCheckInDate().getMonth() == month) {
						totalSales += bookingValue.getTotalAmount();
					}
			}		
		}
		
		commission = totalSales * 5/100;
		
		return commission;
	}
	
	
	public int getUserInput()
	{
		int userInput;
		do {
		userInput = 0;
		
		Scanner scanner = new Scanner(System.in);
		userInput = scanner.nextInt();
		scanner.nextLine();
		
		}while(userInput > 12 || userInput < 1);
		
		return userInput;
	}
	
}
