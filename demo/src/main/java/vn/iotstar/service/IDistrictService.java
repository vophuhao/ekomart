package vn.iotstar.service;

import vn.iotstar.entity.WardEntity;
import vn.iotstar.model.District;
import vn.iotstar.model.Ward;

import java.util.List;

public interface IDistrictService {
    List<District> findAllByProvinceId(Long provinceId);
}
