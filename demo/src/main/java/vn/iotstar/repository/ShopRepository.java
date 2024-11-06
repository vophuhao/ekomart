<<<<<<< HEAD:demo/src/main/java/vn/iotstar/repository/vendor/VendorShopRepository.java
package vn.iotstar.repository.vendor;
=======
package vn.iotstar.repository;
>>>>>>> mun:demo/src/main/java/vn/iotstar/repository/ShopRepository.java

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>{
    Optional<Shop> findById(Long shopId);
}
