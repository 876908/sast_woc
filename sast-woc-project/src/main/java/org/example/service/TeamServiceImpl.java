package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.mapper.*;
import org.example.pojo.DTO.TeamUpdateDTO;
import org.example.pojo.Member;
import org.example.pojo.Team;
import org.example.pojo.User;
import org.example.pojo.VO.TeamVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IntructorMapper intructorMapper;
    @Autowired
    private TeamInstructorMapper teamInstructorMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public TeamVO getByCaptain(String UserCode) {
        TeamVO teamVO=teamMapper.getByCaptain(UserCode);
        if (teamVO == null) {
            throw new BusinessException(4001, "未找到所属队伍");
        }
        List<Integer> memberIds=memberMapper.getStudentIds(teamVO.getId());
        List<Integer> instructorIds=intructorMapper.getInstructorWorkCode(teamVO.getId());
        teamVO.setMemberIds(memberIds);
        teamVO.setInstructorIds(instructorIds);
        return teamVO;
    }
    @Override
    public void updateTeam(TeamUpdateDTO updateDTO, String userCode) {
        Team team = teamMapper.selectById(updateDTO.getId());
        if (team == null) {
            throw new BusinessException(4001, "队伍不存在");
        }

        //校验是否是本队队长
        User user=new User();
        user.setUserCode(userCode);
        User user1 = userMapper.selectByUserCode(user);
        if (user1 == null) {
            throw new BusinessException(4001, "用户不存在");
        }
        if (!team.getCaptainId().equals(user1.getId())) {
            throw new BusinessException(2003, "无权修改其他队伍的信息");
        }

        team.setComId(updateDTO.getComId());
        team.setName(updateDTO.getName());
        team.setCaptainId(Long.valueOf(updateDTO.getCaptainId()));
        team.setCaptainName(updateDTO.getCaptainName());
        team.setStatus(updateDTO.getStatus());
        teamMapper.updateById(team);
        updateMembers(team.getId(), updateDTO.getMemberIds());
        updateInstructors(team.getId(), updateDTO.getInstructorIds());
    }

    private void updateMembers(Long teamId, List<Long> newMemberIds) {
        List<Integer> currentIds = memberMapper.selectIdsByTeamId(teamId);
        Set<Long> currentIdSet = currentIds.stream().map(Long::valueOf).collect(Collectors.toSet());
        Set<Long> newIdSet = newMemberIds == null ? Collections.emptySet() : new HashSet<>(newMemberIds);
        //删除不在新集合的成员
        for (Long id : currentIdSet) {
            if (!newIdSet.contains(id)) {
                Member member = memberMapper.selectById(id);
                if (member.getIsCaptain() == 1) {
                    throw new BusinessException(3002, "不能移除队长");
                }
                memberMapper.deleteById(id);
            }
        }

        //新增新成员
        for (Long id : newIdSet) {
            if (!currentIdSet.contains(id)) {
                Member member = memberMapper.selectById(id);
                if (member == null) {
                    throw new BusinessException(4001, "成员ID " + id + " 不存在");
                }
                if (!teamId.equals(member.getTeamId())) {
                    memberMapper.updateTeamId(id, teamId);
                }
            }
        }
    }

    private void updateInstructors(Long teamId, List<Long> newInstructorIds) {
        List<Integer> currentIds = teamInstructorMapper.selectInstructorIdsByTeamId(teamId);
        Set<Long> currentIdSet = currentIds.stream().map(Long::valueOf).collect(Collectors.toSet());
        Set<Long> newIdSet = newInstructorIds == null ? Collections.emptySet() : new HashSet<>(newInstructorIds);
        //删除不再关联的指导老师
        for (Long id : currentIdSet) {
            if (!newIdSet.contains(id)) {
                teamInstructorMapper.delete(teamId, id);
                intructorMapper.setNullByTeamId(id);
            }
        }
        //新增未关联的指导老师
        for (Long id : newIdSet) {
            if (!currentIdSet.contains(id)) {
                teamInstructorMapper.insert(teamId, id);
                intructorMapper.updateTeamId(id, teamId);
            }
        }
    }
    @Override
    public List<TeamVO> academyGetTeamList(Integer comId, Long academyId){
        List<Team> teams = teamMapper.selectByComIdAndAcademy(academyId, comId);
        List<TeamVO> VoList = new ArrayList<>();
        for (Team team : teams) {
            TeamVO vo = new TeamVO();
            BeanUtils.copyProperties(team, vo);
            vo.setMemberIds(memberMapper.selectIdsByTeamId(team.getId()));
            vo.setInstructorIds(teamInstructorMapper.selectInstructorIdsByTeamId(team.getId()));
            VoList.add(vo);
        }
        return VoList;
    }
}
