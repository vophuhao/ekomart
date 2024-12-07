package vn.iotstar.service.user;


import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;

public interface IAddressService {
	Address save(Address address);
	public List<Address> findByUser(UserInfo user);
	Optional<Address> findByDefaults(int defaults);
	Optional<Address> findById(Long id);
	Optional<Address> findByUserAndDefaults(UserInfo user, int defaults);
	void delete(Long id);
}
