package vn.iotstar.service.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.repository.OderRepository;
import vn.iotstar.repository.OrderDetailRepository;
import vn.iotstar.service.IOderDetailService;

@Service
public class OderDetailServiceImpl implements IOderDetailService {

	@Autowired
	OrderDetailRepository oderrepo;
	
	@Override
    public OrderDetail save(OrderDetail oders) {
        return oderrepo.save(oders);
    }

	@Override
	public List<OrderDetail> findByOrders(Orders orders) {
		return oderrepo.findByOrders(orders);
	}

    
}
