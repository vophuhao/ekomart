package vn.iotstar.controller.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.vendor.VendorIRegisterService;

@Controller
@RequestMapping("/vendor/register")
@SessionAttributes({"shop", "address","info"})
public class RegisterVendorController {
	
	@Autowired
	VendorIRegisterService vendorIRegisterService;
	
	@Autowired
	IUserService userServicer;
	
	@GetMapping("/form1")
	public String showForm1(ModelMap model) {
		Optional<UserInfo> user = userServicer.findById(2);
		Shop shop = new Shop();
		AddressShop address = new AddressShop();
		   if (user.isPresent()) {
			   shop.setEmail(user.get().getEmail());
			   shop.setUser(user.get());
			   shop.setAddress(address);
		   }
		   
		model.addAttribute("shop",shop);
		model.addAttribute("address",address);
		return "vendor/register-shop-form1";

	}
	
	@PostMapping("/form2")
	public String moveToForm2(@Valid Shop shop,@Valid AddressShop address, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "vendor/register-shop-form1";
		}
		IdentificationInfo info = new IdentificationInfo();
		shop.setInfo(info);
		model.addAttribute("shop",shop);
		model.addAttribute("address",address);
		model.addAttribute("info",info);
		return "vendor/register-shop-form2";
	}
	
	@PostMapping("/save")
	public String registerSuccess(@Valid Shop shop, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "vendor/register-shop-form2";
		}
		
		// set mặc định là shop active 
		shop.setStatus(1);
		vendorIRegisterService.save(shop);
		return "vendor/register-done";
	}	
	
}