package vn.iotstar.controller.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.entity.Wishlist;
import vn.iotstar.model.OderRequest;
import vn.iotstar.model.productPayment;
import vn.iotstar.model.productPayment.SelectedProduct;
import vn.iotstar.repository.WishlistRepository;
import vn.iotstar.service.IOderService;
import vn.iotstar.service.Imp.OderDetailServiceImpl;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.ILocationService;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.ProductServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/user")
public class cartController {
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
    private WishlistRepository wishrepo;

	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserInfoServiceImp userservice;

	@Autowired
	private AdminShopService shopservice;

	@Autowired
	private AddressServiceImp addre;

	@Autowired
	private ILocationService location;

	@Autowired
	private IOderService oderservice;

	@Autowired
	private OderDetailServiceImpl oderdetailservice;

	@Autowired
	private ProductServiceImpl productservice;

	@GetMapping("/cart")
	public String showCart(HttpServletRequest request, Model model, HttpSession session) {
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
		Optional<UserInfo> user = userservice.findByName(username);
		 session.setAttribute("username", username);
		UserInfo userInfo = user.get();
		Cart cart = cartService.findByUser(userInfo);
		session.setAttribute("cartCount", cart.getItems().size());
		model.addAttribute("cart", cart);
		Optional<Wishlist> wishlist = wishrepo.findByUser(userInfo);
		Wishlist wish = wishlist.get();
		session.setAttribute("wishlistCount", wish.getItems().size());
		return "page/cart";
	}

	@PostMapping("/add-item")
	public String addItemToCart(@Valid CartItem cartItem,HttpServletRequest request, BindingResult result, HttpSession session) {
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
		Optional<UserInfo> user = userservice.findByName(username);
		UserInfo userInfo = user.get();
		if (result.hasErrors())
			return "redirect:/user/cart";
		cartService.addItemToCart(userInfo, cartItem);
		return "redirect:/user/cart";
	}

	@PostMapping("/cart")
	public String removeProductFromCart(@RequestParam("item") String cartId) {
	    // Giả sử bạn có một phương thức trong service để xóa sản phẩm khỏi giỏ
		cartService.removeItemFromCart(Long.parseLong(cartId));
	    return "redirect:/user/cart";
	}
	
	@PostMapping("/cart/payment")
	public String paymentCart(@ModelAttribute("productPayment") productPayment ProductPayment, Model model,
			RedirectAttributes redirectAttributes, HttpSession session) {
		Optional<UserInfo> user = userservice.findByName((String)session.getAttribute("username"));
		UserInfo userInfo = user.get();
		List<Address> addressUser = addre.findByUser(userInfo);
//		
//		List<AddresModel> address= new ArrayList<AddresModel>();

//		BeanUtils.copyProperties(addressUser, address);
//		model.addAttribute("addressUser",addressUser);

//		List<AddressDTO> address=addre.findAddressesByUserId(1L);
		model.addAttribute("addressUser", addressUser);
		model.addAttribute("productPayment", new productPayment());

		if (ProductPayment == null) {
			// Kiểm tra nếu đối tượng ProductPayment là null
			System.out.println("ProductPayment is null!");
			return "errorPage"; // Hoặc xử lý lỗi thích hợp
		} else {
			// Lấy danh sách các sản phẩm từ ProductPayment
			List<SelectedProduct> selectedProducts = ProductPayment.getSelectedProduct();

			if (selectedProducts == null) {
				// Nếu danh sách null, in thông báo và chuyển đến trang lỗi
				System.out.println("selectedProducts is null!");
				return "errorPage";
			} else {
				// Lọc các sản phẩm có productSelected = true
				selectedProducts = selectedProducts.stream().filter(SelectedProduct::getProductSelected) // Điều kiện
																											// lọc:
																											// productSelected
																											// = true
						.collect(Collectors.toList());

				// Kiểm tra nếu danh sách sau khi lọc rỗng
				if (selectedProducts.isEmpty()) {
					System.out.println("No selected products found!");
					return "errorPage"; // Hoặc trả về trang thông báo khác
				}

				// Thêm danh sách đã lọc vào model để hiển thị trên view
				model.addAttribute("selectedProducts", selectedProducts);

				// Chuyển hướng đến trang thanh toán
				return "page/cart-payment";
			}
		}
	}

