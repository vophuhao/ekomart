package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.DTO.CategoryDTO;
import vn.iotstar.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	 Optional<Category> findByCategoryId(String categoryId);
	 
	 List<Category> findByStatus(int status);
	 
	  @Query("SELECT new vn.iotstar.DTO.CategoryDTO(c.id, c.categoryId, c.categoryName, c.image, c.status) FROM Category c")
	    List<CategoryDTO> findAllCategoryDTO();
}
