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
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.AddresModel;
import vn.iotstar.service.user.ILocationService;
import vn.iotstar.service.user.Imp.AddressServiceImp;
import vn.iotstar.service.user.Imp.CartServiceImpl;
import vn.iotstar.service.user.Imp.UserInfoServiceImp;

@RestController
public class AddressController {

	@Autowired
    private CartServiceImpl cartService;
    
	@Autowired
	private UserInfoServiceImp userservice;
	
	@Autowired
	private AddressServiceImp addre;
	
	@Autowired
	private ILocationService location;
	
	@PostMapping("/user/api/address/save")
	public ResponseEntity<Void> saveAddress(@RequestBody AddresModel address, Model model) {
	    try {
	        // Lấy tên tỉnh, quận, xã từ mã code
//	        String provinceName = location.getProvinceNameByCode(Integer.parseInt(address.getProvince()));
//	        String districtName = location.getDistrictNameByCode(Integer.parseInt(address.getDistrict()));
//	        String wardName = location.getWardNameByCode(Integer.parseInt(address.getWard()));

	        // Kiểm tra nếu không tìm được tên
//	        if (provinceName == null || districtName == null || wardName == null) {
//	            return ResponseEntity.badRequest().build(); // Trả về lỗi 400 nếu mã không hợp lệ
//	        }

	        // Tạo địa chỉ mới
	        Address newAddress = new Address();
	        newAddress.setUname(address.getUname());
	        newAddress.setPhone(address.getPhone());
	        newAddress.setProvince(address.getProvince()); // Lưu tên tỉnh
	        newAddress.setDistrict(address.getDistrict()); // Lưu tên quận
	        newAddress.setWard(address.getWard());         // Lưu tên xã
	        newAddress.setDetail(address.getDetail());
	        newAddress.setDefaults(1);

	        // Lấy thông tin user (giả sử ID user là 1)
	        Optional<UserInfo> user2 = userservice.findById(1);
	        UserInfo entity = user2.get();
	        if (user2.isPresent()) {
	            BeanUtils.copyProperties(user2, entity);
	        }

	        newAddress.setUser(entity);
	        addre.save(newAddress); // Lưu vào cơ sở dữ liệu

	        // Trả về mã 200 OK với thông báo thành công
	        return ResponseEntity.ok().build(); // 200 OK mà không có nội dung trả về

	    } catch (Exception e) {
	        // Nếu có lỗi xảy ra, trả về mã lỗi 500 và thông báo lỗi
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
