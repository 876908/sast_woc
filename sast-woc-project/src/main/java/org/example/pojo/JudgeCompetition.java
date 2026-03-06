package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeCompetition implements Serializable {
    private Long id;          // 关联记录id
    private String judgeId;   // 评委用户id(user_code)
    private Long comId;       // 比赛id
}
