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
		     // Kiểm tra nếu đối tượng ProductPayment là null
		     System.out.println("ProductPayment is null!");
		     return "errorPage"; // Hoặc xử lý lỗi thích hợp
		 } else {
		     // Lấy danh sách các sản phẩm từ ProductPayment
		     List<SelectedProduct> selectedProducts = ProductPayment.getSelectedProduct();

		     if (selectedProducts == null) {
		         // Nếu danh sách null, in thông báo và chuyển đến trang lỗi
		         System.out.println("selectedProducts is null!");
		         return "errorPage";
		     } else {
		         // Lọc các sản phẩm có productSelected = true
		         selectedProducts = selectedProducts.stream()
		                 .filter(SelectedProduct::getProductSelected) // Điều kiện lọc: productSelected = true
		                 .collect(Collectors.toList());

		         // Kiểm tra nếu danh sách sau khi lọc rỗng
		         if (selectedProducts.isEmpty()) {
		             System.out.println("No selected products found!");
		             return "errorPage"; // Hoặc trả về trang thông báo khác
		         }

		         // Thêm danh sách đã lọc vào model để hiển thị trên view
		         model.addAttribute("selectedProducts", selectedProducts);

		         // In danh sách các sản phẩm đã chọn (dành cho debug)
		         System.out.println("Selected Products: " + selectedProducts);

		         // Chuyển hướng đến trang thanh toán
		         return "page/cart-payment";
		     }
		 }

	}

}

