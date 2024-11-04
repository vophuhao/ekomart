package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vn.iotstar.entity.UserInfo;

import jakarta.transaction.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByName(String username);
	Optional<UserInfo> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserInfo u SET u.password = ?2 WHERE u.email = ?1")
	void updatePassword(String email, String password);
}
