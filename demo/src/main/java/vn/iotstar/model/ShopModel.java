package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.AddressShop;
import vn.iotstar.entity.IdentificationInfo;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.UserInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopModel {
    private Long id;
    private String shopId;
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

    private List<Product> products;

    private MultipartFile rts_images0;

    private MultipartFile rts_images2;
    private MultipartFile rts_images1;
}
