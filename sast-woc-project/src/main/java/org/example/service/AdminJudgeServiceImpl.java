package org.example.service;
import org.example.mapper.JudgeCompetitionMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.JudgeCompetition;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DuplicateKeyException;
import org.example.exception.BusinessException;
@Service
public class AdminJudgeServiceImpl implements AdminJudgeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JudgeCompetitionMapper judgeCompetitionMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    @Transactional
    public User addJudge(User user) {
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(1);
        try {
                userMapper.insert(user);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(3001, "用户已存在");
            }
        User user1 = userMapper.selectByUserCode(user);
            JudgeCompetition jc = new JudgeCompetition();
            jc.setJudgeId(user.getUserCode());
            jc.setComId(user.getComId());
            judgeCompetitionMapper.insert(jc);
        return user1;
    }
        @Override
        @Transactional
        public void deleteJudge(String userCode) {
            judgeCompetitionMapper.deleteByJudgeId(userCode);
            userMapper.deleteByUserCode(userCode);
        }
    }

