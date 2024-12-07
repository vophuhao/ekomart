package vn.iotstar.controller.rest;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Convert;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.AddresModel;
import vn.iotstar.service.user.ILocationService;
import vn.iotstar.service.user.IUserService;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;
import vn.iotstar.util.JwtUtil;

@RestController
public class AddressController {

	@Autowired
    private CartServiceImpl cartService;
    
	@Autowired
	private UserInfoServiceImp userservice;
	
	@Autowired
	private AddressServiceImp addre;
	
	 @Autowired
	 private IUserService userService;
	@Autowired
	private ILocationService location;
	
	@Autowired
    private JwtUtil jwtUtil;
	@PostMapping("/user/api/address/delete")
	public ResponseEntity<Void> deleteAddress(@RequestBody String id) {
	    try {
	        // Chuyển đổi id từ String sang Long
	        Long addressId = Long.parseLong(id);

	        // Giả sử phương thức xóa địa chỉ của bạn là addre.delete(addressId)
	        addre.delete(addressId);

	       
	            return ResponseEntity.ok().build();
	        
	        
	    } catch (Exception e) {
	        // Trả về mã lỗi 500 khi có lỗi xảy ra
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@PostMapping("/user/api/address/default")
	public ResponseEntity<Void> setAddressDefault(@RequestParam("addressId")  Long addressId) {
	    try {
	        // Chuyển đổi id từ String sang Long
	       
	    	 Optional<Address>addressde=addre.findByDefaults(1);
	    	 Address addressd=addressde.get();
	    	 addressd.setDefaults(0);
	    	 addre.save(addressd);
	    	
	       Optional<Address>address=addre.findById(addressId);
	       Address addresss=address.get();
	       addresss.setDefaults(1);
	       addre.save(addresss);
	    
	       
	       
	            return ResponseEntity.ok().build();
	        
	        
	    } catch (Exception e) {
	        // Trả về mã lỗi 500 khi có lỗi xảy ra
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	@PostMapping("/user/api/address/save")
	public ResponseEntity<Void> saveAddress(@RequestBody AddresModel address, Model model,HttpServletRequest request) {
	    try {
	    	
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
	        Optional<UserInfo> users=userService.findByName(username);
	        UserInfo userss =new UserInfo();
	        if (users.isPresent()) {
	            userss = users.get();
	            // Xử lý logic với user
	        } 
	        Address newAddress = new Address();
	        newAddress.setUname(address.getUname());
	        newAddress.setPhone(address.getPhone());
	        newAddress.setProvince(address.getProvince()); // Lưu tên tỉnh
	        newAddress.setDistrict(address.getDistrict()); // Lưu tên quận
	        newAddress.setWard(address.getWard());         // Lưu tên xã
	        newAddress.setDetail(address.getDetail());
	        newAddress.setDefaults(0);	     	       	 	        
	        newAddress.setUser(userss);
	        addre.save(newAddress); // Lưu vào cơ sở dữ liệu

	        // Trả về mã 200 OK với thông báo thành công
	        return ResponseEntity.ok().build(); // 200 OK mà không có nội dung trả về

	    } catch (Exception e) {
	        // Nếu có lỗi xảy ra, trả về mã lỗi 500 và thông báo lỗi
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
