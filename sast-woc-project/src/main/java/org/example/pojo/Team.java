package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team implements Serializable {
    private Long id;
    private Long comId;
    private String name;
    private Long captainId;
    private String captainName;
    private Integer status;
    private LocalDateTime createTime;
}
