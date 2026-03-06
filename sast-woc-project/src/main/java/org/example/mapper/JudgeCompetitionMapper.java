package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.JudgeCompetition;

@Mapper
public interface JudgeCompetitionMapper {
    @Insert("insert into judge_competition(judge_id, com_id) values (#{judgeId}, #{comId})")
    void insert(JudgeCompetition judgeCompetition);
    @Delete("delete from judge_competition where judge_id = #{judgeId}")
    void deleteByJudgeId(String judgeId);
    @Select("select count(*) from judge_competition where judge_id = #{judgeId} and com_id = #{comId}")
    int countByJudgeIdAndComId(@Param("judgeId") String judgeId, @Param("comId") Long comId);
}

