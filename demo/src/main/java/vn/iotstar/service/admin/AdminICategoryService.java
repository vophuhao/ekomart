package vn.iotstar.service.admin;

import java.util.List;
import java.util.Optional;

import vn.iotstar.DTO.CategoryDTO;
import vn.iotstar.entity.Category;


public interface AdminICategoryService {

	Category getById(Long id);

	Optional<Category> findById(Long id);

	List<Category> findAll();

	<S extends Category> S save(S entity);

	Optional<Category> findByCategoryId(String categoryId);
	 List<CategoryDTO> findAllCategoryDTO();

	List<CategoryDTO> findAllCate();

}
