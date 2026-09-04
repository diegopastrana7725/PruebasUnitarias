package co.com.bancolombia.h2db.entities.couponUsage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUsageDataRepository extends JpaRepository<CouponUsageEntity, Long> {
    long countByCouponIdAndCustomerId(Long couponId, String customerId);
}
