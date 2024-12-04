package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String oderId;
    private LocalDate orderDate;
    private String status; // New, Confirmed, Shipping, Delivered, Canceled, Returned
    private int totalPay;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "user_id") private User user;
	 */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address userAddress;


}
