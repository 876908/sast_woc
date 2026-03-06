package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String userCode;
    private String password;
    private String name;
    private Long academyId; /*超级管理员可以设置为null 或者默认值*/
    private Long comId;
     /**
     * 0- Captain
     * 1- Judge
     * 2- AcademyAdmin
     * 3- SuperAdmin
     */
    private Integer role;
}