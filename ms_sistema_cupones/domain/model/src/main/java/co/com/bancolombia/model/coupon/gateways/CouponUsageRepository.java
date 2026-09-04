package co.com.bancolombia.model.coupon.gateways;

import co.com.bancolombia.model.coupon.CouponUsage;

public interface CouponUsageRepository {

    int countByCouponAndCustomer(
            Long couponId,
            String customerId
    );

    void save(CouponUsage couponUsage);

}
