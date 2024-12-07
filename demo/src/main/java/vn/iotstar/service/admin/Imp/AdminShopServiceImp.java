package vn.iotstar.service.admin.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.admin.AdminShopService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminShopServiceImp implements AdminShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<Shop> findByDisplay(int display) {
        return shopRepository.findByDisplay(display);
    }

    @Override
    public Optional<Shop> findById(Long shopId) {
        return shopRepository.findById(shopId);
    }

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public <S extends Shop> S save(S entity) {
        return shopRepository.save(entity);
    }

    @Override
    public Optional<Shop> findByUserId(int userId) {
        return shopRepository.findByUserId(userId);
    }

	@Override
	public List<Shop> findByStatus(int status) {
		
		return shopRepository.findByStatus(status);
	}

	@Override
	public Optional<Shop> findByShopId(String shopId) {
		
		return shopRepository.findByShopId(shopId);
	}

}
