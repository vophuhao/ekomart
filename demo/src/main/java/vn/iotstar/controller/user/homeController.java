package vn.iotstar.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Product;
import vn.iotstar.service.user.Imp.UserProductServiceImpl;
import vn.iotstar.util.JwtUtil;

@Controller

@RequestMapping("/user")
public class homeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserProductServiceImpl productService;
	
	@GetMapping("/home")
	public String homeView(HttpServletRequest request, Model model,HttpSession session) {
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
        session.setAttribute("username", username);
        
        List<Product> top20New = productService.getLatestProducts();
        model.addAttribute("top20pro", top20New);
        
        model.addAttribute("Name", username);
		return "page/home-content";
	}
}
