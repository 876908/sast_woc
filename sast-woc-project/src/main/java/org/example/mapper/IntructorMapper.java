package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IntructorMapper {
    @Select("select id from instructor where team_id=#{teamId}")
    List<Integer> getInstructorWorkCode(Long teamId);
    @Update("update instructor set team_id=#{newTeamId} where id=#{instructorId}")
    void updateTeamId(@Param("instructorId") Long instructorId, @Param("newTeamId") Long newTeamId);
    @Update("update instructor set team_id=null where id=#{id}")
    void setNullByTeamId(Long id);
}
