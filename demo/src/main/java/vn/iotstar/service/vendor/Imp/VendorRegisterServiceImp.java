package vn.iotstar.service.vendor.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.vendor.VendorIRegisterService;

@Service
public class VendorRegisterServiceImp implements VendorIRegisterService {

	@Autowired
	ShopRepository vendorShopRepository;

	@Transactional
	@Override
	public <S extends Shop> S save(S entity) {
		return vendorShopRepository.save(entity);
	}
	
	
}
