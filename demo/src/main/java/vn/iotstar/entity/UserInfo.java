package vn.iotstar.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String password;
	private String email;
	private String roles;
	private Integer otp;
    private boolean enabled = false;
	
	@OneToOne(mappedBy = "user")
	private ForgotPassword forgotpassword;
	
	@OneToOne(mappedBy = "user")
	private Shop shop;
	
	@OneToOne(mappedBy = "user")
	private Cart cart;
}