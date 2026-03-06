package org.example.pojo.DTO;

import lombok.Data;

@Data
public class MemberAddDTO {
    private Long id;           // 成员ID（必需，前端提供）
    private String name;       // 姓名（必需）
    private String studentId;  // 学号（必需）
    private Long teamId;       // 队伍ID（必需）
    private Long academyId;    // 学院ID（必需）
    private String phone;      // 电话（必需）
    private Integer isCaptain; // 是否队长（必需）
}
