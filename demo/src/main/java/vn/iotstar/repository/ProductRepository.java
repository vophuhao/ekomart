
package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	 Optional<Product> findByProductId(String productId);	
	 
	 Optional<Product> findByDisplay(int display);
	 
	 List<Product> findByStatus(int status);
	 
	 Optional<Product> findByName(String productName);
	 
	 List<Product> findByShop(Shop shop);

	 
	 @Query("SELECT p FROM Product p ORDER BY p.date DESC")
	 List<Product> findTop20ByOrderByDateDesc(Pageable pageable);
	 
}

