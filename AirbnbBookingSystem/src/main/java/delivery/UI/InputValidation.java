package delivery.UI;

import java.util.*;
import java.util.regex.Pattern;

import delivery.classes.PersonType;



public class InputValidation {
	private static final Scanner input = new Scanner(System.in);
	private static final String stringError = "Sorry, cannot contain ;";

	public static String readString(String info) {
		String readInput = "";
		
		while (true) {
            System.out.printf("%nEnter %s: ", info);
            readInput = input.nextLine();
            if (readInput.contains(";")) {
                System.out.printf("%s%n", stringError);
            } else if (readInput.equals("")) {
                System.out.printf("\nPlease enter a %s.", info);
            } else {
                break;
            }
        }
		
		return readInput;
	}
	
	public static String readEmail(String info) {
		String readInput = "";
		String  regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		while (true) {
            System.out.printf("%nEnter %s: ", info);
            readInput = input.nextLine();
            if(patternMatches(readInput, regexPattern)) {
            	if (readInput.contains(";")) {
                    System.out.printf("%s%n", stringError);
                } else if (readInput.equals("")) {
                    System.out.printf("\nPlease enter a %s.", info);
                } else {
                    break;
                }
            }
            else {
            	System.out.printf("\nPlease enter a valid Email %s.");
            }
            
        }
		
		return readInput;
	}
	
	
	public static boolean patternMatches(String emailAddress, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	public static int readPositiveInt(String info, int min, int max) {
        int readInput = 0;
        String errorMessage = "Please enter a positive integer.";

        while (true) {
            System.out.printf("%nEnter %s: ", info);
            try {
                readInput = Integer.parseInt(input.nextLine());
                if (readInput < 1) {
                    System.out.println(errorMessage);
                }
                else if(readInput > max || readInput < min) {
                	System.out.println("The value exceed the valid value");
                }
                else {
                	return readInput;
                }
                    
             
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }
	
	public static PersonType readPersonType() {
		System.out.println("Enter 1 to 3 to choose your user type.");
		System.out.println("1. User Account");
		System.out.println("2. Host Account");
		System.out.println("3. Admin Account");
		int choice = readPositiveInt("",1,3);
		switch (choice){
			case 1: return PersonType.User;
			case 2: return PersonType.Host;
			case 3: return PersonType.Admin;
			default: return null;
		}
	}
}
