package model;

public class ValidateResponse_DTO {
	
	String discount_amt;
	String fbill_amt;
	String status;
	String errorCode;
	String error_msg;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		this.status = string;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getDiscount_amt() {
		return discount_amt;
	}
	public void setDiscount_amt(String discount_amt) {
		this.discount_amt = discount_amt;
	}

	
	public String getFbill_amt() {
		return fbill_amt;
	}
	public void setFbill_amt(String fbill_amt) {
		this.fbill_amt = fbill_amt;
	}
	
	

}