	@PostMapping("/cart/payment/save")
	public String processPayment(@RequestBody OderRequest orderRequest, HttpServletRequest request, HttpSession session) {
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
		Optional<UserInfo> user = userservice.findByName(username);
		UserInfo userInfo = user.get();
		List<String> productIds = orderRequest.getProductIds();
		List<String> quantities = orderRequest.getQuantities();
		Optional<Address> address = addre.findByUserAndDefaults(userInfo, 1);
		Address addr = address.get();

		List<Product> productList = new ArrayList<>();
		for (String productId : productIds) {
			// Tìm sản phẩm theo productId
			Product product = productservice.getById(Long.parseLong(productId));

			// Nếu sản phẩm tồn tại, thêm vào danh sách productList
			if (product != null) {
				// Tìm chỉ số của sản phẩm trong productIds để lấy số lượng tương ứng

				productList.add(product);
			}
		}

		Set<Long> shopIds = new HashSet<>();

		// Lọc các shopId duy nhất
		for (Product product : productList) {
			if (product.getShop() != null) {
				shopIds.add(product.getShop().getId());
			}
		}

		// Tạo danh sách các Shop từ các shopId duy nhất
		List<Shop> shopList = new ArrayList<>();
		for (Long shopId : shopIds) {
			Optional<Shop> shop = shopservice.findById(shopId);
			if (shop != null) {

				shop.ifPresent(shopList::add);
			}
		}
		for (Shop shop : shopList) {
			Orders oders = new Orders();
			String odersId = generateRandomString(6);
			oders.setOderId(odersId);
			
			oders.setShop(shop);
			oders.setName(addr.getUname());
			oders.setAddress(addr.getDetail()+" "+addr.getWard()+" "+addr.getDistrict()+" "+addr.getProvince());
			oders.setPhone(addr.getPhone());
			oders.setStatus(0);
			oders.setUser(userInfo);
			oderservice.save(oders);
			int total=0;
			for (Product product : productList) {
				System.out.print(product.getName());
				// Kiểm tra nếu sản phẩm thuộc cửa hàng này
				if (product.getShop().getId().equals(shop.getId())) {
					OrderDetail orderDetail = new OrderDetail();
					
					for (int i = 0; i < productIds.size(); i++) {
						
						String productId = productIds.get(i);
						if (product.getId().toString().equals(productId)) {

//                        	Optional<Orders> oderss=oderservice.findByOderId(odersId);
							String quantity = quantities.get(i);
							orderDetail.setProduct(product);
							orderDetail.setQuantity(Integer.parseInt(quantity)); // Chuyển số lượng thành số nguyên
							orderDetail.setPrice(product.getPrice());
							orderDetail.setOrders(oders);
							
							total+=product.getPrice()*Integer.parseInt(quantity);
							// Thêm vào đơn hàng hoặc thực hiện xử lý khác

							// Bạn có thể thêm orderDetail vào đơn hàng hoặc thực hiện hành động khác ở đây
						}
						// Thực hiện thêm sản phẩm vào đơn hàng hoặc xử lý đơn hàng tùy theo yêu cầu
					}
					orderDetail.setTotal(total);
					oderdetailservice.save(orderDetail);
				}
			}
			Optional<Orders> or=oderservice.findByOderId(odersId);
			Orders orde=or.get();
			
			System.out.print(orde.getId());
			orde.setTotalPay(total);
			oderservice.save(orde);
		}
		// Thực hiện thanh toán hoặc thêm vào giỏ hàng (tùy yêu cầu)
		return "redirect:/user/cart"; // Chuyển hướng sau khi thanh toán thành công
	}

	public static String generateRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Chữ cái và số
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length()); // Lấy chỉ số ngẫu nhiên
			randomString.append(characters.charAt(index)); // Thêm ký tự vào chuỗi
		}

		return randomString.toString();
	}

}
