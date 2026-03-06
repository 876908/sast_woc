package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamInstructor implements Serializable {
    private Long id;            // 关联记录id
    private Long teamId;        // 队伍id
    private Long instructorId;  // 指导老师id
}
