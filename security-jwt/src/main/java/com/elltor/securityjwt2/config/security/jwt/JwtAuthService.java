package com.elltor.securityjwt2.config.security.jwt;

import com.elltor.securityjwt2.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthService {

    // 此处注入的bean在SpringConfig中产生, 如果不在其中声明则注入AuthenticationManager报错
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 登录认证换取JWT令牌
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        //用户验证
        Authentication authentication = null;
        try {
            // 进行身份验证,
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException("用户名密码错误");
        }

        Users loginUser = (Users) authentication.getPrincipal();
        // 生成token
        return jwtTokenUtils.generateToken(loginUser);

    }

}
