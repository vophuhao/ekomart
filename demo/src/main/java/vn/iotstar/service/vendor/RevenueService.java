package vn.iotstar.service.vendor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.OderRepository;
import vn.iotstar.repository.OrderDetailRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ShopRepository;

@Service
public class RevenueService {
	
	@Autowired
	OderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	// Tính tổng doanh thu của tháng hiện tại
    public BigDecimal currentMonthRevenue(Long shopId) {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startOfCurrentMonth = currentMonth.atDay(1);
        LocalDate endOfCurrentMonth = currentMonth.atEndOfMonth();

        // Chuyển đổi LocalDate thành LocalDateTime (giả sử là bắt đầu hoặc kết thúc ngày)
        LocalDateTime startOfCurrentMonthTime = startOfCurrentMonth.atStartOfDay();
        LocalDateTime endOfCurrentMonthTime = endOfCurrentMonth.atTime(23, 59, 59); // Cuối ngày

        BigDecimal currentMonthRevenue = orderRepository.calculateTotalRevenue(shopId, startOfCurrentMonthTime, endOfCurrentMonthTime);
        return currentMonthRevenue != null ? currentMonthRevenue : BigDecimal.ZERO;
    }

    // Tính tổng doanh thu của tháng trước
    public BigDecimal previousMonthRevenue(Long shopId) {
        LocalDate now = LocalDate.now();
        YearMonth previousMonth = YearMonth.from(now).minusMonths(1);
        LocalDate startOfPreviousMonth = previousMonth.atDay(1);
        LocalDate endOfPreviousMonth = previousMonth.atEndOfMonth();

        // Chuyển đổi LocalDate thành LocalDateTime
        LocalDateTime startOfPreviousMonthTime = startOfPreviousMonth.atStartOfDay();
        LocalDateTime endOfPreviousMonthTime = endOfPreviousMonth.atTime(23, 59, 59);

        BigDecimal previousMonthRevenue = orderRepository.calculateTotalRevenue(shopId, startOfPreviousMonthTime, endOfPreviousMonthTime);
        return previousMonthRevenue != null ? previousMonthRevenue : BigDecimal.ZERO;
    }

    /**
     * Hàm tính tổng doanh thu của tháng hiện tại và so sánh với tháng trước.
     * 
     * @return Phần trăm thay đổi doanh thu giữa tháng hiện tại và tháng trước.
     */
    public BigDecimal calculateRevenueChangePercentage(Long shopId, BigDecimal currentMonthRevenue, BigDecimal previousMonthRevenue) {
        // So sánh phần trăm thay đổi
        if ((previousMonthRevenue.compareTo(BigDecimal.ZERO) == 0)) {
            // Nếu doanh thu tháng trước bằng 0, trả về 100% (tăng trưởng tuyệt đối) nếu tháng hiện tại có doanh thu
            return currentMonthRevenue.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(100);
        }

        // Phần trăm thay đổi = (Doanh thu tháng hiện tại - Doanh thu tháng trước) / Doanh thu tháng trước * 100
        return currentMonthRevenue.subtract(previousMonthRevenue)
                                  .divide(previousMonthRevenue, 2, RoundingMode.HALF_UP)
                                  .multiply(BigDecimal.valueOf(100));
    }
    
    public BigDecimal totalProducts(long shopId, LocalDateTime d1, LocalDateTime d2) {
        // Tính tổng số sản phẩm bán được trong khoảng thời gian d1 đến d2 cho cửa hàng shopId và đơn hàng orderId
        return orderRepository.sumProductQuantityInOrder(shopId, d1, d2);
    }
    
    public List<Product> getTopSellingProducts(long shopId, int limit) {
        Pageable pageable = PageRequest.of(0, limit); // Lấy số lượng giới hạn
        return productRepository.findTopSellingProductsByShopId(shopId, pageable);
    }
    
    public BigDecimal getTotalOrdersByShop(long shopId, LocalDateTime d1, LocalDateTime d2) {
        return orderRepository.countOrdersByShopId(shopId, d1, d2);
    }
}
