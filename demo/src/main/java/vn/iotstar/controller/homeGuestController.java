package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/home")
public class homeGuestController {
	
	@GetMapping("")
	public String homeGuest() {
		
		return "page/home";
	}
}
