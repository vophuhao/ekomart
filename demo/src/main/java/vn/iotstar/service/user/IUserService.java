package vn.iotstar.service.user;

import java.util.Optional;

import vn.iotstar.entity.UserInfo;

public interface IUserService {

	Optional<UserInfo> findById(Integer id);

}
