package vn.iotstar.controller.vendor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.VendorModel;
import vn.iotstar.service.IStorageService;
import vn.iotstar.service.admin.AdminShopService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.util.JwtUtil;

import java.util.Optional;

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

}
