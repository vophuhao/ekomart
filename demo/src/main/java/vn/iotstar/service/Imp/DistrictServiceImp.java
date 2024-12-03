package vn.iotstar.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.DistrictEntity;
import vn.iotstar.model.District;
import vn.iotstar.repository.DistrictRepository;
import vn.iotstar.service.IDistrictService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImp implements IDistrictService {

    @Autowired
    private DistrictRepository districtRepository;


    @Override
    public List<District> findAllByProvinceId(Long provinceId) {
        List<DistrictEntity> districtEntities = districtRepository.findAllByProvinceId(provinceId);
        List<District> districts = new ArrayList<District>();
        for (DistrictEntity districtEntity : districtEntities) {
            District district = new District();
            district.setId(districtEntity.getId());
            district.setProvinceId(districtEntity.getProvinceId());
            district.setName(districtEntity.getName());
            district.setType(districtEntity.getType());
            districts.add(district);
        }
        return districts;
    }

    @Override
    public String getNameById(Long id) {
        return districtRepository.findById(id).get().getName();
    }
}
