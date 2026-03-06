package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Team;
import org.example.pojo.VO.TeamVO;
import org.example.provider.TeamSqlProvider;

import java.util.List;

@Mapper
public interface TeamMapper {
    @Select("select team.id, team.com_id, team.name, captain_id, captain_name, status from team,`sast-woc`.user where captain_id=user.id and user.id=(select id from user where user.user_code=#{UserCode})")
    TeamVO getByCaptain(String UserCode);

    @Update("update team set com_id=#{comId}, name=#{name}, captain_id=#{captainId}, captain_name=#{captainName}, status=#{status} where id=#{id}")
    void updateById(Team team);

    @Select("select id, com_id, name, captain_id, captain_name, status, create_time from team where id = #{id}")
    Team selectById(Long id);

    @SelectProvider(type = TeamSqlProvider.class, method = "selectByComIdAndAcademy")
    List<Team> selectByComIdAndAcademy(@Param("academyId") Long academyId,
                                       @Param("comId") Integer comId);





}
