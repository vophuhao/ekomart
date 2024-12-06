package vn.iotstar.service.user;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IUserProductService {
	public List<Product> getLatestProducts();
	
	public List<Product> getTop20BestSellers();
	
	public List<Product> findAllByDisplay(int display);
	
	//public List<Object[]> getTop20ReviewedProducts();
}
