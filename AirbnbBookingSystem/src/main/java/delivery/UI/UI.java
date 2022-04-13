package delivery.UI;

import java.util.Scanner;

public class UI {

	//display header
	public static void header(String heading) {
		System.out.printf("%n%n");
        System.out.print("=======================================");
        System.out.printf("%n%n");
        System.out.print("            ");
        System.out.print(heading);
        System.out.printf("%n%n");
        System.out.print("=======================================");
        System.out.printf("%n%n");
	}
	
	public static void displayEnterMainMenu() {
		
	}
	
	public static void displayUserMainMenu() {
		
	}
	
	public static void displayAdminMainMenu() {
		
	}

	public static void displayHostMainMenu() {
		
	}
	
	public static int askEventNo(int beginEventNo, int endEventNo) {
        if (beginEventNo > endEventNo) {
            System.out.printf("%nBug at UI.askEventNo(int, int): the beginEventNo should not greater than the endEventNo.");
            System.exit(1);
        }

        int eventNo;
        final String errorMessage = "Sorry, input failed. Please enter the number of option you want to perform.\n";

        while (true) {
            try {
                System.out.printf("%nPlease select> ");
                Scanner sc = new Scanner(System.in);
                eventNo = Integer.parseInt(sc.nextLine());

                if (eventNo >= beginEventNo && eventNo <= endEventNo) {
                    break;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
        return eventNo;
    }
}
