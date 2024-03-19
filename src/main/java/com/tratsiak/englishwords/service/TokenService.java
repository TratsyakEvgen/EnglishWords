package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.Auth;
import com.tratsiak.englishwords.model.bean.AuthTelegramApp;
import com.tratsiak.englishwords.model.bean.Token;
import com.tratsiak.englishwords.service.exception.ServiceException;

public interface TokenService {
    Token get(AuthTelegramApp auth) throws ServiceException;

    Token get(Auth auth) throws ServiceException;

    Token get(Token token) throws ServiceException;
}
