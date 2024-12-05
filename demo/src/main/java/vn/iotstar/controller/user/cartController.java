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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.model.OderRequest;
import vn.iotstar.model.productPayment;
import vn.iotstar.model.productPayment.SelectedProduct;
import vn.iotstar.service.Imp.OderDetailServiceImpl;
import vn.iotstar.service.Imp.OderServiceImpl;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.ILocationService;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.ProductServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;

@Controller
@RequestMapping("/user")
public class cartController {
	@Autowired
	private CartServiceImpl cartService;

	@Autowired
	private UserInfoServiceImp userservice;

	@Autowired
	private AdminShopService shopservice;

	@Autowired
	private AddressServiceImp addre;

	@Autowired
	private ILocationService location;

	@Autowired
	private OderServiceImpl oderservice;

	@Autowired
	private OderDetailServiceImpl oderdetailservice;

	@Autowired
	private ProductServiceImpl productservice;

	@GetMapping("/cart")
	public String showCart(Model model) {
		Cart cart = cartService.findByUserId(1L);
		model.addAttribute("cart", cart);
		return "page/cart";
	}

	@PostMapping("/add-item")
	public String addItemToCart(@Valid CartItem cartItem, BindingResult result) {
		if (result.hasErrors())
			return "redirect:/user/cart";
		cartService.addItemToCart(1L, cartItem);
		return "redirect:/user/cart";
	}

	@GetMapping("/cart/payment")
	public String paymentCart(@ModelAttribute("productPayment") productPayment ProductPayment, Model model,
			RedirectAttributes redirectAttributes) {
		List<Address> addressUser = addre.findByUser_Id(1L);
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

	@PostMapping("/cart/payment")
	public String processPayment(@RequestBody OderRequest orderRequest) {

		System.out.print(orderRequest);
		String addressId = orderRequest.getAddressId();
		List<String> productIds = orderRequest.getProductIds();
		List<String> quantities = orderRequest.getQuantities();
		Optional<Address> address = addre.findById(Long.parseLong(addressId));
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
			oders.setUserAddress(addr);
			oders.setShop(shop);
			oders.setStatus(0);
			oderservice.save(oders);
			for (Product product : productList) {
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
							// Thêm vào đơn hàng hoặc thực hiện xử lý khác

							// Bạn có thể thêm orderDetail vào đơn hàng hoặc thực hiện hành động khác ở đây
						}
						// Thực hiện thêm sản phẩm vào đơn hàng hoặc xử lý đơn hàng tùy theo yêu cầu
					}

					oderdetailservice.save(orderDetail);
				}
			}
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
