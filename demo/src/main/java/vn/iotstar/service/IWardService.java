package vn.iotstar.service;
import vn.iotstar.model.Ward;
import java.util.List;

public interface IWardService {
    List<Ward> findAllByDistrictId(Long districtId);
    String getNameById(Long districtId);
}
