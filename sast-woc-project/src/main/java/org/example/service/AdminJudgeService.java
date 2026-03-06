package org.example.service;

import org.example.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminJudgeService {
    User addJudge(User user);
    void deleteJudge(String userCode);
}
