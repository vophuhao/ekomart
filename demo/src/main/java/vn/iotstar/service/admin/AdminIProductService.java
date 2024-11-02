package vn.iotstar.service.admin;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Product;

public interface AdminIProductService {

	Product getById(Long id);

	long count();

	List<Product> findAll();

	<S extends Product> S save(S entity);

	Optional<Product> findByName(String productName);

	Optional<Product> findByStatus(int status);

	Optional<Product> findByDisplay(int display);

	Optional<Product> findByProductId(String productId);

}
