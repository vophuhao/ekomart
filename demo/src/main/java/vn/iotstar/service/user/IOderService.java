package vn.iotstar.service.user;

import java.util.Optional;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;

public interface IOderService {

	

	Optional<Orders> findByOderId(String id);


	Orders save(Orders oders);

}
