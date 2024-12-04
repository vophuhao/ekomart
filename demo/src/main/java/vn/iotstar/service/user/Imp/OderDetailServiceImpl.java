package vn.iotstar.service.user.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.repository.OderRepository;
import vn.iotstar.repository.OrderDetailRepository;
import vn.iotstar.service.user.IOderDetailService;

@Service
public class OderDetailServiceImpl implements IOderDetailService {

	@Autowired
	OrderDetailRepository oderrepo;
	
	@Override
    public OrderDetail save(OrderDetail oders) {
        return oderrepo.save(oders);
    }

    
}