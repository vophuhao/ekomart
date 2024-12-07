package vn.iotstar.model;

import java.io.Serializable;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
   private Long id;
    private String categoryId;
    private String categoryName;
    private int status;
    private String image;
	private MultipartFile rts_images1;
	 
	 private Boolean isEdit = false;
	 

}
