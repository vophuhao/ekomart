package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.WardEntity;

import java.util.List;

public interface WardRepository extends JpaRepository<WardEntity,Long> {
    List<WardEntity> findByDistrictId(Long districtId);

}
