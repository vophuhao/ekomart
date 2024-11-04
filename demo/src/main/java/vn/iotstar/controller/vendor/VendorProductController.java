//package vn.iotstar.controller.vendor;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import org.springframework.ui.Model;
//import jakarta.validation.Valid;
//import vn.iotstar.entity.Product;
//import vn.iotstar.service.admin.AdminIProductService;
//import vn.iotstar.service.vendor.VendorIProductService;
//
//@Controller
//@RequestMapping("/vendor/{id}/products") // id goc cua shop 
//public class VendorProductController {
//
//    @Autowired
//    VendorIProductService productService;
//
//    @GetMapping("")
//    public String listProduct(@PathVariable("id") Long id, ModelMap model, 
//                              @RequestParam(value = "name", required = false) String productName,
//                              @RequestParam(value = "status", required = false) Integer status,
//                              @RequestParam(value = "display", required = false) Integer display) 
//    {
//        List<Product> list = productService.findProductsByShopId(id);
//        model.addAttribute("products", list);
//
//        if (productName != null) {
//            Optional<Product> listByName = productService.findByName(productName);
//            model.addAttribute("listByName", listByName.orElse(null));
//        }
//
//        if (status != null) {
//            Optional<Product> listByStatus = productService.findByStatus(status);
//            model.addAttribute("listByStatus", listByStatus.orElse(null));
//        }
//
//        if (display != null) {
//            Optional<Product> listByDisplay = productService.findByDisplay(display);
//            model.addAttribute("listByDisplay", listByDisplay.orElse(null));
//        }
//
//        return "vendor/product-list";
//    }
//
//    @GetMapping("/display/product{id}")
//    public String edit(@PathVariable("productid") long id, Model model) {
//        Product product = productService.getById(id);
//        model.addAttribute("product", product);
//        return "vendor/display-product";
//    }
//
//    @PostMapping("/update")
//    public String update(@Valid Product product, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "vendor/home";
//        }
//        productService.save(product);
//        return "redirect:/vendor/products";
//    }
//}
