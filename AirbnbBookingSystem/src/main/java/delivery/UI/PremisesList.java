package delivery.UI;

import java.util.ArrayList;

import delivery.classes.Person;
import delivery.classes.PersonType;
import delivery.classes.Premises;
import delivery.database.PremisesFile;

public class PremisesList {
	
	PremisesFile file = new PremisesFile();
	ArrayList<Premises> premiseList = file.retrieve();
	public void AddNewPremise(Person activePerson, String name, String address, String type, int capacity,
			int numRoom) {
		
		if(activePerson.getPersonType() == PersonType.Host) {
			Premises newPremise = new Premises(getNewPremiseID(), name, address, type, capacity, numRoom, activePerson.getRegEmail());
			premiseList.add(newPremise);
		}
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
	
	
}
