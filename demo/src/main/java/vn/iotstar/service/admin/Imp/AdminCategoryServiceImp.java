package vn.iotstar.service.admin.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.admin.AdminICategoryService;

@Service

public class AdminCategoryServiceImp implements AdminICategoryService{
	@Autowired 
	CategoryRepository categoryRepository;

	public AdminCategoryServiceImp(CategoryRepository categoryRepository) {
	
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Optional<Category> findByCategoryId(String categoryId) {
		return categoryRepository.findByCategoryId(categoryId);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category getById(Long id) {
		return categoryRepository.getById(id);
	}

	
}
