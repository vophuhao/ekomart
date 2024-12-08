package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.model.CategoryModel;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product")

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    private String productId;
    private String name;
    private String description;
    private double price;  
    private int count;
    private int sold;
    private int status;
    private int display;
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", sold=" + sold +
                ", status=" + status +
                ", display=" + display +
                ", date=" + date +
                ", image='" + image + '\'' +
                '}';
    }

    @PrePersist
    public void onCreate() {
        if (date == null) {
            date = LocalDateTime.now(); // Gán ngày giờ hiện tại khi tạo mới
        }
    }
    
	@ManyToOne  
	@JoinColumn(name = "categoryId") 
	@JsonIgnore
	private Category category;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    private String image;
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProductImage> images;
    
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//    private List<OrderDetail> orderDetails;
//
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews;

//    @ManyToMany(fetch = FetchType.LAZY)
    /*private List<User> likedByUsers;*/

}
