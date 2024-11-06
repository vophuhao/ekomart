package vn.iotstar.service.user.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.UserInfoRepository;
import vn.iotstar.service.user.IUserService;



@Service
public class UserInfoServiceImp implements IUserService{

	@Autowired
	UserInfoRepository userRepository;

	@Override
	public Optional<UserInfo> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	

	
}
