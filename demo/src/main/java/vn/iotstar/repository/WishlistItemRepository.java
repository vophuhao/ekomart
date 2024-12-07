package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long>{
	void deleteById(Long id);
}
