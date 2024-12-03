package vn.iotstar.service;

import vn.iotstar.model.Province;

import java.util.List;

public interface IProvinceService {
    List<Province> findAll();
    String getNameById(Long id);
}
