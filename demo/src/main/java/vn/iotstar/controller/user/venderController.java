package vn.iotstar.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/vender/list")
public class venderController {

	@RequestMapping("")
	public String venderList()
	{
		return "page/vender-list";
	}
}
