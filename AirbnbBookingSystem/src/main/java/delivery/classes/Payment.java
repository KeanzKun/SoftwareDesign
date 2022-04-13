package delivery.classes;

public class Payment {
	private String paymentID;
	private String paymentMethod;
	
	public Payment(String paymentID, String paymentMethod) {
		this.setPaymentID(paymentID);
		this.setPaymentMethod(paymentMethod);
	}
	
	public String getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
