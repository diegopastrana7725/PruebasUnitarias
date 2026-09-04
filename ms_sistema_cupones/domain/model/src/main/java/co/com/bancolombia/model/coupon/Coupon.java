package co.com.bancolombia.model.coupon;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Coupon {
    private Long id;
    private String code;
    private String name;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscount;
    private BigDecimal minimumPurchaseAmount;
    private String category;
    private Boolean active;
    private LocalDate expirationDate;
    private Integer maxUsesPerCustomer;
}