package vn.iotstar.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/user")
public class homeController {
	
	@GetMapping("/home")
	public String homeView() {
		
		return "page/home";
	}
}
