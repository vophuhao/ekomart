package vn.iotstar.service.admin.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.admin.AdminIProductService;


@Service
public class AdminProductServiceImp implements AdminIProductService {

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
	public Optional<Product> findByProductId(String productId) {
		return productRepository.findByProductId(productId);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Optional<Product> findByDisplay(int display) {
		return productRepository.findByDisplay(display);
	}

	@Override
	public List<Product> findByStatus(int status) {
		return productRepository.findByStatus(status);
	}

	@Override
	public Optional<Product> findByName(String productName) {
		return productRepository.findByName(productName);
	}

	@Override
	public List<Product> findByShop(Shop shop) {
	
		return productRepository.findByShop(shop);
	}
	
	
	
	
	
}
