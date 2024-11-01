//package vn.iotstar.entity;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//
//
//public class DiscountShop implements Serializable{/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String discountCode;
//    
//    private int percentage;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private int count;
//
//    @OneToOne
//    @JoinColumn(name = "shop_id")
//    private Shop shop;
//
//
//
//}
