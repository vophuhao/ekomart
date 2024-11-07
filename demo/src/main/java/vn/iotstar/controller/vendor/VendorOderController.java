package vn.iotstar.controller.vendor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendor/oder")
public class VendorOderController {

	
	@GetMapping("/list")
	public String listOder()
	{
		return "vendor/oder-list";
	}
	
	
	@GetMapping("/detail{oderId}")
	public String detailOder()
	{
		return "vendor/oder-detail";
	}
}
