//package vn.iotstar.entity;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.apache.catalina.User;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
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
//public class Order implements Serializable{/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private String oderId;
//    private LocalDate orderDate;
//    private String status; // New, Confirmed, Shipping, Delivered, Canceled, Returned
//    private int totalPay;
//
//	/*
//	 * @ManyToOne
//	 * 
//	 * @JoinColumn(name = "user_id") private User user;
//	 */
//    @ManyToOne
//    @JoinColumn(name = "shop_id")
//    private Shop shop;
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<OrderDetail> orderDetails;
//
//    @OneToOne
//    @JoinColumn(name = "admin_discount_code")
//    private DiscountAdmin discountAdmin;
//
//    @OneToOne
//    @JoinColumn(name = "shop_discount_code")
//    private DiscountShop discountShop;
//
//    @ManyToOne
//    @JoinColumn(name = "address_id")
//    private Address userAddress;
//
//
//}
