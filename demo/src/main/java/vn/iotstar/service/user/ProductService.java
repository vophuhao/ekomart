package vn.iotstar.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Review;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ReviewRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByProductId(long id) {
        return reviewRepository.findByProductId(id);
    }
}

