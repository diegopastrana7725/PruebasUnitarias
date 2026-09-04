package co.com.bancolombia.model.coupon;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplyCouponResponse {
    private boolean applied;
    private String couponCode;
    private BigDecimal purchaseAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String message;
}