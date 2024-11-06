<<<<<<< HEAD:demo/src/main/java/vn/iotstar/repository/vendor/VendorProductRepository.java
package vn.iotstar.repository.vendor;


import java.util.List;
=======
package vn.iotstar.repository;

>>>>>>> mun:demo/src/main/java/vn/iotstar/repository/ProductRepository.java
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	 Optional<Product> findByProductId(String productId);	
	 
	 Optional<Product> findByDisplay(int display);
	 
	 Optional<Product> findByStatus(int status);
	 
	 Optional<Product> findByName(String productName);
}
