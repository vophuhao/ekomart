package vn.iotstar.model;

import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Shop;

public class VendorModel {
    private Long id;
    private String name;
    private AddressShop address;
    private IdentificationInfo info;
    private String province;
    private String district;
    private String ward;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressShop getAddress() {
        return address;
    }

    public void setAddress(AddressShop address) {
        this.address = address;
    }

    public IdentificationInfo getInfo() {
        return info;
    }

    public void setInfo(IdentificationInfo info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public VendorModel(Long id, String name, AddressShop address, IdentificationInfo info ,  String province,String district, String ward) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.info = info;
        this.province = province;
        this.district = district;
        this.ward = ward;
    }

    public VendorModel() {

    }

    public static VendorModel toDTO(Shop vendor) {
        return new VendorModel(
                vendor.getId(),
                vendor.getName(),
                vendor.getAddress(),
                vendor.getInfo(),
                null,null,null
        );
    }

}
