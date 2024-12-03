package vn.iotstar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

	  private Long id;
	    private String categoryId;
	    private String categoryName;
	    private String image;
	    private int status;
}
