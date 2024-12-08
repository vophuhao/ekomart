package vn.iotstar.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.entity.Wishlist;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.OderRepository;
import vn.iotstar.repository.OrderDetailRepository;
import vn.iotstar.repository.UserInfoRepository;
import vn.iotstar.repository.WishlistRepository;
import vn.iotstar.service.IOderService;
import vn.iotstar.service.Imp.OderServiceImpl;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/user/account")
public class AccountController {
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserInfoServiceImp userservice;
	
	@Autowired
    private WishlistRepository wishrepo;
	
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
	private AddressServiceImp addre;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserInfoRepository userRepository;
	
	IOderService orderService = new OderServiceImpl();
	
	@Autowired
	private OderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@GetMapping("")
	public String AccountUser(HttpServletRequest request, Model model,HttpSession session) {
		
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
        
        UserInfo user = userInfoRepository.findByName(jwtUtil.extractUsername(token))
        	.orElseThrow(() -> new UsernameNotFoundException("Please provide a valid name!"));
        String username = user.getName();
        
        model.addAttribute("username", username);
        session.setAttribute("username", username);
        Optional<UserInfo> user1 = userservice.findByName((String)session.getAttribute("username"));
		UserInfo userInfo = user1.get();
        Cart cart = cartService.findByUser(userInfo);
		session.setAttribute("cartCount", cart.getItems().size());
		Optional<Wishlist> wishlist = wishrepo.findByUser(userInfo);
		Wishlist wish = wishlist.get();
		model.addAttribute("wish", wish);
        List<Address> address=addre.findByUser(user);
        List<Category> listcate = cateRepo.findByStatus(1);
        model.addAttribute("cate", listcate);
        session.setAttribute("cate", listcate);

        model.addAttribute("addressUser", address);
        String email = user.getEmail();
        model.addAttribute("email", email);
        List<Orders> list = orderRepository.findByUser(userInfo);
        model.addAttribute("orders", list);
		return "page/account/account";
	}
	
	@GetMapping("/orders/{id}")
	public String getOrderDetails(@PathVariable Long id, Model model) {
	    Orders order = orderRepository.findById(id).orElse(null);
	    List<OrderDetail> list = orderDetailRepository.findByOrders(order);
	    model.addAttribute("order", order);
	    model.addAttribute("listProduct", list);
	    return "page/order-details :: content"; // Trả về một phần HTML (sử dụng Thymeleaf fragment)
	}
	
	@PostMapping("/orders/{id}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
	    Orders order = orderRepository.findById(id).orElse(null);
	    if (order == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
	    }

	    if (order.getStatus() != 0) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot cancel this order");
	    }

	    // Cập nhật trạng thái đơn hàng
	    order.setStatus(4); // 4: Cancel
	    orderRepository.save(order);

	    return ResponseEntity.ok("Order cancelled successfully");
	}

	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassword") String password, 
	                             @RequestParam("confirmPassword") String repassword,
	                             @RequestParam("currentPassword") String cupassword,
	                             HttpServletRequest request,
	                             Model model) {
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
        
        UserInfo user = userInfoRepository.findByName(jwtUtil.extractUsername(token))
        	.orElseThrow(() -> new UsernameNotFoundException("Please provide a valid name!"));
        
        String encodedPassword = passwordEncoder.encode(password);
        
        if (!passwordEncoder.matches(cupassword, user.getPassword())) {
        	model.addAttribute("error", "Wrong current password! Try again.");
	    	return "page/account/account";
        }
		
		// Kiểm tra xem mật khẩu và xác nhận mật khẩu có giống nhau không
	    if (!password.equals(repassword)) {
	    	model.addAttribute("error", "Please enter the password again!");
	    	return "page/account/account";
	    }
	    
        String email = user.getEmail();
	    
	   
        userRepository.updatePassword(email, encodedPassword);

	    return "redirect:/user/account";
	}

}
