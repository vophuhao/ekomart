package vn.iotstar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
	@Column(name = "name", unique = true)
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

	@Override
	public String toString() {
		return "UserInfo{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", roles='" + roles + '\'' +
				", enabled=" + enabled +
				'}';
	}
}