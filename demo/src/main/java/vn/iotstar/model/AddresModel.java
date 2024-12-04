package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddresModel {

	
	
	private String uname;
	private String phone;
	private String province;
    private String district;
    private String ward;
    private String detail;
   
	
}
