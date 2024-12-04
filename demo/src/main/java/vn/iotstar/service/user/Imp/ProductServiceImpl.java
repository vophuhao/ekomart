package vn.iotstar.service.user.Imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.user.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	ProductRepository productRepository;
	

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public Product getById(Long id) {
		return productRepository.getById(id);
	}
	
	@Override
	public List<Product> getProductsByShopId(Long shopId) {
	    List<Product> products = productRepository.findAll(); // Fetch all products
	    return products.stream()
	                   .filter(pro -> pro.getShop() != null && pro.getShop().getId().equals(shopId))
	                   .collect(Collectors.toList()); // Collect results into a List
	}

	@Override
	public List<Product> findByDisplay(int display,Long shopId) {
		
		 Optional<Product> products =  productRepository.findByDisplay(display);
		 return products.stream()
                 .filter(pro -> pro.getShop() != null && pro.getShop().getId().equals(shopId))
                 .collect(Collectors.toList()); // Collect results into a List
	}

	@Override
	public List<Product> findByStatus(int status,Long shopId) {
		 List<Product> products = productRepository.findByStatus(status);
		 return products.stream()
                 .filter(pro -> pro.getShop() != null && pro.getShop().getId().equals(shopId))
                 .collect(Collectors.toList()); // Collect results into a List
	}

	@Override
	public List<Product> findByName(String productName,Long shopId) {
		 Optional<Product> products = productRepository.findByName(productName);
		 return products.stream()
                 .filter(pro -> pro.getShop() != null && pro.getShop().getId().equals(shopId))
                 .collect(Collectors.toList()); // Collect results into a List
	}

	@Override
	public List<Product> findProductsByShopId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
