package org.example.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamInstructorMapper {
    @Select("select instructor_id from team_instructor where team_id = #{teamId}")
    List<Integer> selectInstructorIdsByTeamId(Long teamId);

    @Delete("delete from team_instructor where team_id = #{teamId} and instructor_id = #{instructorId}")
    void delete(@Param("teamId") Long teamId, @Param("instructorId") Long instructorId);

    @Insert("insert into team_instructor(team_id, instructor_id) values(#{teamId}, #{instructorId})")
    void insert(@Param("teamId") Long teamId, @Param("instructorId") Long instructorId);

    @Select("select count(*) > 0 from team_instructor where instructor_id = #{instructorId}")
    boolean existsByInstructorId(Long instructorId);
}