package vn.iotstar.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;

import java.util.List;




public interface AddressRepository extends JpaRepository<Address, Long> {
	 List<Address> findByUser(UserInfo user);
	


	
}
