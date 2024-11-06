package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.IdentificationInfo;

@Repository
public interface IdentificationInfoRepository extends JpaRepository<IdentificationInfo, Long>{


}
