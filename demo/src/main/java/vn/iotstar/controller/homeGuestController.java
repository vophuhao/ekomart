package vn.iotstar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.config.UserInfoService;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.entity.Wishlist;
import vn.iotstar.entity.WishlistItem;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.WishlistItemRepository;
import vn.iotstar.repository.WishlistRepository;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.UserProductServiceImpl;
import vn.iotstar.util.JwtUtil;

import javax.swing.text.html.Option;

@Controller

@RequestMapping("/home")
public class homeGuestController {

	@Autowired
	private CartServiceImpl cartService;
	@Autowired
	private CategoryRepository cateRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserProductServiceImpl productService;
    @Autowired
    private AdminShopService adminShopService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductRepository prorepo;
    @Autowired
    private WishlistRepository wishrepo;
    @Autowired
    private WishlistItemRepository wishItemrepo;

	@GetMapping("")
	public String homeView(HttpServletRequest request, Model model,HttpSession session) {
		
        List<Product> top20New = productService.getLatestProducts();
        model.addAttribute("top20pro", top20New);
        
        List<Product> top20Sell = productService.getTop20BestSellers();
        model.addAttribute("top20Sell", top20Sell);
        
//        List<Object[]> top20Rate = productService.getTop20ReviewedProducts();
//        model.addAttribute("top20Rate", top20Rate);
        List<Category> listcate = cateRepo.findByStatus(1);
        model.addAttribute("cate", listcate);
        session.setAttribute("cate", listcate);
        
      
		
		List<Product> listPage = prorepo.findTop18Page(PageRequest.of(0, 18));
		model.addAttribute("listPage", listPage);
		
	
		
       
		return "page/home-content-guest";
	}
		
	@GetMapping("/product")
	public ModelAndView getProduct(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(defaultValue = "16") int size,
	        @RequestParam("value") String value) {
		ModelAndView modelAndView = new ModelAndView("page/shop-grid-sidebar-guest");
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage;
		if (value == null)
		{
			productPage = productService.findAllByDisplay(1,pageable);
		}
		else
		{
			productPage = prorepo.findByNameOrCategoryNameContaining(value,pageable);
		}
		modelAndView.addObject("value", value);
	    modelAndView.addObject("product", productPage);
	    modelAndView.addObject("currentPage", productPage.getNumber());
	    modelAndView.addObject("totalPages", productPage.getTotalPages());
	    return modelAndView;
	}
	
	
}
