package vn.iotstar.service;

import vn.iotstar.entity.ProvinceEntity;
import vn.iotstar.model.Province;

import java.util.List;

public interface IProvinceService {
    List<Province> findAll();
}
