package vn.iotstar.model;



import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class productModel {
	
	   private Long id;
	    private String name;
	    private String description;
	    private double price;
	    
	    private int count;
	    private String image;
	    private MultipartFile rts_images1;
	    private Long categoryId;
		private Category category;
}
