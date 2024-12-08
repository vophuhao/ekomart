package vn.iotstar.repository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Orders;
import vn.iotstar.entity.UserInfo;


@Repository
public interface OderRepository extends JpaRepository<Orders, Long> {

	@SuppressWarnings("unchecked")
	Orders save(OrderDetail orderDetail);
	Optional<Orders> findByOderId(String oderId);
	List<Orders> findByShopIdAndStatus(Long shopId, int status);
	
	@Query("SELECT SUM(o.totalPay) FROM Orders o WHERE o.date BETWEEN :startDate AND :endDate AND o.status = 3 AND o.shop.id = :shopId")
    BigDecimal calculateTotalRevenue(@Param("shopId") Long shopId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
	@Query("SELECT SUM(oi.quantity) FROM OrderDetail oi WHERE oi.orders.shop.id = :shopId " +
	           "AND oi.orders.status = 3 AND oi.orders.date BETWEEN :d1 AND :d2")
	BigDecimal sumProductQuantityInOrder(@Param("shopId") long shopId,  
	                                         @Param("d1") LocalDateTime d1, 
	                                         @Param("d2") LocalDateTime d2);
	
	@Query("SELECT COUNT(o) FROM Orders o WHERE o.shop.id = :shopId AND o.status = 3 AND o.date BETWEEN :d1 AND :d2")
    BigDecimal countOrdersByShopId(@Param("shopId") long shopId, @Param("d1") LocalDateTime d1, 
            @Param("d2") LocalDateTime d2);
	List<Orders> findByUser(UserInfo userInfo);
}
