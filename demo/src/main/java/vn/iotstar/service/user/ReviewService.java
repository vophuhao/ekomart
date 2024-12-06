package vn.iotstar.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Review;
import vn.iotstar.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findReviewsByRating(long productId, int rating) {
    	// Chỉ trả về các trường cần thiết
    	return reviewRepository.findByProductIdAndRating(productId, rating)
                .stream()
                .map(review -> {
                    Review simplifiedReview = new Review();
                    simplifiedReview.setId(review.getId());
                    simplifiedReview.setComment(review.getComment());
                    simplifiedReview.setRating(review.getRating());
                    simplifiedReview.setDate(review.getDate());
                    simplifiedReview.setUser(review.getUser());
                    return simplifiedReview;
                })
                .collect(Collectors.toList());
    }
}

