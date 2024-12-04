package vn.iotstar.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Review;
import vn.iotstar.service.user.ProductService;
import vn.iotstar.service.user.ReviewService;

@Controller
@RequestMapping("/home/product-detail")
public class productController {

	@Autowired
    private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{id}")
    public String viewProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.getProductById(id);
        List<Review> reviews = productService.getReviewsByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new Review()); // Để gửi form đánh giá
        return "page/product-detail";
    }
	
	@PostMapping("/{id}/addReview")
    public String addReview(@PathVariable("id") long productId, @ModelAttribute("newReview") Review review, Model model) {
        Product product = productService.getProductById(productId);
        review.setProduct(product);
        productService.addReview(review);
        return "redirect:/home/product-detail/" + productId;
    }
	
	@GetMapping("/{id}/reviews")
	@ResponseBody
	public List<Review> getReviewsByRating(@PathVariable long id, @RequestParam int rating) {
	    return reviewService.findReviewsByRating(id, rating);
	}	
	
	@GetMapping("")
	public String productDetail() {
		return "page/product-detail";
	}
	
}
