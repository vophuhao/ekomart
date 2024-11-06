package vn.iotstar.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private Long id;
   
    private String categoryId;
    private String categoryName;
    private int status;

	 List<Product> products;
	 
	 private Boolean isEdit = false;
	 

}
