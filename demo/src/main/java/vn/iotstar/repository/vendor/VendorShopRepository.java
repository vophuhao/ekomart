package vn.iotstar.repository.vendor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Shop;

public interface VendorShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findById(Long shopId);
}
