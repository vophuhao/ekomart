package vn.iotstar.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.UserInfo;
import vn.iotstar.model.MailBody;
import vn.iotstar.repository.UserInfoRepository;

@Service
public record UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder, EmailService emailService) {
	
	public boolean registerUser(UserInfo userInfo) {
		try {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfo.setRoles("ROLE_USER"); // Đặt mặc định là USER, có thể thay đổi nếu cần
			
			// Send mail
	        int otp = otpGenerator();
	        MailBody mailBody = MailBody.builder()
	                .to(userInfo.getEmail())
	                .text("This is the OTP for your Forgot Password request: " + otp)
	                .subject("OTP for Forgot Password request")
	                .build();
	        
	        userInfo.setOtp(otp);
	        repository.save(userInfo);
	        emailService.sendSimpleMessage(mailBody);
	        
	        return true;
		} catch (Exception ex) {
			// Send mail
	        int otp = otpGenerator();
	        MailBody mailBody = MailBody.builder()
	                .to(userInfo.getEmail())
	                .text("This is the OTP for your Forgot Password request: " + otp)
	                .subject("OTP for Forgot Password request")
	                .build();
			boolean isUserDisabled = repository.existsByEmailAndEnabledFalse(userInfo.getEmail());
			if (isUserDisabled) {
				repository.updateOtp(userInfo.getEmail(), otp);
				emailService.sendSimpleMessage(mailBody);
			    return true;
			}
			return false;
		}
	}
	
//    public boolean verifyOtp(String otp) {
//        // Kiểm tra OTP có hợp lệ không và kích hoạt tài khoản
//    	return false;
//    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
    
	public List<UserInfo> findAll() {
		return repository.findAll();
	}
	
	public List<UserInfo> findAllExceptAdmin() {
        return repository.findAllExceptRole("ROLE_ADMIN");
    }
	
	public UserInfo findById(Integer id) {
	        return repository.findById(id).orElse(null);
	}
	
	public UserInfo findByUsername(String username) {
        return repository.findByName(username).orElse(null);
}

    public void updateUser(UserInfo user) {
        repository.save(user);
    }

    public boolean deleteUser(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}