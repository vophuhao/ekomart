package vn.iotstar.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.config.UserInfoService;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.user.Imp.UserProductServiceImpl;
import vn.iotstar.util.JwtUtil;

import javax.swing.text.html.Option;

@Controller

@RequestMapping("/user")
public class homeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserProductServiceImpl productService;
    @Autowired
    private AdminShopService adminShopService;
    @Autowired
    private IUserService userService;

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
        
        List<Product> top20Sell = productService.getTop20BestSellers();
        model.addAttribute("top20Sell", top20Sell);
        
//        List<Object[]> top20Rate = productService.getTop20ReviewedProducts();
//        model.addAttribute("top20Rate", top20Rate);
        
        model.addAttribute("Name", username);
		return "page/home-content";
	}
	
	@GetMapping("/product")
	public String getProduct( HttpServletRequest request, Model model,HttpSession session) {
		
        List<Product> product = productService.findAllByDisplay(0);
        model.addAttribute("product", product);
        
		return "page/shop-grid-sidebar";
	}
	@GetMapping("/my-shop")
    public String checkShop(HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
        Optional<UserInfo> user = userService.findByName(username);
        System.out.println(username);
        System.out.println(user);
        if(user.isPresent()) {
            Optional<Shop> shop = adminShopService.findByUserId(user.get().getId());
            if (shop.isPresent()) {
                // If shop exists for this user, redirect to vendor/home
                return "redirect:/vendor/home";
            } else {
                // If no shop exists, redirect to vendor/register
                return "redirect:/vendor/register";
            }
        }


        return null;
    }}
