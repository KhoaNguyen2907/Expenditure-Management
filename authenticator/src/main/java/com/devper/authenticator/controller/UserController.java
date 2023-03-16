package com.devper.authenticator.controller;

import com.devper.authenticator.model.response.UserInfoResponse;
import com.devper.authenticator.service.AppUserService;
import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ProjectMapper;
import com.devper.common.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private ProjectMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper> loadCurrentUserInfo() {
        UserInfoResponse response = appUserService.getCurrentUserInfo();
        return ResponseUtils.success(response, HttpStatus.OK);
    }
}
