package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.DistrictEntity;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity,Long> {
    List<DistrictEntity> findAllByProvinceId(Long provinceId);
}
