package org.example.service;

import org.example.exception.BusinessException;
import org.example.mapper.CompetitionsMapper;
import org.example.pojo.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionsService {
    @Autowired
    private CompetitionsMapper competitionMapper;
    @Override
    public List<Competition> findAll(Integer role, String userCode){
        if(role==0){
            return competitionMapper.findAll2(userCode);
        }
        return competitionMapper.findAll();
    }
    @Override
    @Transactional
    public Competition createCompetition(Competition competition) {
        try {
            competitionMapper.insert(competition);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(3001, "比赛ID已存在");
        }
        return competition;
    }
}
