package co.com.bancolombia.h2db.entities.couponUsage;

import co.com.bancolombia.h2db.utilities.CouponMapper;
import co.com.bancolombia.model.coupon.CouponUsage;
import co.com.bancolombia.model.coupon.gateways.CouponUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponUsageRepositoryAdapter implements CouponUsageRepository {

    private final CouponUsageDataRepository usageRepository;
    private final CouponMapper mapper;

    @Override
    public int countByCouponAndCustomer(Long couponId, String customerId) {
        return (int) usageRepository.countByCouponIdAndCustomerId(couponId, customerId);
    }

    @Override
    public void save(CouponUsage couponUsage) {
        usageRepository.save(mapper.toEntity(couponUsage));
    }
}
