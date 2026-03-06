package org.example.service;

import org.example.pojo.Competition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionsService {
    Competition createCompetition(Competition competition);
    List<Competition> findAll(Integer role, String userCode);
}
