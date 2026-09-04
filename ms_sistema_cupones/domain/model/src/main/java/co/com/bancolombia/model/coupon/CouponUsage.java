package co.com.bancolombia.model.coupon;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CouponUsage {
    private Long id;
    private Long couponId;
    private String customerId;
    private LocalDateTime usedAt;
}
