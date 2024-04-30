package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.Registration;
import com.tratsiak.englishwords.model.entity.User;
import com.tratsiak.englishwords.repository.UserRepository;
import com.tratsiak.englishwords.service.UserService;
import com.tratsiak.englishwords.service.exception.ErrorMessages;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(Registration registration) throws ServiceException {

        String password = registration.getPassword();

        if (!password.equals(registration.getRepeatPassword())) {
            throw new ServiceException(LevelException.INFO, ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        try {

            String login = registration.getLogin();

            User user = userRepository.findByLogin(login);

            if (user != null) {
                throw new ServiceException(LevelException.INFO, ErrorMessages.USER_ALREADY_EXISTS);
            }

            user = User.builder()
                    .login(login)
                    .password(passwordEncoder.encode(password))
                    .build();
            userRepository.save(user);

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.CREATE_USER,
                    "Repository exception! Can't create new user", e);
        }
    }
}
