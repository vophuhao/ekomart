package vn.iotstar.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class productPayment {

	 private List<SelectedProduct> selectedProduct=new ArrayList<>();

	
	 public static class SelectedProduct {
	 private String productImage;
	 private String productId;
     private String productName;
     private Double productPrice;
     private Integer productQuantity;
     private Boolean productSelected;
     @Override
     public String toString() {
         return "SelectedProduct{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", productSelected=" + productSelected +
                '}';
     }
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Boolean getProductSelected() {
		return productSelected;
	}
	public void setProductSelected(Boolean productSelected) {
		this.productSelected = productSelected;
	}
	 }
	 
}
