package vn.iotstar.service.admin;

import jakarta.validation.Valid;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

import java.util.List;
import java.util.Optional;

public interface AdminShopService {
    List<Shop> findByDisplay(int display);
    Optional<Shop> findById(Long shopId);
    List<Shop> findAll();
    <S extends Shop> S save(S entity);
    Optional<Shop> findByUserId(int userId);
    
    List<Shop> findByStatus(int status);
    Optional<Shop>  findByShopId(String shopId);
}
