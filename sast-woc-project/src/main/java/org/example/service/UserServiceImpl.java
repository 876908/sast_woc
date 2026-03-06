package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.User;
import org.example.mapper.UserMapper;
import org.example.pojo.VO.LoginVO;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserMapper userMapper;
    @Override
    public LoginVO login(User user){
        User u= userMapper.selectByUserCode(user);
        Map<String,Object> claims=new HashMap<>();
        if(u==null){
            log.info("登录失败，用户不存在");
            return null;
        }
        boolean passwordMatch = passwordEncoder.matches(user.getPassword(),u.getPassword());
        /*
        log.info("用户输入密码: {}", user.getPassword());
        log.info("数据库存储密码: {}", u.getPassword());
        log.info("密码匹配结果: {}", passwordMatch);
         */
        if(!passwordMatch){
            log.info("登录失败，密码错误");
            return null;
        }

        log.info("登录成功，登录信息:{}",u);
        claims.put("UserCode",u.getUserCode());
        claims.put("Role",u.getRole());
        claims.put("AcademyId",u.getAcademyId());
        return new LoginVO(JwtUtils.generateToken(claims));
    }
}
