package delivery.UI;

import java.util.ArrayList;

import delivery.classes.Person;
import delivery.classes.PersonType;
import delivery.database.PersonFile;

public class PersonList {
	
	ArrayList<Person> personList = new ArrayList<Person>();
	PersonFile file = new PersonFile();
	
	//retrieve person data
	public PersonList() {
		personList = file.retrieve();
	}
	
	//add person into list
	public void AddNewPerson(String regEmail, String name, String ic, String homeAddress, String phoneNo, String password, PersonType personType) {
		Person newPerson = new Person(regEmail, name, ic, homeAddress, phoneNo, password, personType);
		personList.add(newPerson);
		
		file.save(personList);
	}

	public Person getPerson(String email, String password) {
		
		for(Person person: personList) {
			if(person.equals(email, password)) {
				return person;
			}
		}
		return null;
	}
	
	
}
