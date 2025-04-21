package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.dto.UserRegisterDTO;
import com.kriger.CinemaManager.model.User;
import com.kriger.CinemaManager.model.enums.UserRole;

public interface UserService {

    /**
     * Возвращает пользователя по имени
     */
    User getUserByName(String name);

    /**
     * Создает пользователя
     */
    User createUser(String name, String email, String password, UserRole role);

    /**
     * Регистрирует пользователя
     */
    User registerUser(UserRegisterDTO user, UserRole role);
}
