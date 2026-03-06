package org.example.controller;

import org.example.annotation.RequireRoleAnnotation;
import org.example.pojo.DTO.TeamUpdateDTO;
import org.example.pojo.Result;
import org.example.pojo.VO.TeamVO;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;
    @GetMapping("/captain/team")
    public Result getTeam(@RequestAttribute String UserCode) {
        TeamVO team=teamService.getByCaptain(UserCode);
        return Result.success(team);
    }
    @RequireRoleAnnotation.RequireRole(0)
    @PostMapping("/captain/team")
    public Result updateTeam(@RequestBody TeamUpdateDTO updateDTO, @RequestAttribute String UserCode) {
        teamService.updateTeam(updateDTO, UserCode);
        return Result.success(updateDTO);
    }
    @GetMapping("/academy/team")
    @RequireRoleAnnotation.RequireRole(2)
    public Result academyGetTeamList(@RequestParam(required = false) Integer comId,
                              @RequestAttribute Long AcademyId) {
        List<TeamVO> teamlist = teamService.academyGetTeamList(comId, AcademyId);
        return Result.success(teamlist);
    }
}
