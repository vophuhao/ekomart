package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;



@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long>{

	@SuppressWarnings("unchecked")
	OrderDetail save(OrderDetail oders);
	Optional<OrderDetail> findById(Long id);
	
	List<OrderDetail> findByOrders(Orders orders);
}
