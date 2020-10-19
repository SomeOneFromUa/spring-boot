package com.example.seq.demo.service;

import com.example.seq.demo.dto.request.ChangePasswordRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    BasicResponse changePassword(ChangePasswordRequest body, HttpServletRequest httpServletRequest);
    User getInfo(HttpServletRequest request);
}
