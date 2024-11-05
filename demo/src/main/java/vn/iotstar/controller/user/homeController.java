package vn.iotstar.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.config.UserInfoService;
import vn.iotstar.service.UserService;
import vn.iotstar.util.JwtUtil;

@Controller

@RequestMapping("/user")
public class homeController {

    @Autowired
    private JwtUtil jwtUtil;
	
	@GetMapping("/home")
	public String homeView(HttpServletRequest request, Model model) {
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
		
        model.addAttribute("Name", username);
		return "page/home";
	}
}
