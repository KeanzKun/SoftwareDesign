package delivery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import delivery.classes.Person;
import delivery.classes.PersonType;

public class PersonFile extends AbstractFile {
	final static String fileName = "Person.txt";
	public PersonFile() {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	 public void save(ArrayList<Person> personList) {
	        try {
	            fileWriter = new FileWriter(fileName, false);
	            printWriter = new PrintWriter(fileWriter, false);
	            printWriter.flush();
	            for (Person i : personList) {
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

	    public ArrayList<Person> retrieve() {
	    	ArrayList<Person> personList = new ArrayList<Person>();
	        try {
	            file = new File(fileName);
	            fileScanner = new Scanner(file);

	            while (fileScanner.hasNextLine()) {
	                String str = fileScanner.nextLine();
	                personList.add(readPersonFile(str));
	            }
	        } catch (FileNotFoundException e) {
	            System.out.print("Can't read file");
	        }
	        
	        return personList;
	    }

	    private Person readPersonFile(String str) {
	        String email = null, name = null, ic = null, address = null, telNo = null, password = null, type = null, paymentID = null;
	        PersonType persontype = null;
	        Scanner sc = new Scanner(str);
	        sc.useDelimiter(AbstractFile.getDELIMITER());
	        
	        while (sc.hasNext()) {
	        	email = sc.next();
	        	name = sc.next();
	        	ic = sc.next(); 
	        	address = sc.next(); 
	        	telNo = sc.next(); 
	        	password = sc.next();
	        	type = sc.next();
				paymentID = sc.next();
	        }
	        switch(type){
	        	case "User": persontype = PersonType.User; break;
	        	case "Host": persontype = PersonType.Host; break;
	        	case "Admin": persontype = PersonType.Admin; break;
	        }
	        Person readPerson = new Person(email, name, ic, address, telNo, password, persontype, paymentID); 
	        sc.close();
	        return readPerson;

	        
	    }
}
