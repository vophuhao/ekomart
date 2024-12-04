package vn.iotstar.service.vendor.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.AddressShop;
import vn.iotstar.repository.AddressShopRepository;
import vn.iotstar.service.vendor.VendorIAddressShop;

@Service
public class VendorAddressShopImp implements VendorIAddressShop {
	
	@Autowired
	AddressShopRepository addRepository;

	@Override
	public <S extends AddressShop> S save(S entity) {
		return addRepository.save(entity);
	}
	
	
}
