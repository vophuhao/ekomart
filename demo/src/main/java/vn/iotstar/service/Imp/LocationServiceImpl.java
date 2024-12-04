package vn.iotstar.service.Imp;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.iotstar.service.user.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService{

	private final RestTemplate restTemplate = new RestTemplate();


    // Lấy tên tỉnh từ mã
    public String getProvinceNameByCode(int provinceCode) {
        String url = "https://provinces.open-api.vn/api/p/" + provinceCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("name"); // Trả về tên tỉnh
        }
        return null;
    }

    // Lấy tên quận từ mã
    public String getDistrictNameByCode(int districtCode) {
        String url = "https://provinces.open-api.vn/api/d/" + districtCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("name"); // Trả về tên quận
        }
        return null;
    }

    // Lấy tên xã từ mã
    public String getWardNameByCode(int wardCode) {
        String url = "https://provinces.open-api.vn/api/w/" + wardCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("name"); // Trả về tên xã
        }
        return null;
    }
}
