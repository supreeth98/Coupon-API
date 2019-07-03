package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="couponDetails")
public class Coupon implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@Column(name="couponCode")
	String couponCode;
	@Column(name=" Descripition")
	String  Descripition;
	@Column(name="min_cart_val")
	int min_cart_val;
	@Column(name="max_cart_val")
	int max_cart_val;
	@Column(name="max_usage")
	int max_usage;
	@Column(name="total_used")
	int total_used;
	@Column(name="coupon_type")
	int coupon_type;
	@Column(name="status")
	int status;
	@Column(name="max_discount_amt")
	int max_discount_amt;
	@Column(name="percent_val")
	float percent_val;
	@Column(name="flat_val")
	int flat_val;
	String start_date;
	String end_date;
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getDescripition() {
		return Descripition;
	}
	public void setDescripition(String descripition) {
		Descripition = descripition;
	}
	public int getMin_cart_val() {
		return min_cart_val;
	}
	public void setMin_cart_val(int min_cart_val) {
		this.min_cart_val = min_cart_val;
	}
	public int getMax_cart_val() {
		return max_cart_val;
	}
	public void setMax_cart_val(int max_cart_val) {
		this.max_cart_val = max_cart_val;
	}
	public int getMax_usage() {
		return max_usage;
	}
	public void setMax_usage(int max_usage) {
		this.max_usage = max_usage;
	}
	public int getTotal_used() {
		return total_used;
	}
	public void setTotal_used(int total_used) {
		this.total_used = total_used;
	}
	public int getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(int coupon_type) {
		this.coupon_type = coupon_type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMax_discount_amt() {
		return max_discount_amt;
	}
	public void setMax_discount_amt(int max_discount_amt) {
		this.max_discount_amt = max_discount_amt;
	}
	public float getPercent_val() {
		return percent_val;
	}
	public void setPercent_val(float d) {
		this.percent_val = d;
	}
	public int getFlat_val() {
		return flat_val;
	}
	public void setFlat_val(int flat_val) {
		this.flat_val = flat_val;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", couponCode=" + couponCode + ", Descripition=" + Descripition + ", min_cart_val="
				+ min_cart_val + ", max_cart_val=" + max_cart_val + ", max_usage=" + max_usage + ", total_used="
				+ total_used + ", coupon_type=" + coupon_type + ", status=" + status + ", max_discount_amt="
				+ max_discount_amt + ", percent_val=" + percent_val + ", flat_val=" + flat_val + ", start_date="
				+ start_date + ", end_date=" + end_date + "]";
	}
	
	
	
	
	
	
	


}
