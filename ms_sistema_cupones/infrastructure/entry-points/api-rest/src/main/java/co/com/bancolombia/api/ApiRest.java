package co.com.bancolombia.api;

import co.com.bancolombia.model.coupon.ApplyCouponRequest;
import co.com.bancolombia.model.coupon.ApplyCouponResponse;
import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.exceptions.BusinessException;
import co.com.bancolombia.usecase.applyCoupon.ApplyCouponUseCase;
import co.com.bancolombia.usecase.getCoupons.GetCouponByIdUseCase;
import co.com.bancolombia.usecase.getCoupons.GetCouponsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ApiRest {

    private final GetCouponsUseCase getCouponsUseCase;
    private final GetCouponByIdUseCase getCouponsByIdUseCase;
    private final ApplyCouponUseCase applyCouponUseCase;

    @PostMapping("/coupons/apply")
    public ApplyCouponResponse applyCoupon(
            @RequestBody ApplyCouponRequest request) {
        return applyCouponUseCase.execute(request);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getCoupons() {
        try {
            List<Coupon> coupons = getCouponsUseCase.execute();
            if (coupons.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(coupons);
        }
        catch (BusinessException e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCouponById(
            @PathVariable("id") Long id) {
        try {
            Coupon coupon = getCouponsByIdUseCase.execute(id);
            return ResponseEntity.ok(coupon);
        } catch (BusinessException e) {
            return ResponseEntity.noContent().build();
        }
    }

}