package vn.iotstar.controller.vendor;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.model.productModel;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminICategoryService;
import vn.iotstar.service.vendor.VendorIProductService;

@Controller
@RequestMapping("/vendor/products") // id goc cua shop
public class VendorProductController {

    @Autowired
    VendorIProductService productService;

    @Autowired
    AdminICategoryService categoryService;

    @Autowired
	 private ShopRepository shoprepo;

    @Autowired
    IStorageService storageService;


    @GetMapping("")
    public String listProduct(@PathVariable("id") Long id, ModelMap model,
                              @RequestParam(value = "name", required = false) String productName,
                              @RequestParam(value = "status", required = false) Integer status,
                              @RequestParam(value = "display", required = false) Integer display)
    {
        List<Product> list = productService.getProductsByShopId(id);
        model.addAttribute("products", list);

        if (productName != null) {
            List<Product> listByName = productService.findByName(productName,id);
            model.addAttribute("listByName", listByName);
        }

        if (status != null) {
            List<Product> listByStatus = productService.findByStatus(status,id);
            model.addAttribute("listByStatus", listByStatus);
        }

        if (display != null) {
            List<Product> listByDisplay = productService.findByDisplay(display,id);
            model.addAttribute("listByDisplay", listByDisplay);
        }

        return "vendor/product-list";
    }
    @GetMapping("/add")
    public String addProduct(Model model) {

    	List<Category> categoryList=categoryService.findAll();
    	model.addAttribute("cateList",categoryList);

    	return "vendor/product-add";
    }
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename){

        Resource file =storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }

    public String ShopId(HttpServletRequest request,HttpSession session)
    {
    	String shopId=(String) session.getAttribute("shopId");
    	return shopId;
    }
    @GetMapping("/list")
    public String listProduct(HttpServletRequest request,HttpSession session,Model model) {


        Shop shopp=(Shop) session.getAttribute("shop");


    	List<Product> product=productService.findByShop(shopp);

    	model.addAttribute("listProduct", product);
    	return "vendor/product-list";
    }


	@PostMapping("/add")
    public ModelAndView addNewProduct(@Valid @ModelAttribute("products") productModel productModel ,
	        BindingResult result,
	        RedirectAttributes redirectAttributes)
    {
    	if(result.hasErrors()) {
	        return new ModelAndView("vendor/product-list", "products", productModel);
	    }
    	Category cate=new Category();
    	cate=categoryService.getById(productModel.getCategoryId());

	    Product entity = new Product();
	    BeanUtils.copyProperties(productModel, entity);
	    entity.setCategory(cate);
	    entity.setStatus(0);
	    entity.setDisplay(0);
	   entity.setShop(null);
	    if(!productModel.getRts_images1().isEmpty()) {
	    	//lưu file vào trường poster
	    	UUID uuid = UUID.randomUUID();
	    	String uuString = uuid.toString();
	    	entity.setImage (storageService.getSorageFilename(productModel.getRts_images1(), uuString));
	    	storageService.store(productModel.getRts_images1(), entity.getImage());
	    	}


	   productService.save(entity);
	    redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
	    return new ModelAndView("redirect:/vendor/products/list");
    }


    @GetMapping("/display/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "vendor/display-product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "vendor/product-edit";
    }

    @PostMapping("/update")
    public String update(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "vendor/home";
        }
        productService.save(product);
        return "redirect:/vendor/products";
    }
}
