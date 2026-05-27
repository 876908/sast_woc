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
    public Result getTeam(@RequestAttribute("UserCode") String userCode) {
        TeamVO team = teamService.getByCaptain(userCode);
        return Result.success(team);
    }

    @RequireRoleAnnotation.RequireRole(0)
    @PostMapping("/captain/team")
    public Result updateTeam(@RequestBody TeamUpdateDTO updateDTO, @RequestAttribute("UserCode") String userCode) {
        teamService.updateTeam(updateDTO, userCode);
        return Result.success(updateDTO);
    }

    @GetMapping("/academy/team")
    @RequireRoleAnnotation.RequireRole(2)
    public Result academyGetTeamList(@RequestParam(required = false) Integer comId,
                                     @RequestAttribute("AcademyId") Long academyId) {
        List<TeamVO> teamlist = teamService.academyGetTeamList(comId, academyId);
        return Result.success(teamlist);
    }
}
