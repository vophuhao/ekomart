package vn.iotstar.controller.vendor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/vendor")
public class homeVendorController {
	
	@GetMapping("/home")
	public String homeView() {
		
		return "vendor/home";
	}
}
