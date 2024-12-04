package vn.iotstar.service.user;


import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Address;

public interface IAddressService {
	Address save(Address address);
	List<Address> findByUser_Id(Long userId);
	
	Optional<Address> findById(Long id);
	void delete(Long id);
}
