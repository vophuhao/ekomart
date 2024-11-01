//package vn.iotstar.entity;
//
//import java.io.Serializable;
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Shop implements Serializable{
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
//    @Column(unique = true)
//    private String shopId;
//    
//    private String name;
//    private String avatar;
//    private String description;
//    private String sdt;
//    private String email;
//
//    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
//    private List<AddressShop> addresses;
//
//	/*
//	 * @OneToOne
//	 * 
//	 * @JoinColumn(name = "owner_id")
//	 */
//	/* private User owner; */
//
//    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Product> products;
//
//    @OneToMany(mappedBy = "oderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Order> orders;
//
//
//
//}
