package org.example.controller;
import org.example.annotation.RequireRoleAnnotation;
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
        judgeScoreService.addScore(score, UserCode);
        return Result.success(score);
    }
    @PatchMapping("/judge")
    public Result updateScore(@RequestBody Score score, @RequestAttribute("UserCode") String UserCode) {
        judgeScoreService.updateScore(score, UserCode);
        return Result.success(score);
    }

}
