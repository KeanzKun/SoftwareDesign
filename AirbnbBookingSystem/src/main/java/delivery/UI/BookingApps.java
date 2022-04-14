package delivery.UI;

import java.io.FileNotFoundException;
import java.text.ParseException;

import delivery.classes.*;

public class BookingApps {
	
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		int select = 1;
		boolean exit = false;
		Person entryPerson = UI.login();
		
		do {
			UI.header("Main Menu");
		switch(entryPerson.getPersonType()) {
		case Admin:
			UI.displayAdminMainMenu(entryPerson);
			//tt
			
			break;
		case User:
			UI.displayUserMainMenu(entryPerson);
			
			break;
		case Host:
			UI.displayHostMainMenu(entryPerson);
			
			
			break;
		}
		}while(!exit);
	}
	
	
	
	
}
