package vn.iotstar.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/product-detail")
public class productController {

	@RequestMapping("")
	public String productDetail()
	{
		
		return "page/product-detail";
	}
	
}
