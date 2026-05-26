package org.example.controller;

import org.example.annotation.LogOperation;
import org.example.annotation.RequireRoleAnnotation;
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
    @RequireRoleAnnotation.RequireRole({0,2,3})
    @GetMapping("/competitions")
    @LogOperation
    public Result getCompetitions(@RequestAttribute Integer Role, @RequestAttribute String UserCode) {
        List<Competition> competitionList=competitionsService.findAll(Role,UserCode);
        return Result.success(competitionList);
    }
    @PostMapping("/admin/competition")
    @RequireRoleAnnotation.RequireRole(3)
    public Result createCompetition(@RequestBody Competition competition) {
        Competition competition1= competitionsService.createCompetition(competition);
        return Result.success(competition1);
    }
}
