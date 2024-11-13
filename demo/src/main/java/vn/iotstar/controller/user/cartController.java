package vn.iotstar.controller.user;

import java.io.Console;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.model.productPayment;
import vn.iotstar.model.productPayment.SelectedProduct;

@Controller
@RequestMapping("/home/cart")
public class cartController {

	@RequestMapping("")
	public String cart(Model model)
	{
//		 model.addAttribute("productPayment", new productPayment());
		return "page/cart";
	}
	
	@PostMapping("/payment")
	public String paymentCart(@ModelAttribute("productPayment") productPayment ProductPayment, Model model, RedirectAttributes redirectAttributes)
	{
		 model.addAttribute("productPayment", new productPayment());
		if (ProductPayment == null) {
	        // Kiểm tra nếu đối tượng productPayment là null
	        System.out.println("productPayment is null!");
	        return "errorPage"; // Hoặc xử lý lỗi thích hợp
	    }
		else {
			
		 List<SelectedProduct> selectedProducts = ProductPayment.getSelectedProduct();
				 if (selectedProducts == null) {
				        // Kiểm tra nếu selectedProducts là null
				        System.out.println("selectedProducts is null!");
				        return "errorPage";
				    }
				 else
				 {
					 selectedProducts = selectedProducts.stream()
			        .filter(SelectedProduct::getProductSelected)
			        .collect(Collectors.toList());
		 	
					 System.out.print(selectedProducts);
	       
				 return "page/cart-payment";}
		}
	}
}

