package org.example.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamVO {
    private Long id;
    private Long comId;
    private String name;
    private Long captainId;
    private String captainName;
    private Integer status;
    private List<Integer> memberIds;
    private List<Integer> instructorIds;
}
