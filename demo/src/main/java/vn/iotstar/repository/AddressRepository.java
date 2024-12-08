package vn.iotstar.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;

import java.util.List;
import java.util.Optional;




public interface AddressRepository extends JpaRepository<Address, Long> {
	 List<Address> findByUser(UserInfo user);
	
	 Optional<Address> findByDefaults(int defaults);

	 Optional<Address> findByUserAndDefaults(UserInfo user, int defaults);
}
