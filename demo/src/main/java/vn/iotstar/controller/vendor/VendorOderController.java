package vn.iotstar.controller.vendor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.IOderService;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.UserService;
import vn.iotstar.service.Imp.OderDetailServiceImpl;
import vn.iotstar.service.vendor.RevenueService;
import vn.iotstar.service.vendor.VendorIProductService;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/vendor/order")
public class VendorOderController {

	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	IStorageService storageService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	IOderService oderservice;
	
	@Autowired
    VendorIProductService productService;
	
	@Autowired
	private OderDetailServiceImpl oderdetailservice;
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	
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
		
		model.addAttribute("orderlist", listOrder);
		model.addAttribute("orders", orderss);
		return "vendor/oder-detail";
	}
	
	@GetMapping("/waiting")
	public String listOderWaiting(Model model, HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 0);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/confirm")
	public String listOderConfirm(Model model, HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 1);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/shipping")
	public String listOderShipping(Model model,HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 2);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/complete")
	public String listOderComplete(Model model, HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 3);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	@GetMapping("/cancel")
	public String listOderCancel(Model model,HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 4);
		
		model.addAttribute("listOder", list);
		System.out.print(list.size());
		return "vendor/oder-list";
	}
	
	@GetMapping("/refund")
	public String listOderRefund(Model model,HttpServletRequest request)
	{
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		List<Orders> list=oderservice.findByShopIdAndStatus(shop.getId(), 5);
		
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
