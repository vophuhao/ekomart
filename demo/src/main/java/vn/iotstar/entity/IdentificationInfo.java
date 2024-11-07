package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationInfo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;
	private String number;
	private String name;
	private String beforeImage;
	private String afterImage;
	private int confirm;       // confirm xac nhan dữ liệu cung cấp là trung thực, đồng ý chính sách bảo mật của ekomart
	
	// Thông tin định danh cho vendor lúc đăng ký 
	 @OneToOne(mappedBy = "info")
	 private Shop shop;
	 

}
