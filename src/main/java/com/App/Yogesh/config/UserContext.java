package com.App.Yogesh.config;
import com.App.Yogesh.Dto.UserDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

        private static final ThreadLocal<UserDetailsDto> currentUser = new ThreadLocal<>();
        public void setCurrentUser(UserDetailsDto userDto) {
            currentUser.set(userDto);
        }
        public UserDetailsDto getCurrentUser() {
            return currentUser.get();
        }
        public void clear() {
            currentUser.remove();
        }
    }


