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
	public ResponseEntity<String> deleteAddress(@RequestBody String id) {
		 try {
			 System.out.print(id);
		        Long addressId = Long.parseLong(id);
		       		       
		        // Nếu điều kiện không cho phép xóa
		        if (!canDeleteAddress(addressId)) { // Thay 'canDeleteAddress' bằng điều kiện của bạn
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể xóa địa chỉ này.");
		        }
		        
		        // Thực hiện xóa nếu hợp lệ
		        addre.delete(addressId);
		        return ResponseEntity.ok("Xóa địa chỉ thành công!");
		    } catch (NumberFormatException e) {
		        return ResponseEntity.badRequest().body("ID không hợp lệ.");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xảy ra khi xóa địa chỉ.");
		    }
	}
	private boolean canDeleteAddress(Long addressId) {
	   
		Optional<Address> add=addre.findById(addressId);
		Address address=add.get();
		System.out.print(address.getDefaults());
		if(address.getDefaults()==1)
		{
			  return false; 
		}
	  
	    else
	    {
	    	return true; 
	    }
	}
	@PostMapping("/user/api/address/default")
	public ResponseEntity<Void> setAddressDefault(HttpServletRequest request,@RequestBody  Long addressId) {
		
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
	    	
			
			 Optional<Address>addressde=addre.findByUserAndDefaults(userss, 1);
			 if(addressde.isPresent())
			 {
				 Address add=addressde.get();
				 add=addressde.get();
				 add.setDefaults(0);	 
				 addre.save(add);
			 }
			
			     	
		       Optional<Address>address=addre.findById(addressId);
		       
		       Address addresss=address.get();
		       System.out.print(addresss.getDetail());
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
