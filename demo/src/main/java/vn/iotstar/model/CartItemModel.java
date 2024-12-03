package vn.iotstar.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long cartId;
    private Long productId;
    private int quantity;
    private int status;
    private double price;
	 

}