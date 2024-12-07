package vn.iotstar.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;


@Repository
public interface OderRepository extends JpaRepository<Orders, Long> {

	@SuppressWarnings("unchecked")
	Orders save(OrderDetail orderDetail);
	Optional<Orders> findByOderId(String oderId);
	List<Orders> findByShopIdAndStatus(Long shopId, int status);
}
