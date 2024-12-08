package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.UserInfo;

import jakarta.transaction.Transactional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByName(String username);
	Optional<UserInfo> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserInfo u SET u.password = ?2 WHERE u.email = ?1")
	void updatePassword(String email, String password);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserInfo u SET u.otp = ?2 WHERE u.email = ?1")
	void updateOtp(String email, int otp);
	
	// Tìm tất cả người dùng ngoại trừ những người có role "ROLE_ADMIN"
    @Query("SELECT u FROM UserInfo u WHERE u.roles NOT LIKE %:role%")
    List<UserInfo> findAllExceptRole(@Param("role") String role);
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserInfo u WHERE u.email = :email AND u.enabled = false")
    boolean existsByEmailAndEnabledFalse(@Param("email") String email);
}
