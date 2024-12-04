package vn.iotstar.controller.vendor;

import java.util.Optional;
import java.util.UUID;

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
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.vendor.VendorIRegisterService;

@Controller
@RequestMapping("/vendor")
public class RegisterVendorController {

	@Autowired
	private VendorIRegisterService vendorIRegisterService;
	
	@Autowired
	private IUserService userServicer;

	@Autowired
	IStorageService storageService;
	
	@GetMapping("/register")
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
		   ShopModel shopModel = new ShopModel();
		   BeanUtils.copyProperties(shop, shopModel);
			model.addAttribute("shop",shopModel);
		return "vendor/register-open-shop";

	}

	@PostMapping("/register/save")
	public String registerSuccess( @Valid @ModelAttribute("shop") ShopModel shopModel , BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return "vendor/register-open-shop";
		}
		// set mặc định là shop active
		Shop shop = new Shop();
		BeanUtils.copyProperties(shopModel, shop);
		shop.setStatus(0);
		System.out.println(shop.getName());
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
		}
		vendorIRegisterService.save(shop);
		return "vendor/register-done";
	}	
	
	@GetMapping("/order")
	public String show() {
		return "user/order";

	}
}