package model;

public class ValidateResponse_DTO {
	String status;
	int discount_amt;
	int fbill_amt;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDiscount_amt() {
		return discount_amt;
	}
	public void setDiscount_amt(int discount_amt) {
		this.discount_amt = discount_amt;
	}

	
	public int getFbill_amt() {
		return fbill_amt;
	}
	public void setFbill_amt(int fbill_amt) {
		this.fbill_amt = fbill_amt;
	}
	
	

}
