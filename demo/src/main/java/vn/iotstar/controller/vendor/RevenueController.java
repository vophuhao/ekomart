package vn.iotstar.controller.vendor;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.UserService;
import vn.iotstar.service.vendor.RevenueService;
import vn.iotstar.util.JwtUtil;

@Controller
@RequestMapping("/vendor")
public class RevenueController {

	@Autowired
    private RevenueService revenueService;

	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/revenue")
    public String getRevenue(Model model, HttpServletRequest request) {
		String token = null;
		// Lấy cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        String username = jwtUtil.extractUsername(token);
        UserInfo user = userService.findByUsername(username);
		Shop shop = shopRepository.findByUserId(user.getId()).orElse(null);
		
		LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startOfCurrentMonth = currentMonth.atDay(1); // bat dau thang hien tai
        LocalDate endOfCurrentMonth = currentMonth.atEndOfMonth(); // ket thuc thang hien tai
        // Chuyển đổi LocalDate thành LocalDateTime (giả sử là bắt đầu hoặc kết thúc ngày)
        LocalDateTime startOfCurrentMonthTime = startOfCurrentMonth.atStartOfDay();
        LocalDateTime endOfCurrentMonthTime = endOfCurrentMonth.atTime(23, 59, 59); // Cuối ngày
        YearMonth previousMonth = YearMonth.from(now).minusMonths(1);
        LocalDate startOfPreviousMonth = previousMonth.atDay(1);
        LocalDate endOfPreviousMonth = previousMonth.atEndOfMonth();
        // Chuyển đổi LocalDate thành LocalDateTime
        LocalDateTime startOfPreviousMonthTime = startOfPreviousMonth.atStartOfDay();
        LocalDateTime endOfPreviousMonthTime = endOfPreviousMonth.atTime(23, 59, 59);
        
        BigDecimal totalPro = revenueService.totalProducts(shop.getId(), startOfCurrentMonthTime, endOfCurrentMonthTime);
        BigDecimal totalPro2 = revenueService.totalProducts(shop.getId(), startOfPreviousMonthTime, endOfPreviousMonthTime);
        BigDecimal percentPro = BigDecimal.ZERO;
        if (totalPro != null && totalPro2 != null) {
        	 percentPro = revenueService.calculateRevenueChangePercentage(shop.getId(), totalPro, totalPro2);
        }
        // Tính phần trăm thay đổi doanh thu
        BigDecimal r1 = revenueService.currentMonthRevenue(shop.getId());
        BigDecimal r2 = revenueService.previousMonthRevenue(shop.getId());
        BigDecimal revenueChangePercentage = BigDecimal.ZERO;
        if (r1 != null && r2 != null) {
        	revenueChangePercentage = revenueService.calculateRevenueChangePercentage(shop.getId(), r1, r2);
        }
        
        BigDecimal currentMonthRe = revenueService.currentMonthRevenue(shop.getId());
        List<Product> topProducts = revenueService.getTopSellingProducts(shop.getId(), 5);
        
        BigDecimal totalOrders = revenueService.getTotalOrdersByShop(shop.getId(), startOfCurrentMonthTime, endOfCurrentMonthTime);
        BigDecimal totalOrders2 = revenueService.getTotalOrdersByShop(shop.getId(), startOfPreviousMonthTime, endOfPreviousMonthTime);
        BigDecimal percentOr = BigDecimal.ZERO;
        if (totalOrders != null && totalOrders2 != null) {
        	percentOr = revenueService.calculateRevenueChangePercentage(shop.getId(), totalOrders, totalOrders2);
        }
        
        
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("percentOrder", percentOr);
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("totalProduct", totalPro);
        model.addAttribute("percentProduct", percentPro);
        model.addAttribute("currentMonth", currentMonthRe);
        model.addAttribute("revenueChangePercentage", revenueChangePercentage);

        return "vendor/revenue1";
    }
}
