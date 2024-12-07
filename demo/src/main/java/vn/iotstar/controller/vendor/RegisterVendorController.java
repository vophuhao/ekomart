package vn.iotstar.controller.vendor;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.ShopModel;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.user.Imp.UserProductServiceImpl;
import vn.iotstar.service.vendor.VendorIRegisterService;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/vendor")
public class RegisterVendorController {

	@Autowired
	private VendorIRegisterService vendorIRegisterService;
	
	@Autowired
	private IUserService userServicer;

	@Autowired
	IStorageService storageService;
    @Autowired
    private JwtUtil jwtUtil;
	@Autowired
	private IUserService userService;


	@GetMapping("/register")
	public String showForm1(HttpServletRequest request,ModelMap model) {
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
		Shop shop = new Shop();
		AddressShop address = new AddressShop();
		IdentificationInfo info = new IdentificationInfo();
		   if (user.isPresent()) {
			   shop.setEmail(user.get().getEmail());
			   shop.setUser(user.get());
			   shop.setAddress(address);
			   shop.setInfo(info);
		   }
		   ShopModel shopModel = new ShopModel();
		   BeanUtils.copyProperties(shop, shopModel);
			model.addAttribute("shop",shopModel);
		return "vendor/register-open-shop";

	}

	@PostMapping("/register/save")
	public String registerSuccess( @Valid @ModelAttribute("shop") ShopModel shopModel,HttpServletRequest request , BindingResult result) {
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
		String shopId=generateRandomString();
		String username = jwtUtil.extractUsername(token);
		Optional<UserInfo> user = userService.findByName(username);
        user.ifPresent(shopModel::setUser);
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return "vendor/register-open-shop";
		}
		// set mặc định là shop active
		Shop shop = new Shop();
		BeanUtils.copyProperties(shopModel, shop);
		shop.setStatus(0);
		if(!shopModel.getRts_images0().isEmpty()) {
			//lưu file vào trường poster
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			shop.getInfo().setBeforeImage(storageService.getSorageFilename(shopModel.getRts_images0(), uuString));
			storageService.store(shopModel.getRts_images0(), shop.getInfo().getBeforeImage());

			UUID uuid1 = UUID.randomUUID();
			String uuString1 = uuid1.toString();
			shop.getInfo().setAfterImage(storageService.getSorageFilename(shopModel.getRts_images2(), uuString1));
			storageService.store(shopModel.getRts_images2(), shop.getInfo().getAfterImage());
			
			UUID uuid_logo = UUID.randomUUID();
			String uuString_logo = uuid_logo.toString();
			shop.setAvatar(storageService.getSorageFilename(shopModel.getRts_images1(), uuString_logo));
			storageService.store(shopModel.getRts_images1(), shop.getAvatar());
		}
		shop.setShopId(shopId);
		shop.setDisplay(1);
		vendorIRegisterService.save(shop);
		return "redirect:/vendor/register/done";
	}	
	 public String generateRandomString() {
	        // Tiền tố "EK"
	        String prefix = "EK";

	        // Tạo chuỗi ngẫu nhiên với 4 ký tự (chữ hoa và số)
	        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder(prefix);

	        for (int i = 0; i < 4; i++) {
	            int index = random.nextInt(characters.length());
	            sb.append(characters.charAt(index));
	        }

	        return sb.toString();
	    }
	@GetMapping("/order")
	public String show() {
		return "user/order";

	}
	@GetMapping("/register/done")
	public String doneRegister() {
		return "vendor/register-done";

	}
}