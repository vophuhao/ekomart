package vn.iotstar.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.entity.AddressShop;
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
        Optional<Shop> shop = adminShopService.findById(id);
        if (shop.isPresent()) {

            AddressShop address = shop.get().getAddress();

            String province = provinceService.getNameById(address.getProvinceId());
            String district = districtService.getNameById(address.getDistrictId());
            String ward = wardService.getNameById(address.getStreetId());

            VendorModel vendorModel = VendorModel.toDTO(shop.get());
            vendorModel.setProvince(province);
            vendorModel.setDistrict(district);
            vendorModel.setWard(ward);

            return vendorModel;
        }
        else {
            return null;
        }

    }




}
