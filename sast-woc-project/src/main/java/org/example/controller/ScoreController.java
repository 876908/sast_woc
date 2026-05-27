package org.example.controller;
import org.example.annotation.RequireRoleAnnotation;
import org.example.exception.BusinessException;
import org.example.pojo.Score;
import org.example.service.ScoreService;
import org.example.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireRoleAnnotation.RequireRole(1)
public class ScoreController {
    @Autowired
    private ScoreService judgeScoreService;

    @PostMapping("/judge")
    public Result addScore(@RequestBody Score score, @RequestAttribute("UserCode") String UserCode) {
        validateScore(score);
        judgeScoreService.addScore(score, UserCode);
        return Result.success(score);
    }

    @PatchMapping("/judge")
    public Result updateScore(@RequestBody Score score, @RequestAttribute("UserCode") String UserCode) {
        validateScore(score);
        judgeScoreService.updateScore(score, UserCode);
        return Result.success(score);
    }

    private void validateScore(Score score) {
        if (score.getScore() == null || score.getScore() < 0 || score.getScore() > 100) {
            throw new BusinessException(1001, "分数必须在0-100之间");
        }
    }
}