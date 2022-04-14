package delivery.UI;

import java.util.Scanner;

import delivery.classes.Person;
import delivery.classes.PersonType;

public class AppsController {
	PersonList personList = new PersonList();
	PremisesList premisesList = new PremisesList();
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

	public int getMenuInput() {

		System.out.print("Enter Choice: ");

		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		scanner.nextLine();

		return input;
	}

	
	public void createNewPremises(Person activePerson) {
		
		String name = InputValidation.readString("Premise Name");
		String address = InputValidation.readString("Premise Address");
		String type = InputValidation.readString("Premise Type");
		int capacity = InputValidation.readPositiveInt("Premise Capacity", 1, 50);
		int numRoom = InputValidation.readPositiveInt("Premise Room Number", 1, 50);
		
		premisesList.AddNewPremise(activePerson, name, address, type, capacity, numRoom);
		System.out.println("Premise Added Succesfully!");
	}
}
