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


@Controller
@RequestMapping("/admin/vendor")
public class VendorAdminController {

	@Autowired
	AdminIProductService productService;
	
	@Autowired
	IStorageService storageService;
	
	@GetMapping("/product/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/list")
	public String listVendor()
	{
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
		List<Product> listProduct=productService.findByStatus(0);
	
		model.addAttribute("listProduct",listProduct);
		
		
		return "admin/product-vendor";
	}
	@GetMapping("/product/detail/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile2(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/product/detail")
	public String productVendorDetail(@RequestParam("id") Long id, Model model)
	{
		Product product=new Product();
		product=productService.getById(id);
	
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
	
}
