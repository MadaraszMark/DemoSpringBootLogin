package app.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.main.dto.UserLoginDto;
import app.main.dto.UserRegisterDto;
import app.main.model.User;
import app.main.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(UserRegisterDto dto) {
        if (userRepository.findByUserName(dto.getUsername()).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUserName(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());

        userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDto dto) {
        Optional<User> userOpt = userRepository.findByUserName(dto.getUsername());

        if (userOpt.isPresent()) {
            String storedHashedPassword = userOpt.get().getPassword();
            return passwordEncoder.matches(dto.getPassword(), storedHashedPassword);
        }

        return false;
    }
    
    public boolean usernameExists(String username) {
        return userRepository.findByUserName(username).isPresent();
    }
}

