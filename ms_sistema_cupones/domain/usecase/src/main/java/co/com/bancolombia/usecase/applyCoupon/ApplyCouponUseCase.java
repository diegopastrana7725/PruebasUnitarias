package co.com.bancolombia.usecase.applyCoupon;

import co.com.bancolombia.model.coupon.ApplyCouponRequest;
import co.com.bancolombia.model.coupon.ApplyCouponResponse;
import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.CouponUsage;
import co.com.bancolombia.model.coupon.DiscountType;
import co.com.bancolombia.model.coupon.exceptions.BusinessException;
import co.com.bancolombia.model.coupon.gateways.CouponRepository;
import co.com.bancolombia.model.coupon.gateways.CouponUsageRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ApplyCouponUseCase {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    public ApplyCouponResponse execute(ApplyCouponRequest request) {
        try {
            Coupon coupon = couponRepository
                .findByCode(request.getCouponCode())
                .orElseThrow(() ->
                    new BusinessException("Coupon not found")
                );

            validateCouponIsActive(coupon);
            validateExpiration(coupon);
            validateMinimumPurchase(coupon, request);
            validateCategory(coupon, request);
            validateUsageLimit(coupon, request);

            BigDecimal discount = calculateDiscount(coupon, request.getPurchaseAmount());

            BigDecimal finalAmount = request.getPurchaseAmount().subtract(discount);

            registerCouponUsage(coupon, request.getCustomerId());

            return new ApplyCouponResponse(
                true,
                coupon.getCode(),
                request.getPurchaseAmount(),
                discount,
                finalAmount,
                "Coupon applied successfully"
            );

        } catch (BusinessException ex) {
            return new ApplyCouponResponse(
                false,
                request.getCouponCode(),
                request.getPurchaseAmount(),
                BigDecimal.ZERO,
                request.getPurchaseAmount(),
                ex.getMessage()
            );
        }
    }

    private void validateCouponIsActive(Coupon coupon) {
        if (!coupon.getActive()) {
            throw new BusinessException("Coupon is inactive");
        }
    }

    private void validateExpiration(Coupon coupon) {
        if (coupon.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BusinessException("Coupon has expired");
        }
    }

    private void validateMinimumPurchase(Coupon coupon, ApplyCouponRequest request) {
        if (request.getPurchaseAmount().compareTo(coupon.getMinimumPurchaseAmount()) < 0) {
            throw new BusinessException("Minimum purchase amount not reached");
        }
    }

    private void validateCategory(Coupon coupon, ApplyCouponRequest request) {
        if (!coupon.getCategory().equalsIgnoreCase(request.getCategory())) {
            throw new BusinessException("Coupon is not valid for this category");
        }
    }

    private void validateUsageLimit(Coupon coupon, ApplyCouponRequest request) {
        long usages = couponUsageRepository.countByCouponAndCustomer(
            coupon.getId(),
            request.getCustomerId()
        );
        if (usages >= coupon.getMaxUsesPerCustomer()) {
            throw new BusinessException("Coupon usage limit exceeded");
        }
    }

    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal purchaseAmount) {

        if (coupon.getDiscountType() == DiscountType.FIXED) {
            return coupon.getDiscountValue();
        }

        BigDecimal discount =
            purchaseAmount
            .multiply(coupon.getDiscountValue())
            .divide(
                BigDecimal.valueOf(100),
                2,
                RoundingMode.HALF_UP
            );

        if (discount.compareTo(coupon.getMaxDiscount()) > 0) {
            return coupon.getMaxDiscount();
        }

        return discount;
    }

    private void registerCouponUsage(Coupon coupon, String customerId) {

        CouponUsage usage = new CouponUsage();

        usage.setCouponId(coupon.getId());
        usage.setCustomerId(customerId);
        usage.setUsedAt(LocalDateTime.now());

        couponUsageRepository.save(usage);
    }
}
