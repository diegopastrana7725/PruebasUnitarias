package co.com.bancolombia.usecase.getCoupons;


import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.exceptions.BusinessException;
import co.com.bancolombia.model.coupon.gateways.CouponRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCouponByIdUseCase {

    private final CouponRepository couponRepository;

    public Coupon execute(Long id) {
        return couponRepository.findById(id)
            .orElseThrow(() ->
                new BusinessException(
                    "Coupon not found"
                ));
    }
}
