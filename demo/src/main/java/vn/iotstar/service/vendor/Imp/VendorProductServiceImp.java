package vn.iotstar.service.vendor.Imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.vendor.VendorProductRepository;
import vn.iotstar.repository.vendor.VendorShopRepository;
import vn.iotstar.service.vendor.VendorIProductService;

@Service
public class VendorProductServiceImp implements VendorIProductService {

	@Autowired
	VendorProductRepository productRepository;
	
	@Autowired
	private VendorShopRepository shopRepository;

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
	public Optional<Product> findByDisplay(int display) {
		return productRepository.findByDisplay(display);
	}

	@Override
	public Optional<Product> findByStatus(int status) {
		return productRepository.findByStatus(status);
	}

	@Override
	public Optional<Product> findByName(String productName) {
		return productRepository.findByName(productName);
	}

	@Override
	public List<Product> findAllProductsByShopId(Long shopId) {
	        Shop shop = shopRepository.findByShopId(shopId);
	        return productRepository.findByShop(shop);
	}



	
}
