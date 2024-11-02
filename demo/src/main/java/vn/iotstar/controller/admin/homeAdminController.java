package vn.iotstar.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/admin/home")
public class homeAdminController {
	
	@RequestMapping("")
	public String homeAdmin() {
		
		return "admin/home-content";
	}
}
