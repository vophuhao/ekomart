package vn.iotstar.controller.admin;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.validation.Valid;
import vn.iotstar.DTO.CategoryDTO;
import vn.iotstar.entity.Category;
import vn.iotstar.model.CategoryModel;

import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminICategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	@Autowired
	AdminICategoryService categoryService;
	
	@Autowired
	IStorageService storageService;
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){ 
		
		Resource file =storageService.loadAsResource(filename); 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
	}
	@RequestMapping("")
	public String listCategory(ModelMap model)
	{
		List<CategoryDTO> list=categoryService.findAllCategoryDTO();
	
		model.addAttribute("categories", list);
//		
		return "admin/category-list";
	}

	/*
	 * @GetMapping("/add") public String addCategory() {
	 * 
	 * 
	 * return "admin/home"; }
	 * 
	 * @PostMapping("/add") public ModelAndView
	 * add(@Valid @ModelAttribute("category") CategoryModel cateModel, BindingResult
	 * result, Model model,RedirectAttributes redirectAttributes) {
	 * if(result.hasErrors()) { return new ModelAndView("admin/home2", "category",
	 * cateModel); }
	 * 
	 * Category entity = new Category(); BeanUtils.copyProperties(cateModel,
	 * entity); categoryService.save(entity);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Category saved successfully!"); return new
	 * ModelAndView("redirect:/admin/categories"); }
	 * 
	 * @GetMapping("/edit/{id}") public String edit(@PathVariable("id") Long id,
	 * ModelMap model) { Category category =
	 * categoryService.findById(id).orElseThrow(() -> new
	 * RuntimeException("Not found")); model.addAttribute("category", category);
	 * return "admin/home"; // Render home.html with the category }
	 * 
	 * @GetMapping("/edit/{id}") public String edit(@PathVariable("id") long id,
	 * ModelMap model) { Category category =
	 * categoryService.findById(id).orElseThrow(() -> new
	 * RuntimeException("Not found")); model.addAttribute("category", category);
	 * return "admin/edit-category"; }
	 * 
	 * @GetMapping("/add") public ModelAndView add() { CategoryModel category = new
	 * CategoryModel(); category.setIsEdit(false); return new
	 * ModelAndView("admin/home", "category", category); }
	 */

	/*
	 * @GetMapping("/edit/{id}") public ModelAndView edit(@PathVariable("id") Long
	 * id,HttpSession session) { Optional<Category> optCategory =
	 * categoryService.findById(id); CategoryModel cateModel = new CategoryModel();
	 * if(optCategory.isPresent()) { Category entity = optCategory.get();
	 * BeanUtils.copyProperties(entity, cateModel); cateModel.setIsEdit(true);
	 * session.setAttribute("category",entity); return new
	 * ModelAndView("admin/home", "category", cateModel); } return new
	 * ModelAndView("redirect:/admin/categories", "message",
	 * "Category does not exist!"); }
	 */
	
	@PostMapping("/save")
	public ModelAndView saveOrUpdate(
	        @Valid @ModelAttribute("category") CategoryModel cateModel,
	        BindingResult result,
	        RedirectAttributes redirectAttributes) {
		 if(result.hasErrors()) {
		        return new ModelAndView("admin/home", "category", cateModel);
		    }
		    Category entity = new Category();
		    BeanUtils.copyProperties(cateModel, entity);
		    if(!cateModel.getRts_images1().isEmpty()) {
		    	//lưu file vào trường poster
		    	UUID uuid = UUID.randomUUID();
		    	String uuString = uuid.toString();
		    	entity.setImage (storageService.getSorageFilename(cateModel.getRts_images1(), uuString));
		    	storageService.store(cateModel.getRts_images1(), entity.getImage());
		    	}
		    
		
		    categoryService.save(entity);
		    redirectAttributes.addFlashAttribute("message", "Category saved successfully!");
		    return new ModelAndView("redirect:/admin/categories");
	}
	



		                                      
	
	
	/*
	 * @PostMapping("/save") public String update(@Valid Category category,
	 * BindingResult result, Model model) { if (result.hasErrors()) { return
	 * "admin/home"; } categoryService.save(category); return
	 * "redirect:/admin/categories"; }
	 */
}
