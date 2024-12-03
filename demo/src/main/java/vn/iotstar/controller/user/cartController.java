package vn.iotstar.controller.user;

import java.io.Console;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.model.CartItemModel;
import vn.iotstar.model.productPayment;
import vn.iotstar.model.productPayment.SelectedProduct;
import vn.iotstar.service.user.Imp.CartServiceImpl;

@Controller
@RequestMapping("/cart")
public class cartController {
	@Autowired
    private CartServiceImpl cartService;
    
	@GetMapping("")
    public String showCart(Model model) {
        Cart cart = cartService.findByUserId(1L);
        model.addAttribute("cart", cart);
        return "user/cart";
    }
	
	@GetMapping("/payment")
    public String showPayment(Model model) {
       
        return "user/order";
    }
	
	@PostMapping("/add-item")
	public String addItemToCart(@Valid CartItem cartItem, BindingResult result) {
	    if (result.hasErrors()) 
	    	return "cart";
	    cartService.addItemToCart(1L, cartItem);
	    return "redirect:/cart";
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

