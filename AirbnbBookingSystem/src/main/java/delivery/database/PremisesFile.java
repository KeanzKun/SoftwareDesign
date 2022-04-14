package delivery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


import delivery.classes.Premises;

public class PremisesFile extends AbstractFile{
	final static String fileName = "Premises.txt";
	public PremisesFile() {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	public void save(ArrayList<Premises> premisesList) {
        try {
            fileWriter = new FileWriter(fileName, false);
            printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            for (Premises i : premisesList) {
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

    public ArrayList<Premises> retrieve() {
    	ArrayList<Premises> premisesList = new ArrayList<Premises>();
        try {
            file = new File(fileName);
            fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String str = fileScanner.nextLine();
                premisesList.add(readPremisesFile(str));
            }
        } catch (FileNotFoundException e) {
            System.out.print("Can't read file");
        }
        
        return premisesList;
    }

    private Premises readPremisesFile(String str) {
        String name = null, address = null, type = null, email = null;
        int capacity = 0, numRoom = 0, premiseID = 0;
        double price = 0;
        Boolean active = null;
        Scanner sc = new Scanner(str);
        sc.useDelimiter(AbstractFile.getDELIMITER());
        
        while (sc.hasNext()) {
        	premiseID = Integer.parseInt(sc.next()); 
        	name = sc.next(); 
        	address = sc.next(); 
        	type = sc.next(); 
        	capacity = Integer.parseInt(sc.next());
        	numRoom = Integer.parseInt(sc.next());
            price = Double.parseDouble(sc.next());   
            
        	email = sc.next(); 
        	active = Boolean.parseBoolean(sc.next());
        }
       
        Premises readPremise = new Premises(premiseID, name, address, type, capacity, numRoom,price, email, active); 
        
        sc.close();
        return readPremise;
        
    }
}
