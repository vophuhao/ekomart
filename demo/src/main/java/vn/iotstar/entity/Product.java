package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product")

public class Product implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    private String productId;
    private String name;
    private String description;
    private double price;
    private int life; // in months
    private int count;
    private int sold;
    private int status;
    private int display;
    
    private LocalDate date;

	
	  @ManyToOne  
	  @JoinColumn(name = "categoryId") 
	  private Category category;


//    @ManyToOne
//    @JoinColumn(name = "shop_id")
//    private Shop shop;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProductImage> images;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<OrderDetail> orderDetails;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<Review> reviews;

//    @ManyToMany(fetch = FetchType.LAZY)
    /*private List<User> likedByUsers;*/

}
