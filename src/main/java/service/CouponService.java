package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import model.Coupon;
import model.ValidateRequest_DTO;
import model.ValidateResponse_DTO;
import repository.CouponRepository;

@Service
public class CouponService {

	// @Autowired
	private CouponRepository couponRepository;

	@Autowired
	public CouponService(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}

	public List<Coupon> getAll() {
		return (List<Coupon>) couponRepository.findAll();
	}

	public Optional<Coupon> get(int id) {
		
			 return couponRepository.findById(id);
		 

	}

	public Coupon getBycoupon(String couponCode) {
		Coupon c = (Coupon) couponRepository.findByCouponCode(couponCode);
		if(c==null)
			return null;
		else
		return c;

	}

	public String validate(ValidateRequest_DTO obj) throws ParseException {
		String CouponCode = obj.getCouponCode();
		int CartAmt = obj.getCartAmt();
		int ctr;
		boolean flag = false;
		Coupon c = getBycoupon(CouponCode);
		if (c == null) {
			flag = false;
		} else {
			String start_date = c.getStart_date();
			String end_date = c.getEnd_date();
			if (c.getMin_cart_val() < CartAmt && c.getStatus() == 1 && c.getTotal_used() < c.getMax_usage()
					&& date_validate(start_date, end_date))
				flag = true;
			ctr = c.getTotal_used() + 1;
			c.setTotal_used(ctr);
			couponRepository.save(c);

		}

		if (flag)
			return "Coupon is Valid";

		else
			return "Coupon Invalid";
	}

	private boolean date_validate(String start_date, String end_date) throws ParseException {

		LocalDate localDate = LocalDate.now();
		String curr_date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date cdate = format.parse(curr_date);
		Date sdate = format.parse(start_date);
		Date edate = format.parse(end_date);

		if (sdate.compareTo(cdate) <= 0 && edate.compareTo(cdate) >= 0)
			return true;

		else
			return false;
	}

	//@SuppressWarnings("unused")
	public ValidateResponse_DTO ValidateWithInfo(ValidateRequest_DTO obj) throws ParseException {
		ValidateResponse_DTO d = new ValidateResponse_DTO();
		String CouponCode = obj.getCouponCode();
		int CartAmt = obj.getCartAmt();

		float percent;
		float discount_amt;
		int final_discount_amt;
		int bill_amt;
		int final_user_pts;
		Coupon c = getBycoupon(CouponCode);
		
		if (c == null) {
			d.setStatus("Coupon code you are looking for does not exist");
			d.setDiscount_amt(0);
			d.setFbill_amt(obj.getCartAmt());
			return d;
		}
		String start_date = c.getStart_date();
		String end_date = c.getEnd_date();
		if (c.getStatus() != 1) {
			d.setStatus("Coupon is not active");
			d.setDiscount_amt(0);
			d.setFbill_amt(obj.getCartAmt());

			return d;
		}
		if (!date_validate(start_date, end_date)) {
			d.setStatus("Coupon can not be used because it is not being used in defined time period");
			d.setDiscount_amt(0);
			d.setFbill_amt(obj.getCartAmt());

			return d;
		}

		if (c.getTotal_used() >= c.getMax_usage()) {
			d.setStatus("Coupon has exceeded it usage limit");
			d.setDiscount_amt(0);
			d.setFbill_amt(obj.getCartAmt());

			return d;
		}

		if (c.getMin_cart_val() > obj.getCartAmt() || c.getMax_cart_val() < obj.getCartAmt()) {
			d.setStatus("Cart amount is not in defined range");
			d.setDiscount_amt(0);
			d.setFbill_amt(obj.getCartAmt());

			return d;
		}
		d.setStatus("Coupon applied succesfully");
		if (c.getCoupon_type() == 0) {
			percent = c.getPercent_val();
			discount_amt = obj.getCartAmt() * percent;
			final_discount_amt = (int) Math.ceil(discount_amt);

			if (final_discount_amt > c.getMax_discount_amt()) {
				final_discount_amt = c.getMax_discount_amt();
				bill_amt = obj.getCartAmt() - final_discount_amt;

				d.setDiscount_amt(final_discount_amt);
				d.setFbill_amt(bill_amt);

				return d;
			} else {
				bill_amt = obj.getCartAmt() - final_discount_amt;
				d.setDiscount_amt(final_discount_amt);
				d.setFbill_amt(bill_amt);
				return d;
			}
		}
		else if(c.getCoupon_type()==1) {
			int flat_val = c.getFlat_val();
			if(flat_val>=obj.getCartAmt()) {
				d.setStatus("Coupon applied succesfully."
						+ "Please note that you have been charged 1 point as cart amount is less than or equal to the discount amount applied.");
				d.setDiscount_amt(obj.getCartAmt()-1);
				d.setFbill_amt(1);
				return d;
			}
			
			d.setDiscount_amt(flat_val);
			d.setFbill_amt(obj.getCartAmt()-flat_val);
			return d;
		}
		return null;

	}

}
