package vn.iotstar.service.vendor;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Product;

public interface VendorIProductService {

	Product getById(Long id);

	long count();

	List<Product> findAll();

	<S extends Product> S save(S entity);

	Optional<Product> findByName(String productName);

	Optional<Product> findByStatus(int status);

	Optional<Product> findByDisplay(int display);

	Optional<Product> findByProductId(String productId);

	List<Product> findAllProductsByShopId(Long shopId);

	List<Product> getProductsByShopId(Long shopId);

	List<Product> findProductsByShopId(Long id);

}
