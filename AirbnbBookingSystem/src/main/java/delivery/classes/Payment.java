package delivery.classes;

public class Payment {
	private String paymentID;
	
	public Payment(String paymentID) {
		this.setPaymentID(paymentID);
	}
	public String getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	
}
