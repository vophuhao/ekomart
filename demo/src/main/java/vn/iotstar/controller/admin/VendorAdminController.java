package vn.iotstar.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.service.admin.AdminShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/admin/vendor")
public class VendorAdminController {

	@Autowired
	AdminShopService adminShopService;
	
	@GetMapping("/list")
	public String listVendor(ModelMap model)
	{
		List<Shop> list = new ArrayList<>();
		list = adminShopService.findAll();
		model.addAttribute("list", list);
		return "admin/vendor-list";
	}

	@PostMapping("/update")
	public String update(@Valid Shop vendor, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/home";
		}
		Optional<Shop> optVendor = adminShopService.findById(vendor.getId());
		if (optVendor.isPresent()) {
			Shop existingVendor = optVendor.get();
			existingVendor.setDisplay(vendor.getDisplay());
			adminShopService.save(existingVendor); // Lưu đối tượng đã cập nhật
		}
		return "redirect:/admin/vendor/list";
	}
}
