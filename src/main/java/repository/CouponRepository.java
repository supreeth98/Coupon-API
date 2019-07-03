package repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Coupon;


@Transactional
@Repository
public interface CouponRepository  extends CrudRepository<Coupon, Integer> {

	Coupon findByCouponCode(String couponCode);
	
}
