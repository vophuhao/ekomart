package vn.iotstar.controller.user;

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

@RequestMapping("/user")
public class homeController {

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
        Optional<UserInfo> users=userService.findByName(username);
        UserInfo userss =new UserInfo();
        if (users.isPresent()) {
            userss = users.get();
            // Xử lý logic với user
        } 
        session.setAttribute("username", username);
        session.setAttribute("role", userss.getRoles());
        List<Product> top20New = productService.getLatestProducts();
        model.addAttribute("top20pro", top20New);
        
        List<Product> top20Sell = productService.getTop20BestSellers();
        model.addAttribute("top20Sell", top20Sell);
        
//        List<Object[]> top20Rate = productService.getTop20ReviewedProducts();
//        model.addAttribute("top20Rate", top20Rate);
        List<Category> listcate = cateRepo.findByStatus(1);
        model.addAttribute("cate", listcate);
        session.setAttribute("cate", listcate);
        
        Optional<UserInfo> user = userService.findByName((String)session.getAttribute("username"));
		UserInfo userInfo = user.get();
		Cart cart = cartService.findByUser(userInfo);
		session.setAttribute("cartCount", cart.getItems().size());
        
		
		List<Product> listPage = prorepo.findTop18Page(PageRequest.of(0, 18));
		model.addAttribute("listPage", listPage);
		
		Optional<Wishlist> wish=wishrepo.findByUser(userInfo);
		Wishlist wishlist=wish.get();
		session.setAttribute("wishlistCount", wishlist.getItems().size());
		
        model.addAttribute("Name", username);
		return "page/home-content";
	}
	
	@GetMapping("/wishlist")
	public String getWishlist(HttpServletRequest request, Model model, HttpSession session) 
	{
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
		UserInfo userInfo = user.get();
		   session.setAttribute("username", username);
	   
		Cart cart = cartService.findByUser(userInfo);
		session.setAttribute("cartCount", cart.getItems().size());
		Optional<Wishlist> wishlist = wishrepo.findByUser(userInfo);
		Wishlist wish = wishlist.get();
		session.setAttribute("wishlistCount", wish.getItems().size());
		model.addAttribute("wish", wish);
		return "page/wishlist";
	}
	
	@PostMapping("/wishlist/add-item")
	public String addItemToWishlist(@RequestParam("item") String id,HttpServletRequest request, HttpSession session) {
		System.out.print(id);
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
        
        String username = jwtUtil.extractUsername(token);
		Optional<UserInfo> user = userService.findByName(username);
		UserInfo userInfo = user.get();
		Optional<Wishlist> wishl=wishrepo.findByUser(userInfo);
		Wishlist wish=wishl.get();
		Optional<Product> pro=prorepo.findById(Long.parseLong(id));
		Product product=pro.get();
		WishlistItem wishItem =new WishlistItem();
		wishItem.setProduct(product);
		wishItem.setWishlist(wish);
		wishItemrepo.save(wishItem);
		
	}
        return "redirect:/user/wishlist";
	}
	
	@PostMapping("/wishlist")
	public String removeProductFromWishlist(@RequestParam("item") String wishId) {
	    // Giả sử bạn có một phương thức trong service để xóa sản phẩm khỏi giỏ
		wishItemrepo.deleteById(Long.parseLong(wishId));
	    return "redirect:/user/wishlist";
	}
	
	@GetMapping("/product")
	public ModelAndView getProduct(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(defaultValue = "16") int size,
	        @RequestParam("value") String value,HttpServletRequest request,HttpSession session,Model model) {
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
        List<Category> listcate = cateRepo.findByStatus(1);
        model.addAttribute("cate", listcate);
        session.setAttribute("cate", listcate);
        String username = jwtUtil.extractUsername(token);
        Optional<UserInfo> users=userService.findByName(username);
        UserInfo userss =new UserInfo();
        if (users.isPresent()) {
            userss = users.get();
            // Xử lý logic với user
        } 
        session.setAttribute("username", username);
        session.setAttribute("role", userss.getRoles());
		ModelAndView modelAndView = new ModelAndView("page/shop-grid-sidebar");
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
	
//	@GetMapping("/product")
//	public String getProduct( HttpServletRequest request, Model model,HttpSession session) {
//		
//        List<Product> product = productService.findAllByDisplay(1);
//        model.addAttribute("product", product);
//        
//		return "page/shop-grid-sidebar";
//	}
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
