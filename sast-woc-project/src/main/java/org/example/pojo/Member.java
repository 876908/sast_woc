package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String studentId;
    private Long teamId;
    private Long academyId;
    private String phone;
    private Integer isCaptain;
}
