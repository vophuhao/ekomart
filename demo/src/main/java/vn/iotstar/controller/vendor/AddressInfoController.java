package vn.iotstar.controller.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.model.District;
import vn.iotstar.model.Province;
import vn.iotstar.model.Ward;
import vn.iotstar.service.IDistrictService;
import vn.iotstar.service.IProvinceService;
import vn.iotstar.service.IWardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor")
public class AddressInfoController {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private IDistrictService districtService;

    @Autowired
    private IWardService wardService;

    @GetMapping("/provinces")
    public List<Province> getListProvince(){
        return provinceService.findAll();
    }

    @GetMapping("/districts/{provinceId}")
    public List<District> getListDistrict(@PathVariable Long provinceId){
        return districtService.findAllByProvinceId(provinceId);
    }

    @GetMapping("/wards/{districtId}")
    public List<Ward> getListWard(@PathVariable Long districtId){
        return wardService.findAllByDistrictId(districtId);
    }
}
