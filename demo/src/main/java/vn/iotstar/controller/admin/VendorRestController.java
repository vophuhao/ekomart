package vn.iotstar.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Shop;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.model.VendorModel;
import vn.iotstar.service.IDistrictService;
import vn.iotstar.service.IProvinceService;
import vn.iotstar.service.IWardService;
import vn.iotstar.service.admin.AdminShopService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/vendor")
public class VendorRestController {
    @Autowired
    private AdminShopService adminShopService;
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private IDistrictService districtService;
    @Autowired
    private IWardService wardService;

    @GetMapping("/approve/{id}")
    public Shop approve(@PathVariable("id") Long id) {
        Optional<Shop> optShop = adminShopService.findById(id);
        return optShop.isPresent() ? optShop.get() : null;
    }
    @GetMapping("/details/{id}")
    public VendorModel showDetails(@PathVariable("id") Long id) {
        Optional<Shop> optShop = adminShopService.findById(id);
        Long provinceId = optShop.get().getAddress().getProvinceId();
        Long districtId = optShop.get().getAddress().getDistrictId();
        Long streetId = optShop.get().getAddress().getStreetId();

        String province = provinceService.getNameById(provinceId);
        String district = districtService.getNameById(districtId);
        String ward = wardService.getNameById(streetId);

        VendorModel vendorModel = new VendorModel();
        vendorModel = VendorModel.toDTO(optShop.get());
        vendorModel.setProvince(province);
        vendorModel.setDistrict(district);
        vendorModel.setWard(ward);
        // Convert the Shop entity to VendorModel
        return vendorModel;

    }



}
