package vn.iotstar.controller.vendor;

import java.security.SecureRandom;
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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.model.productModel;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminICategoryService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.vendor.VendorIProductService;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/vendor/products") // id goc cua shop
public class VendorProductController {

	@Autowired
	VendorIProductService productService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	AdminICategoryService categoryService;

	@Autowired
	private ShopRepository shoprepo;

	@Autowired
	IStorageService storageService;

	@Autowired
	private IUserService userService;

	@GetMapping("")
	public String listProduct(@PathVariable("id") Long id, ModelMap model,
			@RequestParam(value = "name", required = false) String productName,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "display", required = false) Integer display) {
		List<Product> list = productService.getProductsByShopId(id);
		model.addAttribute("products", list);

		if (productName != null) {
			List<Product> listByName = productService.findByName(productName, id);
			model.addAttribute("listByName", listByName);
		}

		if (status != null) {
			List<Product> listByStatus = productService.findByStatus(status, id);
			model.addAttribute("listByStatus", listByStatus);
		}

		if (display != null) {
			List<Product> listByDisplay = productService.findByDisplay(display, id);
			model.addAttribute("listByDisplay", listByDisplay);
		}

		return "vendor/product-list";
	}

	@GetMapping("/add")
	public String addProduct(Model model) {

		List<Category> categoryList = categoryService.findAll();
		model.addAttribute("cateList", categoryList);

		return "vendor/product-add";
	}

	public String ShopId(HttpServletRequest request, HttpSession session) {
		String shopId = (String) session.getAttribute("shopId");
		return shopId;
	}

	@GetMapping("/list/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/list")
	public String listProduct(HttpServletRequest request, HttpSession session, Model model) {
		String token = null;
		// Lấy cookie từ request
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JWT".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		String username = jwtUtil.extractUsername(token);
		Optional<UserInfo> users = userService.findByName(username);
		UserInfo userss = new UserInfo();
		if (users.isPresent()) {
			userss = users.get();
			// Xử lý logic với user
		}
		Optional<Shop> shop = shoprepo.findByUserId(userss.getId());
		Shop shopp = new Shop();
		if (shop.isPresent()) {
			shopp = shop.get();
			// Xử lý logic với user
		}

		List<Product> product = productService.findByShop(shopp);

		model.addAttribute("listProduct", product);
		return "vendor/product-list";
	}

	public String generateRandomString() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom(); // Sử dụng SecureRandom để tăng cường bảo mật khi tạo số ngẫu nhiên

		// Bắt đầu với ba ký tự cố định "EKP"
		StringBuilder result = new StringBuilder("EKP");

		// Thêm 4 ký tự ngẫu nhiên vào sau
		for (int i = 0; i < 4; i++) {
			int randomIndex = random.nextInt(chars.length());
			result.append(chars.charAt(randomIndex));
		}

		return result.toString();
	}

	@PostMapping("/add")
	public ModelAndView addNewProduct(@Valid @ModelAttribute("products") productModel productModel,
			HttpServletRequest request, BindingResult result, RedirectAttributes redirectAttributes,
			HttpSession session) {
		String token = null;
		// Lấy cookie từ request
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JWT".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		String username = jwtUtil.extractUsername(token);
		Optional<UserInfo> users = userService.findByName(username);
		UserInfo userss = new UserInfo();
		if (users.isPresent()) {
			userss = users.get();
			// Xử lý logic với user
		}
		Optional<Shop> shop = shoprepo.findByUserId(userss.getId());
		Shop shopp = new Shop();
		if (shop.isPresent()) {
			shopp = shop.get();
			// Xử lý logic với user
		}
		if (result.hasErrors()) {
			return new ModelAndView("vendor/product-list", "products", productModel);
		}
		Category cate = new Category();
		cate = categoryService.getById(productModel.getCategoryId());

		String prId = generateRandomString();
		Product entity = new Product();
		BeanUtils.copyProperties(productModel, entity);
		entity.setCategory(cate);
		entity.setStatus(0);
		entity.setDisplay(0);
		entity.setShop(null);
		entity.setShop(shopp);
		entity.setProductId(prId);
		if (!productModel.getRts_images1().isEmpty()) {
			// lưu file vào trường poster
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			entity.setImage(storageService.getSorageFilename(productModel.getRts_images1(), uuString));
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

	@PostMapping("/update")
	public String update(@Valid productModel product, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "vendor/home";
		}
		Optional<Product> existProduct = productService.findById(product.getId());
		if(existProduct.isPresent()) {
			Product product1 = existProduct.get();
			product1.setName(product.getName());
			product1.setDescription(product.getDescription());
			product1.setPrice(product.getPrice());
			product1.setCount(product.getCount());
			Optional<Category> cate = categoryService.findById(product.getCategoryId());
			if(cate.isPresent()) {
				product1.setCategory(cate.get());
			}
			if (!product.getRts_images1().isEmpty()) {
				// lưu file vào trường poster
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				product1.setImage(storageService.getSorageFilename(product.getRts_images1(), uuString));
				storageService.store(product.getRts_images1(), product1.getImage());
			}
			productService.save(product1);
		}

		return "redirect:/vendor/products/list";
	}
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.getById(id);
		List<Category> categoryList = categoryService.findAll();
		model.addAttribute("cateList", categoryList);
		productModel productModel = new productModel();
		BeanUtils.copyProperties(product, productModel);
        model.addAttribute("product", productModel);
        return "vendor/product-edit";
    }
	@PostMapping("/displayProduct")
	public String updateProduct(@Valid Product product, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "vendor/home";
		}
		Optional<Product> existProduct = productService.findById(product.getId());
		if(existProduct.isPresent()) {
			Product product1 = existProduct.get();
			System.out.println(product1);
			if(product1.getDisplay() == 1)
			{
				product1.setDisplay(0);
			}
			else {
				product1.setDisplay(1);
			}
			productService.save(product1);
		}
		return "redirect:/vendor/products/list";
	}



}
