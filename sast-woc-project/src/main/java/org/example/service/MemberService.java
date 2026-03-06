package org.example.service;

import org.example.pojo.DTO.MemberAddDTO;
import org.example.pojo.DTO.MemberUpdateDTO;
import org.example.pojo.Member;
import org.example.pojo.VO.MemberVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;
@Service
public interface MemberService {
    List<MemberVO> getByTeamId(Integer teamId, String userCode);
    void addMember(Integer teamId, MemberAddDTO addDTO);
    void deleteMember(Integer teamId, Long memberId);
    void updateMember(Integer teamId, MemberUpdateDTO updateDTO);
}
