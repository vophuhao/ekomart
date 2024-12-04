package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.AddressShop;

@Repository
public interface AddressShopRepository extends JpaRepository<AddressShop, Long>{
 
}
