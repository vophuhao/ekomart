package vn.iotstar.service.user;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IUserProductService {
	public List<Product> getLatestProducts();
}
