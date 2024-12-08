package vn.iotstar.service.admin.Imp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.iotstar.DTO.CategoryDTO;
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

	@SuppressWarnings("deprecation")
	@Override
	public <S extends Category> S save(S entity) {
		if(entity.getId() == null) {
			
			return categoryRepository.save(entity);
			}
		else {
			
			if(entity.getId()!=null)
			{
				Optional<Category> opt=findById(entity.getId());
				if(opt.isPresent()) {
					if(StringUtils.isEmpty(entity.getImage())) { 
						
						entity.setImage(opt.get().getImage());
				}else {
					entity.setImage(entity.getImage());
				}
			}
		}
		
			return categoryRepository.save(entity);
		}
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

	@Override
	public List<CategoryDTO> findAllCategoryDTO() {
		return categoryRepository.findAllCategoryDTO();
	}

	@Override
	public List<CategoryDTO> findAllCate() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
