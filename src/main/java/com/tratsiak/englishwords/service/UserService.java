package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.Registration;
import com.tratsiak.englishwords.service.exception.ServiceException;

public interface UserService {
    void register(Registration registration) throws ServiceException;

}
