package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.Auth;
import com.tratsiak.englishwords.model.bean.AuthTelegramApp;
import com.tratsiak.englishwords.model.bean.Token;
import com.tratsiak.englishwords.model.entity.User;
import com.tratsiak.englishwords.repository.UserRepository;
import com.tratsiak.englishwords.security.JwtProvider;
import com.tratsiak.englishwords.security.JwtProviderException;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final String usernameTelegramApp;
    private final String passwordTelegramApp;

    @Autowired
    public TokenServiceImpl(PasswordEncoder passwordEncoder,
                            UserRepository userRepository,
                            JwtProvider jwtProvider,
                            @Value("${telegram.username}") String usernameTelegramApp,
                            @Value("${telegram.password}") String passwordTelegramApp) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.usernameTelegramApp = usernameTelegramApp;
        this.passwordTelegramApp = passwordTelegramApp;
    }

    @Override
    public Token get(AuthTelegramApp auth) throws ServiceException {
        if (!auth.getUsername().equals(usernameTelegramApp) ||
                !passwordEncoder.matches(auth.getPassword(), passwordTelegramApp)) {
            throw new ServiceException("Not valid password or username");
        }

        long telegramId = auth.getTelegramId();

        User user = userRepository.findByTelegramId(telegramId);

        if (user == null) {
            user = User.builder().telegramId(telegramId).build();
            userRepository.save(user);
        }

        return Token.builder()
                .access(jwtProvider.generateAccessToken(user.getId()))
                .refresh(jwtProvider.generateRefreshToken(user.getId()))
                .build();
    }

    @Override
    public Token get(Auth auth) throws ServiceException {
        return null;
    }

    @Override
    public Token get(Token token) throws ServiceException {
        try {
            Claims claims = jwtProvider.getRefreshClaims(token.getRefresh());
            token.setAccess(jwtProvider.generateAccessToken(claims.get("id", Long.class)));
        } catch (JwtProviderException e) {
            throw new ServiceException("Not valid refresh token", e);
        }

        return token;
    }
}
