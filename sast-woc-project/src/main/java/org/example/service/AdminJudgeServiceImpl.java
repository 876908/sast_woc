package org.example.service;
import org.example.constant.RoleConstants;
import org.example.mapper.JudgeCompetitionMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.JudgeCompetition;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${judge.default-password}")
    private String defaultPassword;

    @Override
    @Transactional
    public User addJudge(User user) {
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setRole(RoleConstants.JUDGE);
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(3001, "用户已存在");
        }
        User created = userMapper.selectByUserCode(user.getUserCode());
        JudgeCompetition jc = new JudgeCompetition();
        jc.setJudgeId(user.getUserCode());
        jc.setComId(user.getComId());
        judgeCompetitionMapper.insert(jc);
        return created;
    }

    @Override
    @Transactional
    public void deleteJudge(String userCode) {
        judgeCompetitionMapper.deleteByJudgeId(userCode);
        userMapper.deleteByUserCode(userCode);
    }
}