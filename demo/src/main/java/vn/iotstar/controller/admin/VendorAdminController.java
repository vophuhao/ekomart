package vn.iotstar.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/vendor")
public class VendorAdminController {

	
	@GetMapping("/list")
	public String listVendor()
	{
		return "admin/vendor-list";
	}
	
}
