package vn.iotstar.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.ProvinceEntity;
import vn.iotstar.model.Province;
import vn.iotstar.repository.ProvinceRepository;
import vn.iotstar.service.IProvinceService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceServiceImp implements IProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        List< ProvinceEntity> provinceEntities = provinceRepository.findAll();
        List<Province> provinces = new ArrayList<>();

        for (ProvinceEntity provinceEntity : provinceEntities) {
            Province province = new Province();
            province.setId(provinceEntity.getId());
            province.setName(provinceEntity.getName());
            province.setSlug(provinceEntity.getSlug());
            province.setType(provinceEntity.getType());
            provinces.add(province);
        }
        return provinces;
    }

    @Override
    public String getNameById(Long id) {
        return provinceRepository.findById(id).get().getSlug();
    }
}
