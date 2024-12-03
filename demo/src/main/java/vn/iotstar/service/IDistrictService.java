package vn.iotstar.service;
import vn.iotstar.model.District;
import java.util.List;

public interface IDistrictService {
    List<District> findAllByProvinceId(Long provinceId);
    String getNameById(Long id);
}

