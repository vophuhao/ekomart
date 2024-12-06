package vn.iotstar.controller.vendor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.ShopModel;
import vn.iotstar.model.VendorModel;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.vendor.VendorIRegisterService;
import vn.iotstar.util.JwtUtil;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vendor/profile")
public class VendorProfileController {
    @Autowired
    private AdminShopService adminShopService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    IStorageService storageService;
    @Autowired
    private VendorIRegisterService vendorIRegisterService;

    @GetMapping("")
    public String profile(Model model , HttpServletRequest request) {
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
        Optional<UserInfo> user = userService.findByName(username);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            VendorModel vendorModel = new VendorModel();
            Shop shop = user.get().getShop();
            BeanUtils.copyProperties(shop, vendorModel);
            model.addAttribute("shop", vendorModel);
        }
        return "vendor/profile-setting";
    }
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename){

        Resource file =storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/edit")
    public String registerSuccess(@Valid @ModelAttribute("shop") ShopModel shopModel, HttpServletRequest request , BindingResult result) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "vendor/profile-setting";
        }
        // set mặc định là shop active
        Optional<Shop> existShop = adminShopService.findById(shopModel.getId());
        if(existShop.isPresent()) {
            Shop shop = existShop.get();
            shop.setName(shopModel.getName());
            shop.setEmail(shopModel.getEmail());
            shop.setSdt(shopModel.getSdt());
            shop.setDescription(shopModel.getDescription());
            shop.getAddress().setAddressSdt(shopModel.getAddress().getAddressSdt());
            shop.getAddress().setDetail(shopModel.getAddress().getDetail());
            shop.getAddress().setProvinceId(shopModel.getAddress().getProvinceId());
            shop.getAddress().setStreetId(shopModel.getAddress().getStreetId());
            shop.getAddress().setDistrictId(shopModel.getAddress().getDistrictId());
            shop.getAddress().setProvinceId(shopModel.getAddress().getProvinceId());
            if(!shopModel.getRts_images1().isEmpty()) {
                //lưu file vào trường poster
                UUID uuid_logo = UUID.randomUUID();
                String uuString_logo = uuid_logo.toString();
                shop.setAvatar(storageService.getSorageFilename(shopModel.getRts_images1(), uuString_logo));
                storageService.store(shopModel.getRts_images1(), shop.getAvatar());
            }
            vendorIRegisterService.save(shop);
        }
        return "redirect:/vendor/home";
    }

}
