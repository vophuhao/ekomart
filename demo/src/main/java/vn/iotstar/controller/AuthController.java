package vn.iotstar.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.config.UserInfoService;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.entity.Wishlist;
import vn.iotstar.model.AuthRequest;
import vn.iotstar.model.MailBody;
import vn.iotstar.repository.UserInfoRepository;
import vn.iotstar.repository.WishlistRepository;
import vn.iotstar.service.EmailService;
import vn.iotstar.service.UserService;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

	@Autowired
    private WishlistRepository wishrepo;
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoService userDetailsService;
    
    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Autowired
    private  EmailService emailService;

    @Autowired
	private CartServiceImpl cartService;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userInfo") @Validated UserInfo userInfo, BindingResult result, Model model) {
    	// Validation
        if (result.hasErrors()) {
            return "register";
        }
        // Add user to Database
        if (!userService.registerUser(userInfo)) {
        	model.addAttribute("error", "Email đã tồn tại");
        	return "register";
        }
//        UserInfo user = userInfoRepository.findByEmail(userInfo.getEmail()).orElse(null);
    
          
        
        return "redirect:/register/register-verify-otp?email=" + userInfo.getEmail();
    }
    
    // Trang để người dùng nhập OTP
    @GetMapping("/register/register-verify-otp")
    public String showVerifyOtpPage() {
        return "register-verify-otp";
    }
    
    @GetMapping("/login")
    public String loginUser(@RequestParam(value = "error", required = false) String error, Model model) {
    	if (error != null) {
    		model.addAttribute("message", "Invalid username or password");
    	}
    	model.addAttribute("userModel", new AuthRequest());
    	return "login";
    }
    
    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpServletResponse response, Model model) {
        try {
        	// Tìm người dùng trong cơ sở dữ liệu
            UserInfo user = userInfoRepository.findByName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Kiểm tra xem tài khoản có được kích hoạt không
            if (!user.isEnabled()) {
                model.addAttribute("error", "Your account is disabled.");
                return "login";
            }
        	
            // Tiến hành xác thực người dùng
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails.getUsername());
            
            // Thiết lập token dưới dạng cookie
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true); // Bảo mật cookie, không truy cập được từ JavaScript
            jwtCookie.setPath("/"); // Áp dụng cookie cho toàn bộ ứng dụng
            jwtCookie.setMaxAge(60 * 60 * 10); // Thời hạn 10 giờ
            response.addCookie(jwtCookie);
            
            // Lưu token vào model để truyền sang view nếu cần
            model.addAttribute("token", token);

            // Điều hướng dựa trên vai trò người dùng
            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/home";  // Điều hướng đến trang admin
            } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                return "redirect:/user/home";   // Điều hướng đến trang user
            } else {
                return "redirect:/user/home"; // Điều hướng đến trang vendor
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";  // Quay lại trang đăng nhập nếu lỗi
        }
    }
    
    // login không dùng PostMapping, Bắt buộc dùng (username, pasword) ở view 
    // để Spring Security tự động lưu vào userInfo  
    
    @PostMapping("/register/register-verify-otp")
    public String verifyOtp(@RequestParam("otp") Integer otp, @RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
    	UserInfo user = userInfoRepository.findByEmail(email).orElse(null);

        if (user != null && user.getOtp().equals(otp)) {
        	 Cart cart=new Cart(); 
      		cart.setUser(user); 
      		Wishlist wishlist=new Wishlist(); 
      		wishlist.setUser(user); 
      		cartService.save(cart);
      		wishrepo.save(wishlist);
            user.setEnabled(true);
            user.setOtp(null); // Clear OTP after successful verification
            userInfoRepository.save(user);
            redirectAttributes.addFlashAttribute("successMessage", "Xác thực OTP thành công. Vui lòng đăng nhập!");
            return "redirect:/login";
        } else {
        	model.addAttribute("message", "Invalid OTP!");
            return "register-verify-otp";
        }
    }
    
    @GetMapping("/verify-otp/resend-otp")
    public String ReSendOtp(@RequestParam("email") String email, Model model) {
    	// Send mail
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request: " + otp)
                .subject("OTP for Forgot Password request")
                .build();
        userInfoRepository.updateOtp(email, otp);
        emailService.sendSimpleMessage(mailBody);
        return "register-verify-otp";
    }
    
    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}