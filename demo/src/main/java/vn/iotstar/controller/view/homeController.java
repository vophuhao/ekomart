package vn.iotstar.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/home")
public class homeController {
	
	@RequestMapping("")
	public String homeView() {
		
		return "page/home";
	}
}
