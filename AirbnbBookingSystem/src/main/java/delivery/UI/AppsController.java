package delivery.UI;

import delivery.classes.Person;
import delivery.classes.PersonType;

public class AppsController {
	PersonList personList = new PersonList();
	public void createNewUser() {
		
		PersonType personType = InputValidation.readPersonType();
		String regEmail = InputValidation.readEmail("Email");
		String name = InputValidation.readString("Name");
		String ic = InputValidation.readString("IC");
		String homeAddress = InputValidation.readString("Home Address");
		String phoneNo = InputValidation.readString("Phone Number");
		String password = InputValidation.readString("Password");
		
		
		personList.AddNewPerson(regEmail, name, ic, homeAddress, phoneNo, password, personType);
		System.out.println("User Added Succesfully!");
	}

	public Person findPerson(String email,String password) {
		return personList.getPerson(email, password);

	}
}
