package vn.iotstar.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.UserInfoRepository;
import vn.iotstar.service.UserService;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/user/account")
public class AccountController {
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserInfoRepository userRepository;
	
	@GetMapping("")
	public String AccountUser(HttpServletRequest request, Model model) {
		
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
        
        String email = user.getEmail();
        model.addAttribute("email", email);
		
		return "page/account/account";
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
