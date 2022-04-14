package delivery.UI;

import java.util.ArrayList;

import delivery.classes.Person;
import delivery.classes.PersonType;
import delivery.classes.Premises;
import delivery.database.PremisesFile;
import java.util.Scanner;
public class PremisesList {
	
	PremisesFile file = new PremisesFile();
	ArrayList<Premises> premiseList = file.retrieve();
	public void AddNewPremise(Person activePerson, String name, String address, String type, int capacity,
			int numRoom, double price) {
		
		if(activePerson.getPersonType() == PersonType.Host) {
			Premises newPremise = new Premises(getNewPremiseID(), name, address, type, capacity, numRoom, price, activePerson.getRegEmail());
			premiseList.add(newPremise);
		}
		file.save(premiseList);
		file.retrieve();
	}
	
	private int getNewPremiseID() {
		int newPremiseID = 0;
		
		if(premiseList.size() == 0) {
			newPremiseID = 1;
		}
		else {
			for(Premises premise: premiseList) {
				if(premise.getPremiseID() > newPremiseID) {
					newPremiseID = premise.getPremiseID();
				}
			}
			newPremiseID += 1;
		}
		
		return newPremiseID;
		
	}
	
	public Premises selectPremiseList()
	{
		String userInput = "";
		ArrayList<Premises> premiseListTemp = new ArrayList<Premises>(); 
		int printedResultCount = 0;
		
		for(int i = 0; i < premiseList.size(); i++)
		{
			if(premiseList.get(i).getActive()) {
			
			System.out.println("No: " + (printedResultCount+1));
			System.out.println("Premise ID: " + premiseList.get(i).getPremiseID());
			System.out.println("Name : " + premiseList.get(i).getName());
			System.out.println("Type : " + premiseList.get(i).getType());
			System.out.println("Capacity : " + premiseList.get(i).getCapacity());
			System.out.println("Price : " + premiseList.get(i).getPrice());
			System.out.println("========================================================");
			printedResultCount ++;
			premiseListTemp.add(premiseList.get(i));
			
			}
			if(printedResultCount % 5 == 0 || i == premiseList.size()-1)
			{
				
				do {
					System.out.println("Enter N for next page, Q for quit");
					System.out.println("Enter No to book the premise.");
					Scanner scanner = new Scanner(System.in);
					userInput = scanner.nextLine();
					
					if(userInput.equals("n"))
					{
						printedResultCount = 0;
						
						if(i == premiseList.size() - 1) {
						System.out.println("\n\nNo more premise to show......\n\n");
						i = 0;
						}
			
					}
					else if(userInput.equals("q"))
					{
						return null;
					}
					else if(userInput.equals("1"))
					{
						return premiseListTemp.get(0);
					}
					else if(userInput.equals("2"))
					{
						return premiseListTemp.get(1);
					}
					else if(userInput.equals("3"))
					{
						return premiseListTemp.get(2);
					}
					else if(userInput.equals("4"))
					{
						return premiseListTemp.get(3);
					}
					else if(userInput.equals("5"))
					{
						return premiseListTemp.get(4);
					}
					
					else
						System.out.println("Please enter a proper input.");
					
				}while(!userInput.equals("n") && !userInput.equals("q") 
						&& !userInput.equals("0")&& !userInput.equals("1")&& !userInput.equals("2")
						&& !userInput.equals("3")&& !userInput.equals("4")); 
				
				premiseListTemp.clear();
				
			}
		}
		
		return null;
		
	}
	
	public Premises selectPremise(String hostEmail)
	{
		int userInput = 0;
		ArrayList<Premises> premiseListTemp = new ArrayList<Premises>(); 
		Scanner scanner = new Scanner(System.in);
		
		for(int i = 0; i < premiseList.size(); i++)
		{
			if(premiseList.get(i).getRegEmail().equals(hostEmail)) {
			
			System.out.println("No: " + i);
			System.out.println("Premise ID: " + premiseList.get(i).getPremiseID());
			System.out.println("Name : " + premiseList.get(i).getName());
			System.out.println("Type : " + premiseList.get(i).getType());
			System.out.println("Capacity : " + premiseList.get(i).getCapacity());
			System.out.println("Price : " + premiseList.get(i).getPrice());
			System.out.println("========================================================");
			premiseListTemp.add(premiseList.get(i));
			
			do {
			System.out.println("Please enter your choice.");
			userInput = scanner.nextInt();		
			scanner.nextLine();
			
			if(userInput > premiseListTemp.size()-1 || userInput < 0)
				System.out.println("Invalid number input");
			
			}while(userInput > premiseListTemp.size()-1 || userInput < 0);
				
			}
	}
		premiseListTemp.clear();
		return premiseList.get(userInput);
	}
}
