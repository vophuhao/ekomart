package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;

public interface IOderService {

	List<Orders> findByShopIdAndStatus(Long shopId, int status);

	Optional<Orders> findByOderId(String id);


	Orders save(Orders oders);

	List<Orders> findAll();

}
