package controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Coupon;
import model.CouponCodeRequest_DTO;
import model.CouponRequest_DTO;
import model.ValidateRequest_DTO;
import model.ValidateResponse_DTO;
import service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	
	//@Autowired
	private CouponService couponService;
	
	
	
	  @Autowired public CouponController(CouponService couponService) {
	  this.couponService = couponService; }
	 
	 
	@Cacheable(value = "Coupons")
	@RequestMapping(value = "/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAll(){
		
	return couponService.getAll();
		
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getById(@RequestBody CouponRequest_DTO obj){
		
		Optional<Coupon> c=couponService.get(obj.getId());
		if(c.isPresent())
			return ResponseEntity.ok(couponService.get(obj.getId()));
		else
			return ResponseEntity.notFound().build();
	}
	
	//@Cacheable(value = "Coupons")
	@RequestMapping(value = "/getByCouponCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon getByCouponCode(@RequestBody CouponCodeRequest_DTO obj){
		
		return couponService.getBycoupon(obj.getCouponCode());
		
	}
	
	@RequestMapping(value = "/validate1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity ValidateCoupon(@RequestBody ValidateRequest_DTO obj) throws ParseException{
		
		return ResponseEntity.ok(couponService.validate(obj));
		
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ValidateResponse_DTO> ValidateCoupon1(@RequestBody ValidateRequest_DTO obj) throws ParseException{
		
		return ResponseEntity.ok(couponService.ValidateWithInfo(obj));
		
	}
	
	

}