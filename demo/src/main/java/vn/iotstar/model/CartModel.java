package vn.iotstar.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	 
	private List<CartItemModel> items;
	 

}