package vn.iotstar.model;

import lombok.Getter;
import lombok.Setter;
import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.UserInfo;

@Setter
@Getter
public class VendorModel {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private String sdt;
    private String email;
    private int status;
    private int display;
    private AddressShop address;
    private IdentificationInfo info;
    private UserInfo user;
    private String province;
    private String district;
    private String ward;


    public VendorModel(Long id, String name,String avatar, String description, String sdt,String email, int status,
    int display,  AddressShop address,IdentificationInfo info ,  String province,String district, String ward) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.sdt = sdt;
        this.email = email;
        this.status = status;
        this.display = display;
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
                vendor.getAvatar(),
                vendor.getDescription(),
                vendor.getSdt(),
                vendor.getEmail(),
                vendor.getStatus(),
                vendor.getDisplay(),
                vendor.getAddress(),
                vendor.getInfo(),
                null,null,null
        );
    }

}
