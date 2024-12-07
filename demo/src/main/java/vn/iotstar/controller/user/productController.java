package vn.iotstar.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Review;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.UserService;
import vn.iotstar.service.user.ProductService;
import vn.iotstar.service.user.ReviewService;
import vn.iotstar.util.JwtUtil;
import vn.iotstar.service.IStorageService;

@Controller
@RequestMapping("/user/home/product-detail")
public class productController {

	@Autowired
    private ProductService productService;
	
	@Autowired
    private ProductRepository productRepo;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ShopRepository shopRepo;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;

    @Autowired
	IStorageService storageService;
	
	@GetMapping("/{id}")
    public String viewProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.getProductById(id);
        List<Review> reviews = productService.getReviewsByProductId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Review review : reviews) {
            review.setFormattedDateString(review.getDate().format(formatter));
        }
        Shop shop = product.getShop();
        Category cate = product.getCategory();
        List<Product> relatePro = productRepo.findByCategory(cate);
        model.addAttribute("relatePro", relatePro);
        model.addAttribute("shop", shop);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewsCount", reviews.size());
        model.addAttribute("newReview", new Review()); // Để gửi form đánh giá
        return "page/product-detail";
    }
	
	@PostMapping("/{id}/addReview")
    public String addReview(@PathVariable("id") long productId, @ModelAttribute("newReview") Review review, Model model, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        review.setProduct(product);
        String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        UserInfo user = userService.findByUsername(username);
        review.setUser(user);
        productService.addReview(review);
        return "redirect:/user/home/product-detail/" + productId;
    }
	
	@GetMapping("/{id}/reviews")
	@ResponseBody
	public List<Review> getReviewsByRating(@PathVariable long id, @RequestParam int rating) {
		List<Review> list = reviewService.findReviewsByRating(id, rating);
	    return list;
	}	
	
	@GetMapping("")
	public String productDetail() {
		return "page/product-detail";
	}
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
}
