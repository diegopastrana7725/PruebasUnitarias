package co.com.bancolombia.h2db.entities.coupon;

import co.com.bancolombia.h2db.utilities.CouponMapper;
import co.com.bancolombia.model.coupon.Coupon;
import co.com.bancolombia.model.coupon.gateways.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryAdapter
        implements CouponRepository {

    private final CouponDataRepository repository;
    private final CouponMapper mapper;

    @Override
    public Coupon save(Coupon coupon) {

        return mapper.toModel(
                repository.save(
                        mapper.toEntity(coupon)
                )
        );
    }

    @Override
    public Optional<Coupon> findById(Long id) {

        return repository.findById(id)
                .map(mapper::toModel);
    }

    @Override
    public Optional<Coupon> findByCode(String code) {

        return repository.findByCode(code)
                .map(mapper::toModel);
    }

    @Override
    public List<Coupon> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}