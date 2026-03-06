package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.mapper.MemberMapper;
import org.example.pojo.DTO.MemberAddDTO;
import org.example.pojo.DTO.MemberUpdateDTO;
import org.example.pojo.Member;
import org.example.pojo.VO.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<MemberVO> getByTeamId(Integer teamId, String userCode) {
        return memberMapper.getByTeamId(teamId);
    }
    @Override
    @Transactional
    public void addMember(Integer teamId, MemberAddDTO addDTO) {
        //验证路径teamId与DTO中的teamId一致
        if (!teamId.equals(addDTO.getTeamId().intValue())) {
            throw new BusinessException(1001, "队伍ID不一致");
        }

        Member member = new Member();
        member.setId(addDTO.getId());
        member.setTeamId(addDTO.getTeamId());
        member.setName(addDTO.getName());
        member.setStudentId(addDTO.getStudentId());
        member.setAcademyId(addDTO.getAcademyId());
        member.setPhone(addDTO.getPhone());
        member.setIsCaptain(addDTO.getIsCaptain());
    //判断是否违反唯一约束
        try {
            memberMapper.insert(member);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(3001, "该成员已存在于队伍中或ID已存在");
        }
    }

    @Override
    @Transactional
    public void deleteMember(Integer teamId, Long memberId) {
        Member member = memberMapper.selectById(memberId);
        if (member == null) {
            throw new BusinessException(4001, "成员不存在");
        }
        if (!member.getTeamId().equals(teamId.longValue())) {
            throw new BusinessException(2003, "无权操作其他队伍的成员");
        }
        if (member.getIsCaptain() == 1) {
            throw new BusinessException(3002, "不能删除队长");
        }
        memberMapper.deleteById(memberId);
    }

    @Override
    @Transactional
    public void updateMember(Integer teamId, MemberUpdateDTO updateDTO) {
        //验证路径teamId与DTO中的teamId一致
        if (!teamId.equals(updateDTO.getTeamId().intValue())) {
            throw new BusinessException(1001, "队伍ID不一致");
        }

        Member member1 = memberMapper.selectById(updateDTO.getId());
        if (member1 == null) {
            throw new BusinessException(4001, "成员不存在");
        }
        if (!member1.getTeamId().equals(teamId.longValue())) {
            throw new BusinessException(2003, "无权操作其他队伍的成员");
        }

        Member member = new Member();
        member.setId(updateDTO.getId());
        member.setName(updateDTO.getName());
        member.setStudentId(updateDTO.getStudentId());
        member.setTeamId(updateDTO.getTeamId());
        member.setAcademyId(updateDTO.getAcademyId());
        member.setPhone(updateDTO.getPhone());
        member.setIsCaptain(updateDTO.getIsCaptain());

        memberMapper.updateById(member);
    }
}
