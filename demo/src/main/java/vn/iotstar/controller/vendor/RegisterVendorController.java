package vn.iotstar.controller.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.vendor.VendorIRegisterService;

@Controller
@RequestMapping("/vendor/register")
public class RegisterVendorController {

	@Autowired
	private VendorIRegisterService vendorIRegisterService;
	
	@Autowired
	private IUserService userServicer;
	
	@GetMapping("/form1")
	public String showForm1(ModelMap model) {
		Optional<UserInfo> user = userServicer.findById(2);
		Shop shop = new Shop();
		AddressShop address = new AddressShop();
		IdentificationInfo info = new IdentificationInfo();
		   if (user.isPresent()) {
			   shop.setEmail(user.get().getEmail());
			   shop.setUser(user.get());
			   shop.setAddress(address);
			   shop.setInfo(info);
		   }
			model.addAttribute("shop",shop);
		return "vendor/register-shop-form1";

	}

	@PostMapping("/save")
	public String registerSuccess(@Valid Shop shop, BindingResult result) {
		if (result.hasErrors()) {
			return "vendor/register-shop-form1";
		}
		// set mặc định là shop active
		shop.setStatus(1);

		vendorIRegisterService.save(shop);
		return "vendor/register-done";
	}	
	
}