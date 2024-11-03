package vn.iotstar.repository.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;

@Repository
public interface AdminCategoryRepository extends JpaRepository<Category, Long> {

	 Optional<Category> findByCategoryId(String categoryId);
	 
}

	

