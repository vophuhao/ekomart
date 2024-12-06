package vn.iotstar.service.user;

import java.util.Optional;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.UserInfo;

public interface IUserService {

	Optional<UserInfo> findById(Integer id);
	Optional<UserInfo> findByName(String username);
	<S extends UserInfo> S save(S entity);
}
