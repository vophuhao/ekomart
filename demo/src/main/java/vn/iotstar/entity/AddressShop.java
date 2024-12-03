package vn.iotstar.entity;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddressShop implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String addressName;    // mun them phan name và sdt 
	private String addressSdt;
	private Long provinceId;
	private Long districtId;
	private Long streetId;
	private String detail;

	
	@OneToOne(mappedBy = "address")
	private Shop shop;


}
