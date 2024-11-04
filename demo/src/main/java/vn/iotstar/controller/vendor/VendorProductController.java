package vn.iotstar.controller.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import jakarta.validation.Valid;
import vn.iotstar.entity.Product;
import vn.iotstar.service.vendor.VendorIProductService;

@Controller
@RequestMapping("/vendor/{shopId}/products") // id goc cua shop 
public class VendorProductController {

    @Autowired
    VendorIProductService productService;

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

    @GetMapping("/display/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "vendor/display-product";
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
