package vn.iotstar.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@JsonIgnore
	private ForgotPassword forgotpassword;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Shop shop;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Cart cart;
	
	
	@OneToMany(mappedBy="user")
	private List<Orders> orders;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Wishlist wishlist;
	
	@OneToMany(mappedBy = "user")
    @JsonIgnore // Bỏ qua danh sách reviews khi chuyển đổi sang JSON
    private List<Review> reviews;

	@Override
		public String toString() {
			return "UserInfo{" +
					"id=" + id +
					", name='" + name + '\'' +
					", email='" + email + '\'' +
					", roles='" + roles + '\'' +
					", enabled=" + enabled +
					'}';
		}}