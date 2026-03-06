package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.mapper.JudgeCompetitionMapper;
import org.example.mapper.ScoreMapper;
import org.example.pojo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private JudgeCompetitionMapper judgeCompetitionMapper;

    @Override
    @Transactional
    public void addScore(Score score, String UserCode) {
        if (!score.getJudgeId().equals(UserCode)) {
            throw new BusinessException(2003, "请求体id与当前身份不一致！");
        }
        int count = judgeCompetitionMapper.countByJudgeIdAndComId(score.getJudgeId(), score.getComId());
        if (count == 0) {
            throw new BusinessException(2003, "你不是这个比赛的评委！");
        }
        scoreMapper.insert(score);
    }
    @Override
    @Transactional
    public void updateScore(Score score, String UserCode) {
        Score existing = scoreMapper.selectById(score.getId());
        if (existing == null) {
            throw new BusinessException(4001, "评分记录不存在！");
        }
        if (!existing.getJudgeId().equals(UserCode)) {
            throw new BusinessException(2003, "无权修改他人的评分！");
        }
        if (!score.getJudgeId().equals(UserCode)) {
            throw new BusinessException(2003, "请求体id与当前身份不一致！");
        }
        int count = judgeCompetitionMapper.countByJudgeIdAndComId(score.getJudgeId(), score.getComId());
        if (count == 0) {
            throw new BusinessException(2003, "无权对此比赛评分！");
        }
        scoreMapper.updateById(score);
    }
}