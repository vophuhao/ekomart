//package vn.iotstar.entity;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Review implements Serializable {
//
//	
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private int rating; // rating out of 5
//    private String comment;
//    private LocalDate reviewDate;
//
//	/*
//	 * @ManyToOne
//	 * 
//	 * @JoinColumn(name = "user_id") private User user;
//	 */
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
// 
//}
