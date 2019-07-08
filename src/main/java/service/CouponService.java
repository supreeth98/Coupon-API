package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.AddCoupon_DTO;
import model.Coupon;
import model.CouponResponse_DTO;
import model.ValidateRequest_DTO;
import model.ValidateResponse_DTO;
import model.errors;
import repository.CouponRepository;
import repository.ErrorsRespository;

@Service
public class CouponService {

	// @Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ErrorsRespository errorsRespository;
	
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

	public CouponResponse_DTO getBycoupon(String couponCode) {
		//errors e= errorsRespository.findByerrorCode(1000);
		Coupon c = (Coupon) couponRepository.findByCouponCode(couponCode);
		if(c!=null) {
		CouponResponse_DTO response=new CouponResponse_DTO();
		response.setId(Integer.toString(c.getId()));
		response.setCouponCode(c.getCouponCode());
		response.setDescripition(c.getDescripition());
		response.setMin_cart_val(Integer.toString(c.getMin_cart_val()));
		response.setMax_cart_val(Integer.toString(c.getMax_cart_val()));
		response.setTotal_used(Integer.toString(c.getTotal_used()));
		response.setCoupon_type(Integer.toString(c.getCoupon_type()));
		response.setMax_usage(Integer.toString(c.getMax_cart_val()));
		response.setStatus(Integer.toString(c.getStatus()));
		response.setMax_discount_amt(Integer.toString(c.getMax_discount_amt()));
		response.setPercent_val(Float.toString(c.getPercent_val()));
		response.setFlat_val(Integer.toString(c.getFlat_val()));
		response.setStart_date(c.getStart_date().toString());
		response.setEnd_date(c.getEnd_date().toString());
		response.setEstatus("0");
		response.setErrorCode("");
		response.setError_msg("");
		return response;
		}  
		else {
			CouponResponse_DTO response1=new CouponResponse_DTO();
			response1.setId("");
			response1.setCouponCode("");
			response1.setDescripition("");
			response1.setMin_cart_val("");
			response1.setMax_cart_val("");
			response1.setTotal_used("");
			response1.setCoupon_type("");
			response1.setStatus("");
			response1.setMax_usage("");
			response1.setMax_discount_amt("");
			response1.setPercent_val("");
			response1.setFlat_val("");
			response1.setStart_date("");
			response1.setEnd_date("");
			response1.setEstatus("1");
			response1.setErrorCode("1000");
			response1.setError_msg("coupon does not exist");
			return response1;
		}
		

	}

