
package vn.iotstar.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>{
    Optional<Shop> findById(Long shopId);
}
