package vn.iotstar.controller.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminIProductService;
import jakarta.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import vn.iotstar.entity.Shop;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.user.ProductService;

import java.util.ArrayList;


@Controller
@RequestMapping("/home/vendor")
public class VendorGuestController {

	@Autowired
	private AdminIProductService productService;
	@Autowired
	private AdminShopService adminShopService;
	@Autowired
	IStorageService storageService;
	@Autowired
	IUserService userService;
	@Autowired
	private ProductService proService;


	@GetMapping("/product/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile1(@PathVariable String filename){

		Resource file =storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}

	@GetMapping("/detail/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile11(@PathVariable String filename){

		Resource file =storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/list/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile21(@PathVariable String filename){

		Resource file =storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/list/waiting")
	public String listVendor(ModelMap model)
	{
		List<Shop> list = adminShopService.findByStatus(0);
		model.addAttribute("list", list);
		return "admin/vendor-list-waiting";
	}

	@GetMapping("/detail")
	public String vendorDetail(@RequestParam("shopId") String shopId,
								@RequestParam(defaultValue = "0") int page,
					            @RequestParam(defaultValue = "6") int size,
					            Model model)
	{
		Optional<Shop> shop=adminShopService.findByShopId(shopId);
		Shop shopp=new Shop();
		if(shop.isPresent())
		{
			shopp=shop.get();
			
		}
		Page<Product> productPage = proService.findByShop(shopp,  PageRequest.of(page, size));

		model.addAttribute("productList", productPage);
		model.addAttribute("shop",shopp);
		model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
		return "page/vendor-detail-guest";
	}
	@GetMapping("/list")
	public String listVendor(Model model)
	{
		List<Shop> listShop=adminShopService.findByStatus(1);
		
		model.addAttribute("listShop",listShop);
		return "page/vendor-list-guest";
	}
	
	
	@GetMapping("/product")
	public String productVendor(Model model)
	{
		List<Product> listProduct=productService.findAll();
	
		model.addAttribute("listProduct",listProduct);
		
		
		return "admin/product-vendor";
	}
	@GetMapping("/product/detail/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile2(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/product/detail/{id}")
	public String productVendorDetail(@PathVariable("id") Long id, Model model)
	{
		Product product=new Product();
		product=productService.getById(id);
		Optional<Shop> shop = adminShopService.findById(product.getShop().getId());
		if(shop.isPresent())
		{
			model.addAttribute("shop",shop.get());
			System.out.print("hello");
		}
		model.addAttribute("product",product);
		return "admin/product-detail";
	}
	
	@PostMapping("/product/detail")
	public ModelAndView productVendorDetailsetStatus(@RequestParam("id") Long id, Model model)
	{
		
		System.out.print(id);
		Product product=new Product();
		product=productService.getById(id);
		product.setStatus(1);
		productService.save(product);
		return new ModelAndView ("redirect:/admin/vendor/product");
	}
	

	@PostMapping("/update")
	public String update(@Valid Shop vendor, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/home";
		}
		Optional<Shop> optVendor = adminShopService.findById(vendor.getId());
		if (optVendor.isPresent()) {
			UserInfo user = optVendor.get().getUser();

			// Nếu admin duyệt shop thì update ROLE USER => ROLE VENDOR
			if(vendor.getDisplay() == 1)
			{
				user.setRoles("ROLE_VENDOR");
				userService.save(user);
			}

			//Cập nhật display cho vendor
			Shop existingVendor = optVendor.get();
			existingVendor.setStatus(vendor.getDisplay());
			adminShopService.save(existingVendor);
		}
		return "redirect:/admin/vendor/list/waiting";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@Valid Product product, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/home";
		}
		Product existingProduct = productService.getById(product.getId());
		existingProduct.setDisplay(product.getDisplay());
		productService.save(existingProduct); // Lưu đối tượng đã cập nhật
		return "redirect:/admin/vendor/product";
	}
}
