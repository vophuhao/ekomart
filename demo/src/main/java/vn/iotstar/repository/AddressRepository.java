package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Address;
import java.util.List;




public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByUser_Id(Long userId);
	
}
