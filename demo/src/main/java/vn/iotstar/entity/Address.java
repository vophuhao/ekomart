package vn.iotstar.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String uname;
	private String phone;
	private String province;
    private String district;
    private String ward;
    private String detail;
    private int defaults;
	
	 @ManyToOne 
	 @JoinColumn(name = "user_id") 
	 private UserInfo user;

	@Override
	public String toString() {
		return "Address [id=" + id + ", uname=" + uname + ", phone=" + phone + ", province=" + province + ", district="
				+ district + ", ward=" + ward + ", detail=" + detail + ", defaults=" + defaults + ", user=" + user
				+ "]";
	}
	 
	 
}
