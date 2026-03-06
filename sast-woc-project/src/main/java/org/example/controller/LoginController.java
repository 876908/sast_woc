package org.example.controller;

import org.example.pojo.User;
import org.example.pojo.Result;
import org.example.pojo.VO.LoginVO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        LoginVO Token=userService.login(user);
        if(Token!=null){
            return Result.success(Token);
        }
        return Result.error(2002,"用户名或密码错误");
    }

}
