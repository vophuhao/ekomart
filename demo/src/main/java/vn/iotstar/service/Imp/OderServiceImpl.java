package vn.iotstar.service.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Orders;
import vn.iotstar.repository.OderRepository;
import vn.iotstar.service.IOderService;

@Service
public class OderServiceImpl implements IOderService {

	@Autowired
	OderRepository oderrepo;
	
	@Override
    public Orders save(Orders oders) {
        return oderrepo.save(oders);
    }

    @Override
    public Optional<Orders> findByOderId(String id) {
        return Optional.ofNullable(oderrepo.findByOderId(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id)));
    }
    
    @Override
    public List<Orders>findByShopIdAndStatus(Long shopId, int status)
    {
    	return oderrepo.findByShopIdAndStatus(shopId, status);
    }
    
    @Override
	public List<Orders> findAll() {
    	return oderrepo.findAll();
    }
}
