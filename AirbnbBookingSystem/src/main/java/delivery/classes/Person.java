package delivery.classes;

public class Person {
	private String regEmail;
	private String name;
	private String ic;
	private String homeAddress;
	private String phoneNo;
	private String password;
	private PersonType personType;
	private String paymentID;
	
	public Person(String regEmail, String name, String ic, String homeAddress, String phoneNo, String password, PersonType personType, String paymentID) {
		this.setRegEmail(regEmail);
		this.name = name;
		this.ic = ic;
		this.setHomeAddress(homeAddress);
		this.setPhoneNo(phoneNo);
		this.setPassword(password);
		this.personType = personType;
		this.paymentID = paymentID;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegEmail() {
		return regEmail;
	}

	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public String getName() {
		return name;
	}

	public String getIc() {
		return ic;
	}
	
	public String getPaymentID() {
		return this.paymentID;
	}

	public void setPaymentID(String ID) {
		this.paymentID = ID;
	}
	public boolean equals(String regEmail) {
		boolean sameObject= false;
		
		if(this.getRegEmail().equals(regEmail)) {
			sameObject = true;
		}
		return sameObject;
	}
	
	public boolean equals(String regEmail, String password) {
		boolean sameObject= false;
		
		if(this.getRegEmail().equals(regEmail) && this.password.equals(password)) {
			sameObject = true;
		}
		return sameObject;
	}
	
	@Override
	public String toString() {
		
		return this.getRegEmail() + ";" + this.getName() + ";" + this.getIc() + ";" + this.getHomeAddress() + ";" + this.getPhoneNo() + ";" + this.password + ";" + this.getPersonType() + ";" + this.getPaymentID();
		
	}
}
