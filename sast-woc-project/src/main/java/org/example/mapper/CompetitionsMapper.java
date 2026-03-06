package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Competition;

import java.util.List;

@Mapper
public interface CompetitionsMapper {
    @Select("select id, name, description, max_team_members, min_team_members, work_code, start_time, end_time, review_begin_time, review_end_time, create_time from competition")
    List<Competition> findAll();
    @Select("select competition.id, competition.name, description, max_team_members, min_team_members, work_code, start_time, end_time, review_begin_time, review_end_time, competition.create_time from competition,team,user where competition.id=team.com_id and team.captain_id=user.id and user_code=#{userCode}")
    List<Competition> findAll2(String userCode);
    @Insert("insert into competition(id, name, description, min_team_members, max_team_members, work_code, start_time,end_time, review_begin_time, review_end_time, create_time) values (#{id}, #{name}, #{description}, #{minTeamMembers}, #{maxTeamMembers}, #{workCode},#{startTime}, #{endTime}, #{reviewBeginTime}, #{reviewEndTime}, #{createTime})")
    void insert(Competition competition);
}
