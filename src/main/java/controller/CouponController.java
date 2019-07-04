package controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.AddCoupon_DTO;
import model.Coupon;
import model.CouponCodeRequest_DTO;
import model.CouponRequest_DTO;
import model.Status_DTO;
import model.ValidateRequest_DTO;
import model.ValidateResponse_DTO;
import service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	
	//@Autowired
	private CouponService couponService;
	
	@Autowired
	private  RedisTemplate<String, Coupon> resdisTemplate;
	
	 
	  @Autowired public CouponController(CouponService couponService) {
	  this.couponService = couponService; }
	 
	 
	//@Cacheable(value = "Coupons")
	@RequestMapping(value = "/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAll(){
		//resdisTemplate.opsForValue().set("coupon",(List<Coupon>) couponService.getAll() );
		
		
		
	return couponService.getAll();
		
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getById(@RequestBody CouponRequest_DTO obj){
		
		Optional<Coupon> c=couponService.get(obj.getId());
		if(c.isPresent())
			return ResponseEntity.ok(couponService.get(obj.getId()));
		else {
			Status_DTO e= new Status_DTO();
			e.setErroMsg("Coupon does not exist");
			return ResponseEntity.ok(e);
		}
	}
	
	//@Cacheable(value = "Coupons")
	@RequestMapping(value = "/getByCouponCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getByCouponCode(@RequestBody CouponCodeRequest_DTO obj){
		
		Coupon c=couponService.getBycoupon(obj.getCouponCode());
		
		if(c==null) {
			Status_DTO w= new Status_DTO();
			w.setErroMsg(" This CouponCode does not exist");
			return ResponseEntity.ok(w);
		}
		else
			return ResponseEntity.ok(couponService.getBycoupon(obj.getCouponCode()));
		
		//return couponService.getBycoupon(obj.getCouponCode());
		
	}
	
	@RequestMapping(value = "/validate1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity ValidateCoupon(@RequestBody ValidateRequest_DTO obj) throws ParseException{
		
		return ResponseEntity.ok(couponService.validate(obj));
		
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ValidateResponse_DTO> ValidateCoupon1(@RequestBody ValidateRequest_DTO obj) throws ParseException{
		
		return ResponseEntity.ok(couponService.ValidateWithInfo(obj));
		
	}
	
	@RequestMapping(value = "/getCache", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon getByIdCache(@RequestBody CouponRequest_DTO obj){
		
		Optional<Coupon> c=couponService.get(obj.getId());
		Coupon coupon=c.get();
		resdisTemplate.opsForValue().set("coupon",(Coupon) coupon);
			return coupon;
		
	}
	
	@RequestMapping(value = "/SetInactive", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon SetStatusInactive(@RequestBody CouponCodeRequest_DTO obj){
		
		return couponService.changeStatus(obj.getCouponCode());	
	}
	
	@RequestMapping(value = "/AddCoupon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon Addcoupon(@RequestBody AddCoupon_DTO obj){
		
		return couponService.AddCoupon(obj);	
	}
	
}
