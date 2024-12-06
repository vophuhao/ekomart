package vn.iotstar.service.user;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IProductService {

	Product getById(Long id);

	long count();

	List<Product> findAll();

	<S extends Product> S save(S entity);

	List<Product> findByName(String productName,Long shopId);

	List<Product> findByStatus(int status,Long shopId);

	List<Product> findByDisplay(int display,Long shopId);

	List<Product> getProductsByShopId(Long shopId);

	List<Product> findProductsByShopId(Long id);
}
