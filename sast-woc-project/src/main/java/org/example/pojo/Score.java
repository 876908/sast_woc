package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Long id;
    private String judgeId;
    private String workCode;
    private Long teamId;
    private Long comId;
    private Integer score;
    private String comment;
}
