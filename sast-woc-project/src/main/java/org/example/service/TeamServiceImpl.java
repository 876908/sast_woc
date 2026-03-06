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
import java.util.List;

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
        //删除不在新集合的成员
        for (Integer id : currentIds) {
            if (!newMemberIds.contains(id)) {
                Member member = memberMapper.selectById(Long.valueOf(id));
                if (member.getIsCaptain() == 1) {
                    throw new BusinessException(3002, "不能移除队长");
                }
                memberMapper.deleteById(Long.valueOf(id));
            }
        }

        //新增新成员
        for (Long id : newMemberIds) {
            if (!currentIds.contains(id)) {
                //检查该成员是否存在
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
        //删除不再关联的指导老师
        for (Integer id : currentIds) {
            if (!newInstructorIds.contains(id)) {
                teamInstructorMapper.delete(teamId, Long.valueOf(id));
                intructorMapper.setNullByTeamId(Long.valueOf(id));
            }
        }
        //新增未关联的指导老师
        for (Long id : newInstructorIds) {
            if (!currentIds.contains(id)) {
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
