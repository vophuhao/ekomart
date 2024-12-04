package vn.iotstar.service.user;


import java.util.Optional;

import vn.iotstar.entity.Address;

public interface IAddressService {
	Address save(Address address);
	Optional<Address> findByUser_Id(Long userId);
}
