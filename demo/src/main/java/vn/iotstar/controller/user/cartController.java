package vn.iotstar.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/cart")
public class cartController {

	@RequestMapping("")
	public String cart()
	{
		
		return "page/cart";
	}
}
