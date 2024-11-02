package vn.iotstar.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.service.admin.ICategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	@RequestMapping("")
	public String listCategory(ModelMap model)
	{
		List<Category> list=categoryService.findAll();
		model.addAttribute("categories", list);
	
		return "admin/list";
	}

//	@GetMapping("/add")
//	public  String addCategory() {
//		
//		
//		return "admin/add-category";
//	}
	@PostMapping("/add")
	public String add(@Valid Category category, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
		/*
		 * System.out.print(category); if (result.hasErrors()) { return
		 * "redirect:/admin/categories"; }
		 */
		 redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully!");
		categoryService.save(category);
		return "redirect:/admin/categories";
	}
//	
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") long id, ModelMap model) {
//		Category category = categoryService.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
//		model.addAttribute("category", category);
//		return "admin/edit-category";
//	}
//
//	@PostMapping("/update")
//	public String update(@Valid Category category, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "admin/home";
//		}
//		categoryService.save(category);
//		return "redirect:/admin/categories";
//	}
}
