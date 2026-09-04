package co.com.bancolombia.usecase.getCoupons;

import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.exceptions.BusinessException;
import co.com.bancolombia.model.coupon.gateways.CouponRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetCouponsUseCase {

    private final CouponRepository couponRepository;

    public List<Coupon> execute() {
        List<Coupon> coupons = couponRepository.findAll();
        if (coupons.isEmpty()) {
            throw new BusinessException("Coupons not found");
        }
        return coupons;
    }
}
