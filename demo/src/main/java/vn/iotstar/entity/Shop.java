package vn.iotstar.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String shopId;
    
    private String name;
    private String avatar;
    private String description;
    private String sdt;
    private String email;
    private int status;
    private int display;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private AddressShop address;
    
    @OneToOne( cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "identifi_id")
    @JsonIgnore
    private IdentificationInfo info;

    @OneToOne( cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
	private UserInfo user;


    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", display=" + display +
                '}';
    }

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

//    @OneToMany(mappedBy = "oderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Order> orders;



}
