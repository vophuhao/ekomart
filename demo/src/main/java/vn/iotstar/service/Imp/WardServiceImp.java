package vn.iotstar.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.WardEntity;
import vn.iotstar.model.Ward;
import vn.iotstar.repository.WardRepository;
import vn.iotstar.service.IWardService;

import java.util.ArrayList;
import java.util.List;

@Service
public class WardServiceImp implements IWardService {

    @Autowired
    private WardRepository wardRepository;

    @Override
    public List<Ward> findAllByDistrictId(Long provinceId) {
        List<WardEntity> wardEntities = wardRepository.findByDistrictId(provinceId);
        List<Ward> wards = new ArrayList<Ward>();
        for (WardEntity wardEntity : wardEntities) {
            Ward ward = new Ward();
            ward.setId(wardEntity.getId());
            ward.setDistrictId(wardEntity.getDistrictId());
            ward.setName(wardEntity.getName());
            ward.setType(wardEntity.getType());
            wards.add(ward);
        }
        return wards;
    }

    @Override
    public String getNameById(Long districtId) {
        return wardRepository.findById(districtId).get().getName();
    }
}
