package vn.iotstar.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iotstar.entity.Product;

public interface IUserProductService {
	public List<Product> getLatestProducts();
	
	public List<Product> getTop20BestSellers();
	
	public Page<Product> findAllByDisplay(int display, Pageable pageable);
	
	public Page<Product> findByDisplayContaining(int display, Pageable pageable);
	
	//public List<Object[]> getTop20ReviewedProducts();
}
