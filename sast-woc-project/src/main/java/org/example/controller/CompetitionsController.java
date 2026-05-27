package org.example.controller;

import org.example.annotation.LogOperation;
import org.example.annotation.RequireRoleAnnotation;
import org.example.constant.RoleConstants;
import org.example.pojo.Competition;
import org.example.pojo.Result;
import org.example.service.CompetitionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompetitionsController {
    @Autowired
    private CompetitionsService competitionsService;

    @RequireRoleAnnotation.RequireRole({RoleConstants.CAPTAIN, RoleConstants.ACADEMY_ADMIN, RoleConstants.SUPER_ADMIN})
    @GetMapping("/competitions")
    @LogOperation
    public Result getCompetitions(@RequestAttribute("Role") Integer role, @RequestAttribute("UserCode") String userCode) {
        List<Competition> competitionList = competitionsService.findAll(role, userCode);
        return Result.success(competitionList);
    }

    @PostMapping("/admin/competition")
    @RequireRoleAnnotation.RequireRole(RoleConstants.SUPER_ADMIN)
    public Result createCompetition(@RequestBody Competition competition) {
        Competition created = competitionsService.createCompetition(competition);
        return Result.success(created);
    }
}
