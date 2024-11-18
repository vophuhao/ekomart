package vn.iotstar.service;

import vn.iotstar.entity.WardEntity;
import vn.iotstar.model.Ward;

import java.util.List;
import java.util.Optional;

public interface IWardService {
    List<Ward> findAllByDistrictId(Long districtId);
}
