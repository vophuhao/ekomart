package vn.iotstar.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller



@RequestMapping("/user/oder-detail")
public class oderController {

	
	@GetMapping("")
	public String oderDetail()
	{
		return "page/oder-detail";
	}
	
}