	public String validate(ValidateRequest_DTO obj) throws ParseException {
		String CouponCode = obj.getCouponCode();
		int CartAmt = obj.getCartAmt();
		int ctr;
		boolean flag = false;
		Coupon c = getBycoupon2(CouponCode);
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

	
	public ValidateResponse_DTO ValidateWithInfo(ValidateRequest_DTO obj) throws ParseException {
		ValidateResponse_DTO d = new ValidateResponse_DTO();
		String CouponCode = obj.getCouponCode();
		int CartAmt = obj.getCartAmt();

		float percent;
		float discount_amt;
		int final_discount_amt;
		int bill_amt;
		int final_user_pts;
		Coupon c = getBycoupon2(CouponCode);

		if (c == null) {
			d.setStatus("1");
			errors e= new errors();
			e=errorsRespository.findByerrorCode(1000);
			d.setError_msg(e.getError_msg());
			d.setErrorCode("1000");
			d.setDiscount_amt("0");
			d.setFbill_amt(Integer.toString(obj.getCartAmt()));
			return d;
		}
		String start_date = c.getStart_date();
		String end_date = c.getEnd_date();
		if (c.getStatus() != 1) {
			d.setStatus("1");
			 errors e=errorsRespository.findByerrorCode(2000);
			d.setError_msg(e.getError_msg());
			d.setErrorCode("2000");
			d.setDiscount_amt("0");
			d.setFbill_amt(Integer.toString(obj.getCartAmt()));

			return d;
		}
		if (!date_validate(start_date, end_date)) {
			d.setStatus("1");
			 errors e=errorsRespository.findByerrorCode(3000);
			 d.setError_msg(e.getError_msg());
				d.setErrorCode("3000");
			d.setDiscount_amt("0");
			d.setFbill_amt(Integer.toString(obj.getCartAmt()));

			return d;
		}

		if (c.getTotal_used() >= c.getMax_usage()) {
			d.setStatus("1");
			 errors e=errorsRespository.findByerrorCode(4000);
			 d.setError_msg(e.getError_msg());
				d.setErrorCode("4000");
			d.setDiscount_amt("0");
			d.setFbill_amt(Integer.toString(obj.getCartAmt()));

			return d;
		}

		if (c.getMin_cart_val() > obj.getCartAmt() || c.getMax_cart_val() < obj.getCartAmt()) {
			d.setStatus("1");
			 errors e=errorsRespository.findByerrorCode(5000);
			 d.setError_msg(e.getError_msg());
				d.setErrorCode("5000");
			d.setDiscount_amt("0");
			d.setFbill_amt(Integer.toString(obj.getCartAmt()));

			return d;
		}
		d.setStatus("0");
		d.setErrorCode("");
		d.setError_msg("");	
		if (c.getCoupon_type() == 0) {
			percent = c.getPercent_val();
			discount_amt = obj.getCartAmt() * percent;
			final_discount_amt = (int) Math.ceil(discount_amt);

			if (final_discount_amt > c.getMax_discount_amt()) {
				final_discount_amt = c.getMax_discount_amt();
				bill_amt = obj.getCartAmt() - final_discount_amt;		
				d.setDiscount_amt(Integer.toString(final_discount_amt));
				d.setFbill_amt(Integer.toString(bill_amt));
				return d;
			} else {
				bill_amt = obj.getCartAmt() - final_discount_amt;
				d.setDiscount_amt(Integer.toString(final_discount_amt));
				d.setFbill_amt(Integer.toString(bill_amt));
				return d;
			}
		}
		else if(c.getCoupon_type()==1) {
			int flat_val = c.getFlat_val();
			if(flat_val>=obj.getCartAmt()) {
				d.setDiscount_amt(Integer.toString(obj.getCartAmt()-1));
				d.setFbill_amt("1");
				return d;
			}

			d.setDiscount_amt(Integer.toString(flat_val));
			d.setFbill_amt(Integer.toString(obj.getCartAmt()-flat_val));
			return d;
		}
		return null;

	}

	private Coupon getBycoupon2(String couponCode) {
		Coupon c=couponRepository.findByCouponCode(couponCode);
		return c;
	}

	public CouponResponse_DTO changeStatus(String couponCode) {
		Coupon c= getBycoupon2(couponCode);
		if(c!=null) {
		c.setStatus(0);
		couponRepository.save(c);
		CouponResponse_DTO response=new CouponResponse_DTO();
		response.setId(Integer.toString(c.getId()));
		response.setCouponCode(c.getCouponCode());
		response.setDescripition(c.getDescripition());
		response.setMin_cart_val(Integer.toString(c.getMin_cart_val()));
		response.setMax_cart_val(Integer.toString(c.getMax_cart_val()));
		response.setTotal_used(Integer.toString(c.getTotal_used()));
		response.setCoupon_type(Integer.toString(c.getCoupon_type()));
		response.setMax_usage(Integer.toString(c.getMax_cart_val()));
		response.setStatus(Integer.toString(c.getStatus()));
		response.setMax_discount_amt(Integer.toString(c.getMax_discount_amt()));
		response.setPercent_val(Float.toString(c.getPercent_val()));
		response.setFlat_val(Integer.toString(c.getFlat_val()));
		response.setStart_date(c.getStart_date().toString());
		response.setEnd_date(c.getEnd_date().toString());
		response.setEstatus("0");
		response.setErrorCode("");
		response.setError_msg("");
		return response;
		
		}
		else {
			CouponResponse_DTO response1=new CouponResponse_DTO();
			response1.setId("");
			response1.setCouponCode("");
			response1.setDescripition("");
			response1.setMin_cart_val("");
			response1.setMax_cart_val("");
			response1.setTotal_used("");
			response1.setCoupon_type("");
			response1.setStatus("");
			response1.setMax_usage("");
			response1.setMax_discount_amt("");
			response1.setPercent_val("");
			response1.setFlat_val("");
			response1.setStart_date("");
			response1.setEnd_date("");
			response1.setEstatus("1");
			response1.setErrorCode("1000");
			response1.setError_msg("coupon does not exist");
			return response1;
			
		}
	}

	public Coupon AddCoupon(AddCoupon_DTO obj) {
		Coupon coupon = new Coupon();
		coupon.setTotal_used(0);
		coupon.setDescripition(obj.getDescripition());
		coupon.setCoupon_type(obj.getCoupon_type());
		coupon.setMin_cart_val(obj.getMin_cart_val());
		coupon.setMax_cart_val(obj.getMax_cart_val());
		coupon.setStatus(1);
		coupon.setMax_usage(obj.getMax_usage());
		coupon.setMax_discount_amt(obj.getMax_discount_amt());
		coupon.setPercent_val(obj.getPercent_val());
		coupon.setFlat_val(obj.getFlat_val());
		coupon.setStart_date(obj.getStart_date());
		coupon.setEnd_date(obj.getEnd_date());

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz"; 
		String sb = obj.getBaseCouponCode();
		System.out.println(sb);
		int n=sb.length();

		for (int i = n; i <n+5 ; i++) { 
			int index = (int)(AlphaNumericString.length()* Math.random()); 
			sb+=(AlphaNumericString .charAt(index)); 
		}
		coupon.setCouponCode(sb);
		couponRepository.save(coupon);
		return coupon;
	}

}
