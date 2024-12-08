
package vn.iotstar.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>{
    Optional<Shop> findById(Long shopId);
    List<Shop> findByDisplay(int display);
    List<Shop> findAll();
    Optional<Shop> findByUserId(int userId);
    
    List<Shop> findByStatus(int status);
    
    Optional<Shop>  findByShopId(String shopId);
    
    @Query("SELECT s FROM Shop s  WHERE (s.name LIKE %:keyword% ) AND s.status = 1")
	 List<Shop> findByNameContaining(@Param("keyword") String keyword);
}
