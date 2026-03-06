package org.example.pojo.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamUpdateDTO {
    private Long id;
    private Long comId;
    private String name;
    private Integer captainId;
    private String captainName;
    private Integer status;
    private List<Long> memberIds;
    private List<Long> instructorIds;
    private String createTime;
}
