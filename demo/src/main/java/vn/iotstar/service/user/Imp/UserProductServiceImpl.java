package vn.iotstar.service.user.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.user.IUserProductService;

@Service
public class UserProductServiceImpl implements IUserProductService{
	@Autowired
    private ProductRepository productRepository;

    public List<Product> getLatestProducts() {
    	Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.desc("date")));
        return productRepository.findTop20ByOrderByDateDesc(pageable);
    }
    public List<Product> findByStatus(int status) {
        // Lấy tất cả sản phẩm với giá trị display
        return productRepository.findByStatus(status);
    }
}
