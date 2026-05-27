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
    public Result login(@RequestBody User user) {
        if (user == null || user.getUserCode() == null || user.getUserCode().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {
            return Result.error(2001, "用户名或密码不能为空");
        }
        LoginVO token = userService.login(user);
        if (token != null) {
            return Result.success(token);
        }
        return Result.error(2002, "用户名或密码错误");
    }
}