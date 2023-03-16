package com.devper.authenticator.service;

import com.devper.authenticator.dao.AppUserDAO;
import com.devper.authenticator.grpc.ReportClientGrpc;
import com.devper.authenticator.model.AppUser;
import com.devper.authenticator.model.response.UserBasicResponse;
import com.devper.authenticator.model.response.UserInfoResponse;
import com.devper.common.exception.NotFoundException;
import com.devper.common.utils.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private ProjectMapper mapper;
    @Autowired
    private ReportClientGrpc reportClientGrpc;

    @Override
    public UserInfoResponse getCurrentUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        UserBasicResponse user = mapper.map(getUserByUsername(username), UserBasicResponse.class);
        BigDecimal balance = BigDecimal.valueOf(reportClientGrpc.getBalance(token).getBalance()) ;
        return UserInfoResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .balance(balance)
                .build();
    }

    public AppUser getUserByUsername(String username) {
        return appUserDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
