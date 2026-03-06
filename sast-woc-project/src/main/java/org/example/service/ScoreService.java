package org.example.service;

import org.example.pojo.Score;

public interface ScoreService {
    void addScore(Score score, String currentUserCode);
    void updateScore(Score score, String currentUserCode);
}