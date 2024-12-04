package vn.iotstar.controller.user;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.AddresModel;
import vn.iotstar.model.productPayment;
import vn.iotstar.model.productPayment.SelectedProduct;
import vn.iotstar.service.Imp.LocationServiceImpl;
import vn.iotstar.service.user.ILocationService;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;

@Controller
@RequestMapping("/user")
public class cartController {
	@Autowired
    private CartServiceImpl cartService;
    
	@Autowired
	private UserInfoServiceImp userservice;
	
	@Autowired
	private AddressServiceImp addre;
	
	@Autowired
	private ILocationService location;
	
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
	public String paymentCart(@ModelAttribute("productPayment") productPayment ProductPayment, Model model, RedirectAttributes redirectAttributes)
	{
//		Optional<Address> addressUser=addre.findByUser_Id(1L);
//		
//		List<AddresModel> address= new ArrayList<AddresModel>();
		
//		BeanUtils.copyProperties(addressUser, address);
//		model.addAttribute("addressUser",addressUser);
		
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
		         selectedProducts = selectedProducts.stream()
		                 .filter(SelectedProduct::getProductSelected) // Điều kiện lọc: productSelected = true
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
		 
//	@PostMapping("/api/address/save")
//	public ResponseEntity<Void> saveAddress(@ModelAttribute("address") AddresModel address, Model model) {
//	    try {
//	        // Lấy tên tỉnh, quận, xã từ mã code
//	        String provinceName = location.getProvinceNameByCode(Integer.parseInt(address.getProvince()));
//	        String districtName = location.getDistrictNameByCode(Integer.parseInt(address.getDistrict()));
//	        String wardName = location.getWardNameByCode(Integer.parseInt(address.getWard()));
//
//	        // Kiểm tra nếu không tìm được tên
//	        if (provinceName == null || districtName == null || wardName == null) {
//	            return ResponseEntity.badRequest().build(); // Trả về lỗi 400 nếu mã không hợp lệ
//	        }
//
//	        // Tạo địa chỉ mới
//	        Address newAddress = new Address();
//	        newAddress.setUname(address.getUname());
//	        newAddress.setPhone(address.getPhone());
//	        newAddress.setProvince(provinceName); // Lưu tên tỉnh
//	        newAddress.setDistrict(districtName); // Lưu tên quận
//	        newAddress.setWard(wardName);         // Lưu tên xã
//	        newAddress.setDetail(address.getDetail());
//	        newAddress.setDefaults(1);
//
//	        // Lấy thông tin user (giả sử ID user là 1)
//	        Optional<UserInfo> user2 = userservice.findById(1);
//	        UserInfo entity = user2.get();
//	        if (user2.isPresent()) {
//	            BeanUtils.copyProperties(user2, entity);
//	        }
//
//	        newAddress.setUser(entity);
//	        addre.save(newAddress); // Lưu vào cơ sở dữ liệu
//
//	        // Trả về mã 200 OK với thông báo thành công
//	        return ResponseEntity.ok().build(); // 200 OK mà không có nội dung trả về
//
//	    } catch (Exception e) {
//	        // Nếu có lỗi xảy ra, trả về mã lỗi 500 và thông báo lỗi
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}

	
	
//		 @PostMapping("/address/save")
//		 public String saveAddress(@ModelAttribute("address") Address address, Model model, RedirectAttributes redirectAttributes)
//			{
//			 	String url = "https://provinces.open-api.vn/api/p/" + 1;
//
//		        // Tạo RestTemplate để gọi API
//		        RestTemplate restTemplate = new RestTemplate();
//
//		            // Gửi request GET đến API
//		            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//		            // Nếu API trả về thành công
//		            if (response.getStatusCode().is2xxSuccessful()) {
//		                String provinceName = response.getBody(); // Kết quả là JSON của tỉnh
//		                System.out.print(provinceName);
//		            }
//			 
//			 
//			 		Address address2=new Address();
//			 		Optional<UserInfo> user2=userservice.findById(1);
//			 		 UserInfo entity = user2.get();
//			 		 if(user2.isPresent()) {
//			 	       
//			 	        BeanUtils.copyProperties(user2, entity);
//			 	       	        
//			 	    }
//			 		 
//			 		BeanUtils.copyProperties(address, address2);
//			 		address2.setDefaults(1);
//			 		address2.setUser(entity);
//			 		addre.save(address2);
//				 return "redirect:/user/cart/payment";
//			}
//			
	

}

