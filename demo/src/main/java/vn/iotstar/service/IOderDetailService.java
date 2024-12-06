package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;

public interface IOderDetailService {

	OrderDetail save(OrderDetail oders);
	List<OrderDetail> findByOrders(Orders orders);
}
