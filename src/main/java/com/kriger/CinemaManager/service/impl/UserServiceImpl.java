package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.dto.UserRegisterDTO;
import com.kriger.CinemaManager.exception.EmailAlreadyExistsException;
import com.kriger.CinemaManager.exception.UsernameAlreadyExistsException;
import com.kriger.CinemaManager.model.User;
import com.kriger.CinemaManager.model.enums.UserRole;
import com.kriger.CinemaManager.repository.UserRepository;
import com.kriger.CinemaManager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User createUser(String name, String email, String password, UserRole role) {
        User user = new User(name, email, password, role);
        return userRepository.save(user);
    }

    @Override
    public User registerUser(UserRegisterDTO user, UserRole role) {
        if (userRepository.existsByName(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        User userToSave = new User(
                user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                role);

        return userRepository.save(userToSave);
    }
}
