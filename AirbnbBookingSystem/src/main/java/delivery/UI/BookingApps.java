package delivery.UI;

import delivery.classes.*;

public class BookingApps {
	
	private static AppsController controller = new AppsController();
	public static void main(String[] args) {
		int select = 1;
		boolean exit = false;
		Person entryPerson = login();
		
		do {
			UI.header("Main Menu");
		switch(entryPerson.getPersonType()) {
		case Admin:
			UI.displayAdminMainMenu();
			//tt
			
			break;
		case User:
			UI.displayUserMainMenu();
			
			break;
		case Host:
			UI.displayHostMainMenu();
			
			
			break;
		}
		}while(!exit);
	}
	
	private static Person login() {
		Person loginUserDetail = null;
		boolean login_Next = false;
		int loginOption = 0;
		
		do {
			UI.header("Welcome to the Airbnb Booking System");
			UI.displayEnterMainMenu();
			loginOption = UI.askEventNo(1, 2);
			if(loginOption == 1) {
				String email = InputValidation.readString("Email");
				String password = InputValidation.readString("Password");
				loginUserDetail = controller.findPerson(email, password);
				if(loginUserDetail != null) {
					login_Next = true;
				}
				else {
					System.out.println("Invalid Login Credential!!!");
				}
			}
			else if(loginOption == 2) {
				UI.header("Sign Up");
				System.out.println("Enter New Details:");
				controller.createNewUser();
			}
			
			
		} while(!login_Next);
		
		return loginUserDetail;
	}
	
	
}
