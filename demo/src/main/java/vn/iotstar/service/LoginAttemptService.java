package vn.iotstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.UserInfoRepository;
import java.time.LocalDateTime;
import java.time.Duration;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCK_TIME_DURATION = 5; // minutes

    @Autowired
    private UserInfoRepository userRepository;

    public boolean isAccountLocked(String username) {
        UserInfo user = userRepository.findByName(username).orElse(null);
        if (user != null && user.getLockTime() != null) {
            if (LocalDateTime.now().isAfter(user.getLockTime())) {
                // Nếu đã hết thời gian khóa, reset trạng thái
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);
                userRepository.save(user);
                return false;
            }
            return true;
        }
        return false;
    }

    public void loginSucceeded(String username) {
        UserInfo user = userRepository.findByName(username).orElse(null);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            userRepository.save(user);
        }
    }

    public void loginFailed(String username) {
        UserInfo user = userRepository.findByName(username).orElse(null);
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
                user.setLockTime(LocalDateTime.now().plusMinutes(LOCK_TIME_DURATION));
            }
            userRepository.save(user);
        }
    }

    public String getErrorMessage(String username) {
        UserInfo user = userRepository.findByName(username).orElse(null);
        if (user != null) {
            if (user.getLockTime() != null) {
                Duration timeLeft = Duration.between(LocalDateTime.now(), user.getLockTime());
                if (timeLeft.isNegative()) {
                    return null;
                }
                long minutesLeft = timeLeft.toMinutes();
                long secondsLeft = timeLeft.toSecondsPart();
                return String.format("Tài khoản đã bị khóa. Vui lòng thử lại sau %d phút %d giây",
                        minutesLeft, secondsLeft);
            } else {
                int attemptsLeft = MAX_ATTEMPTS - user.getFailedLoginAttempts();
                if (attemptsLeft < MAX_ATTEMPTS) {
                    return String.format("Sai mật khẩu. Còn %d lần thử", attemptsLeft);
                }
            }
        }
        return null;
    }
}