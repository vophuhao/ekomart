package vn.iotstar.service.user;

public interface ILocationService {

	  String getProvinceNameByCode(int provinceCode);

	    String getDistrictNameByCode(int districtCode);

	    String getWardNameByCode(int wardCode);
}
