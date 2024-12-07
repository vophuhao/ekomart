package vn.iotstar.controller.admin;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.service.admin.AdminICategoryService;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryRestController {
	
	@Autowired
	AdminICategoryService categoryService;
	
	@GetMapping("/edit/{id}")
	public CategoryModel edit(@PathVariable("id") Long id) {
		
	    Optional<Category> optCategory = categoryService.findById(id);
	    CategoryModel cateModel = new CategoryModel();
	    if(optCategory.isPresent()) {
	        Category entity = optCategory.get();
	        BeanUtils.copyProperties(entity, cateModel);
	               
	    }
	    System.out.print(cateModel);
	    return cateModel;
	}
	@GetMapping("/edit-deloy")
	public String editDeploy(@RequestParam("id") Long id, @RequestParam("status") int status) {
		
		
	    Optional<Category> optCategory = categoryService.findById(id);
	    Category cate=optCategory.get();
	    if(status==0)
	    {
	    	
	    	cate.setStatus(1);
	    }
	    else {
	    	
	    	cate.setStatus(0);
	    }
	   
	    categoryService.save(cate);
	    
	    
	    return "redirect:/admin/categories";
	}
	@GetMapping("/add")
	public CategoryModel add() {
	    CategoryModel cateModel = new CategoryModel();
	        cateModel.setIsEdit(false);	        
	    
	    return cateModel;
	}

}
