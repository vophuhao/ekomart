package vn.iotstar.service.admin;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

public interface AdminIProductService {

	Product getById(Long id);

	long count();

	List<Product> findAll();

	<S extends Product> S save(S entity);

	Optional<Product> findByName(String productName);

	List<Product> findByStatus(int status);

	Optional<Product> findByDisplay(int display);

	Optional<Product> findByProductId(String productId);

	Optional<Product> findById(Long id);
	 List<Product> findByShop(Shop shop);

}
