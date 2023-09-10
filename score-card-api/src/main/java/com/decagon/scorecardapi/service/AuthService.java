package com.decagon.scorecardapi.service;

import com.decagon.scorecardapi.dto.requestdto.LoginDto;
import com.decagon.scorecardapi.dto.responsedto.LoginResponse;

public interface AuthService{
    LoginResponse login(LoginDto loginDto) throws Exception;
}
