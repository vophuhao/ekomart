package vn.iotstar.service.vendor;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

public interface VendorIProductService {

	Optional<Product> findById(Long id);

	Product getById(Long id);

	long count();

	List<Product> findAll();

	<S extends Product> S save(S entity);

	List<Product> findByName(String productName,Long shopId);

	List<Product> findByStatus(int status,Long shopId);

	List<Product> findByDisplay(int display,Long shopId);

	List<Product> getProductsByShopId(Long shopId);

	List<Product> findByShop(Shop shop);
}
