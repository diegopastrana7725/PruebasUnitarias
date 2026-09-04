package co.com.bancolombia.model.coupon;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplyCouponRequest {
    private String customerId;
    private String couponCode;
    private BigDecimal purchaseAmount;
    private String category;
}