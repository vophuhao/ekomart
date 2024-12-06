package vn.iotstar.controller.vendor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.Product;
import vn.iotstar.service.Imp.OderDetailServiceImpl;
import vn.iotstar.service.Imp.OderServiceImpl;
import vn.iotstar.service.vendor.VendorIProductService;

@Controller
@RequestMapping("/vendor/order")
public class VendorOderController {

	@Autowired
	OderServiceImpl oderservice;
	
	@Autowired
    VendorIProductService productService;
	
	@Autowired
	private OderDetailServiceImpl oderdetailservice;
	
	
	
	@GetMapping("/detail")
	public String oderDetail(@RequestParam("id") String id,Model model)
	{
		
		Optional<Orders> orders=oderservice.findByOderId(id);
		 Orders orderss=new Orders();
		 
		
		if (orders.isPresent()) {
		    orderss = orders.get();
		    // Xử lý logic với order
		}
		
		List<OrderDetail> listOrder=oderdetailservice.findByOrders(orderss);
		Address address=orderss.getUserAddress();
		
		
		model.addAttribute("addressUser", address);
		model.addAttribute("orderlist", listOrder);
		return "vendor/oder-detail";
	}
	
	@GetMapping("/waiting")
	public String listOderWaiting(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 0);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/confirm")
	public String listOderConfirm(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 1);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/shipping")
	public String listOderShipping(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 2);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/complete")
	public String listOderComplete(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 3);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/cancel")
	public String listOderCancel(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 4);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	
	@GetMapping("/refund")
	public String listOderRefund(Model model)
	{
		List<Orders> list=oderservice.findByShopIdAndStatus(11L, 5);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	
	@GetMapping("/detail{oderId}")
	public String detailOder()
	{
		return "vendor/oder-detail";
	}
	
	@PostMapping("/status")
	public ResponseEntity<?> statusOrder(@RequestBody Map<String, Object> requestData) {
	    String orderId = requestData.get("orderId").toString();
	    Integer status = Integer.valueOf(requestData.get("status").toString());
	    
	    // Logic cập nhật trạng thái đơn hàng
	    Optional<Orders> ordersOpt = oderservice.findByOderId(orderId);

	    if (ordersOpt.isPresent()) {
	        Orders order = ordersOpt.get();
	        if (status == 3) {
	            // Duyệt qua các OrderDetail trong đơn hàng và cập nhật số lượng sold của từng sản phẩm
	            for (OrderDetail orderDetail : order.getOrderDetails()) {
	                Product product = orderDetail.getProduct();
	                int quantitySold = orderDetail.getQuantity();

	                // Tăng sold của sản phẩm tương ứng với số lượng trong OrderDetail
	                product.setSold(product.getSold() + quantitySold);
	                
	                // Lưu lại thông tin sản phẩm sau khi cập nhật
	                productService.save(product); // Giả sử bạn có một service để lưu sản phẩm
	            }
	        }
	        order.setStatus(status);
	        oderservice.save(order);
	        return ResponseEntity.ok().body("Order updated successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
	    }
	}

}
