package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import jakarta.validation.Valid;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.service.admin.AdminIProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    AdminIProductService productService;

    @GetMapping("")
    public String listProduct(ModelMap model, 
                              @RequestParam(value = "name", required = false) String productName,
                              @RequestParam(value = "status", required = false) Integer status,
                              @RequestParam(value = "display", required = false) Integer display) 
    {
        List<Product> list = productService.findAll();
        model.addAttribute("products", list);

        if (productName != null) {
            Optional<Product> listByName = productService.findByName(productName);
            model.addAttribute("listByName", listByName.orElse(null));
        }

      
        if (display != null) {
            Optional<Product> listByDisplay = productService.findByDisplay(display);
            model.addAttribute("listByDisplay", listByDisplay.orElse(null));
        }

        return "admin/product-list";
    }

    @GetMapping("/display/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/display-product";
    }

}
