package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.RequireRoleAnnotation;
import org.example.mapper.TeamMapper;
import org.example.pojo.DTO.MemberAddDTO;
import org.example.pojo.DTO.MemberDeleteDTO;
import org.example.pojo.DTO.MemberUpdateDTO;
import org.example.pojo.Result;
import org.example.pojo.VO.MemberVO;
import org.example.pojo.VO.TeamVO;
import org.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequireRoleAnnotation.RequireRole(0)
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamMapper teamMapper;

    @GetMapping("/captain/team/{teamId}/member")
    public Result getTeamMember(@PathVariable Integer teamId, @RequestAttribute String UserCode) {
        Result authError = checkCaptainTeamAccess(teamId, UserCode);
        if (authError != null) {
            return authError;
        }
        List<MemberVO> memberList=memberService.getByTeamId(teamId, UserCode);
        return Result.success(memberList);
    }
    @PostMapping("/captain/team/{teamId}/member")
    public Result addMember(@PathVariable Integer teamId, @RequestBody MemberAddDTO addDTO, @RequestAttribute String UserCode) {
        if (!Objects.equals(addDTO.getTeamId(), Long.valueOf(teamId))) {
            return Result.error(1001, "路径teamId与请求体中的teamId不一致");
        }
        Result authError = checkCaptainTeamAccess(teamId, UserCode);
        if (authError != null) {
            return authError;
        }
        memberService.addMember(teamId, addDTO);
        return Result.success(addDTO);
    }
    @DeleteMapping("/captain/team/{teamId}/member")
    public Result deleteMember(@PathVariable Integer teamId, @RequestBody MemberDeleteDTO deleteDTO, @RequestAttribute String UserCode) {
        Result authError = checkCaptainTeamAccess(teamId, UserCode);
        if (authError != null) {
            return authError;
        }
        memberService.deleteMember(teamId, deleteDTO.getId());
        return Result.success(deleteDTO);
    }
    @PatchMapping("/captain/team/{teamId}/member")
    public Result updateMember(@PathVariable Integer teamId, @RequestBody MemberUpdateDTO updateDTO, @RequestAttribute String UserCode) {
        if (!Objects.equals(updateDTO.getTeamId(), Long.valueOf(teamId))) {
            return Result.error(1001, "路径teamId与请求体中的teamId不一致");
        }
        Result authError = checkCaptainTeamAccess(teamId, UserCode);
        if (authError != null) {
            return authError;
        }
        memberService.updateMember(teamId, updateDTO);
        return Result.success(updateDTO);
    }

    private Result checkCaptainTeamAccess(Integer teamId, String userCode) {
        TeamVO teamVO = teamMapper.getByCaptain(userCode);
        if (teamVO == null) {
            return Result.error(4001, "未找到所属队伍");
        }
        if (!Objects.equals(teamVO.getId(), teamId)) {
            log.info("该队长无权限");
            return Result.error(2003, "不能访问其他队伍！");
        }
        return null;
    }
}

