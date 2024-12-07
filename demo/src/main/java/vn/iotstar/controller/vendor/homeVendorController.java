package vn.iotstar.controller.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.util.JwtUtil;

@Controller

@RequestMapping("/vendor")
public class homeVendorController {
	
	@Autowired
    private JwtUtil jwtUtil;
	 @Autowired
	 private IUserService userService;
	 
	 @Autowired
	 private ShopRepository shoprepo;
	@GetMapping("/home")
	public String homeVendor(HttpServletRequest request, Model model,HttpSession session) {
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
        Optional<UserInfo> users=userService.findByName(username);
        UserInfo userss =new UserInfo();
        if (users.isPresent()) {
            userss = users.get();
            // Xử lý logic với user
        } 
       Optional<Shop> shop=shoprepo.findByUserId(userss.getId());
       Shop shopp=new Shop();
       if (shop.isPresent()) {
           shopp = shop.get();
           // Xử lý logic với user
       } 
       session.setAttribute("shop", shopp);
        session.setAttribute("username", username);
		return "vendor/home";
	}
}
