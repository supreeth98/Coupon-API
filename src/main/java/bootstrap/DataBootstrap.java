/*
 * package bootstrap;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.ApplicationListener; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.event.ContextRefreshedEvent;
 * 
 * import model.Coupon; import repository.CouponRepository;
 * 
 * @Configuration public class DataBootstrap implements
 * ApplicationListener<ContextRefreshedEvent> { private CouponRepository rep;
 * 
 * @Autowired public DataBootstrap(CouponRepository rep) { this.rep = rep; }
 * 
 * @Override public void onApplicationEvent(ContextRefreshedEvent event) {
 * rep.saveAll(addData());
 * 
 * }
 * 
 * public List<Coupon> addData() { List<Coupon> l = new ArrayList<>(); Coupon
 * coupon = new Coupon(); coupon.setId(1); coupon.setCouponCode("FLAT123");
 * coupon.setDescripition("flat"); coupon.setMin_cart_val(1000);
 * coupon.setMax_cart_val(10000); coupon.setMax_usage(5);
 * coupon.setTotal_used(1); coupon.setCoupon_type(1); coupon.setStatus(1);
 * coupon.setMax_discount_amt(0); coupon.setPercent_val(0);
 * coupon.setFlat_val(100); coupon.setStart_date("2019-01-11");
 * coupon.setEnd_date("2019-04-11"); l.add(coupon);
 * 
 * Coupon coupon1 = new Coupon(); coupon1.setId(2);
 * coupon1.setCouponCode("PERCENT123"); coupon1.setDescripition("percentage");
 * coupon1.setMin_cart_val(1000); coupon1.setMax_cart_val(10000);
 * coupon1.setMax_usage(5); coupon1.setTotal_used(3); coupon1.setCoupon_type(0);
 * coupon1.setStatus(1); coupon1.setMax_discount_amt(100);
 * coupon1.setPercent_val((float) 0.32); coupon1.setFlat_val(0);
 * coupon1.setStart_date("2019-01-11"); coupon1.setEnd_date("2019-08-11");
 * l.add(coupon1);
 * 
 * Coupon coupon2 = new Coupon(); coupon2.setId(3);
 * coupon2.setCouponCode("PERCENT223"); coupon2.setDescripition("percentage");
 * coupon2.setMin_cart_val(1000); coupon2.setMax_cart_val(10000);
 * coupon2.setMax_usage(5); coupon2.setTotal_used(2); coupon2.setCoupon_type(0);
 * coupon2.setStatus(1); coupon2.setMax_discount_amt(200);
 * coupon2.setPercent_val((float) 0.27); coupon2.setFlat_val(0);
 * coupon2.setStart_date("2019-01-11"); coupon2.setEnd_date("2020-04-11");
 * l.add(coupon2);
 * 
 * Coupon coupon3 = new Coupon(); coupon3.setId(4);
 * coupon3.setCouponCode("FLAT"); coupon3.setDescripition("flat");
 * coupon3.setMin_cart_val(1000); coupon3.setMax_cart_val(10000);
 * coupon3.setMax_usage(5); coupon3.setTotal_used(2); coupon3.setCoupon_type(1);
 * coupon3.setStatus(1); coupon3.setMax_discount_amt(0);
 * coupon3.setPercent_val(0); coupon3.setFlat_val(400);
 * coupon3.setStart_date("2019-01-11"); coupon3.setEnd_date("2020-04-11");
 * l.add(coupon3); return l;
 * 
 * }
 * 
 * }
 */