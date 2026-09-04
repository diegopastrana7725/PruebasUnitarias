package co.com.bancolombia.h2db.utilities;

import co.com.bancolombia.h2db.entities.coupon.CouponEntity;
import co.com.bancolombia.h2db.entities.couponUsage.CouponUsageEntity;
import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.CouponUsage;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public Coupon toModel(CouponEntity entity) {

        Coupon coupon = new Coupon();

        coupon.setId(entity.getId());
        coupon.setCode(entity.getCode());
        coupon.setName(entity.getName());
        coupon.setDiscountType(entity.getDiscountType());
        coupon.setDiscountValue(entity.getDiscountValue());
        coupon.setMaxDiscount(entity.getMaxDiscount());
        coupon.setMinimumPurchaseAmount(entity.getMinimumPurchaseAmount());
        coupon.setCategory(entity.getCategory());
        coupon.setActive(entity.getActive());
        coupon.setExpirationDate(entity.getExpirationDate());
        coupon.setMaxUsesPerCustomer(entity.getMaxUsesPerCustomer());

        return coupon;
    }

    public CouponEntity toEntity(Coupon coupon) {

        CouponEntity entity = new CouponEntity();

        entity.setId(coupon.getId());
        entity.setCode(coupon.getCode());
        entity.setName(coupon.getName());
        entity.setDiscountType(coupon.getDiscountType());
        entity.setDiscountValue(coupon.getDiscountValue());
        entity.setMaxDiscount(coupon.getMaxDiscount());
        entity.setMinimumPurchaseAmount(coupon.getMinimumPurchaseAmount());
        entity.setCategory(coupon.getCategory());
        entity.setActive(coupon.getActive());
        entity.setExpirationDate(coupon.getExpirationDate());
        entity.setMaxUsesPerCustomer(coupon.getMaxUsesPerCustomer());

        return entity;
    }

    public CouponUsage toModel(CouponUsageEntity entity) {

        CouponUsage usage = new CouponUsage();

        usage.setId(entity.getId());
        usage.setCouponId(entity.getCouponId());
        usage.setCustomerId(entity.getCustomerId());
        usage.setUsedAt(entity.getUsedAt());

        return usage;
    }

    public CouponUsageEntity toEntity(CouponUsage usage) {

        CouponUsageEntity entity = new CouponUsageEntity();

        entity.setId(usage.getId());
        entity.setCouponId(usage.getCouponId());
        entity.setCustomerId(usage.getCustomerId());
        entity.setUsedAt(usage.getUsedAt());

        return entity;
    }
}