package org.example.service;

import org.example.pojo.DTO.TeamUpdateDTO;
import org.example.pojo.VO.TeamVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    TeamVO getByCaptain(String userCode);

    void updateTeam(TeamUpdateDTO updateDTO, String userCode);

    List<TeamVO> academyGetTeamList(Integer comId, Long academyId);
}
