package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminIProductService;
import jakarta.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import vn.iotstar.entity.Shop;
import vn.iotstar.service.admin.AdminShopService;
import java.util.ArrayList;


@Controller
@RequestMapping("/admin/vendor")
public class VendorAdminController {

	@Autowired
	private AdminIProductService productService;
	@Autowired
	private AdminShopService adminShopService;
	@Autowired
	IStorageService storageService;


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


	@GetMapping("/list")
	public String listVendor(ModelMap model)
	{
		List<Shop> list = adminShopService.findAll();
		model.addAttribute("list", list);
		return "admin/vendor-list";
	}

	
	@GetMapping("/list/approve")
	public String listVendorApprove()
	{
		return "admin/vendor-list-approve";
	}
	
	@GetMapping("/detail")
	public String detailVendor()
	{
		return "admin/vendor-detail";
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
			Shop existingVendor = optVendor.get();
			existingVendor.setDisplay(vendor.getDisplay());
			adminShopService.save(existingVendor); // Lưu đối tượng đã cập nhật
		}
		return "redirect:/admin/vendor/list";
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
