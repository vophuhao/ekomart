package vn.iotstar.repository.vendor;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

@Repository
public interface VendorProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByProductId(String productId);
	 
	 Optional<Product> findByDisplay(int display);
	 
	 Optional<Product> findByStatus(int status);
	 
	 Optional<Product> findByName(String productName);
	 
	 List<Product> findByShop(Shop shop);
	 
}
