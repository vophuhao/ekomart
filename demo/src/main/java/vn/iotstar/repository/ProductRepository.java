
package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.Wishlist;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	 Optional<Product> findByProductId(String productId);	
	 
	 Optional<Product> findByDisplay(int display);
	 
	 Page<Product> findAllByDisplay(int display, Pageable pageable);
	 
	 Page<Product> findAll(Pageable pageable);
	 
	 Page<Product> findByDisplayContaining(int display, Pageable pageable);
	 
	 List<Product> findByStatus(int status);
	 
	 Optional<Product> findByName(String productName);
	 
	 Page<Product> findByShop(Shop shop, Pageable pageable);
	 
	 List<Product> findByShop(Shop shop);
	 
	 @Query("SELECT p FROM Product p ORDER BY p.date DESC")
	 List<Product> findTop20ByOrderByDateDesc(Pageable pageable);
	 
	 @Query("SELECT p FROM Product p ORDER BY p.sold DESC")
	 List<Product> findTop20ByOrderBySoldDesc(Pageable pageable);
	 
	 @Query("SELECT p FROM Product p")
	 List<Product> findTop18Page(Pageable pageable);
	 
	 List<Product> findByCategory(Category category);
	 
//	 List<Object[]> findTop20ProductsByReviewCount(Pageable pageable);
	 
	 @Query("SELECT p " +
		       "FROM OrderDetail od " +
		       "JOIN od.product p " +
		       "JOIN od.orders o " +
		       "WHERE o.shop.id = :shopId AND o.status = 3" +
		       "GROUP BY p.id, p.productId, p.name, p.image, p.price " +
		       "ORDER BY SUM(od.quantity) DESC")
		List<Product> findTopSellingProductsByShopId(@Param("shopId") long shopId, Pageable pageable);
	 
	 @Query("SELECT p FROM Product p JOIN p.category c WHERE (p.name LIKE %:keyword% OR c.categoryName LIKE %:keyword%) AND p.display = 1")
	 Page<Product> findByNameOrCategoryNameContaining(@Param("keyword") String keyword, Pageable pageable);
}

