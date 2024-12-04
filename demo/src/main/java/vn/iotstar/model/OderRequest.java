package vn.iotstar.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OderRequest {

	 private String addressId;
	    private List<String> productIds;
	    private List<String> quantities;
}
