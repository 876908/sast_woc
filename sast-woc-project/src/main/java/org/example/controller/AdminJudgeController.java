package org.example.controller;

import org.example.annotation.RequireRoleAnnotation;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.AdminJudgeService;
import org.example.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireRoleAnnotation.RequireRole(3)
public class AdminJudgeController {

    @Autowired
    private AdminJudgeService adminJudgeService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("admin/judge")
    public Result addJudge(@RequestBody User user) {
        return Result.success(adminJudgeService.addJudge(user));
    }

    @DeleteMapping("admin/judge")
    public Result deleteJudge(@RequestBody User user) {
        User user1=userMapper.selectByUserCode(user);
        adminJudgeService.deleteJudge(user.getUserCode());
        return Result.success(user1);
    }
}
