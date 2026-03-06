package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Score;

@Mapper
public interface ScoreMapper {
    @Insert("insert into score(id, judge_id, work_code, team_id, com_id, score, comment) " +
            "values(#{id},#{judgeId}, #{workCode}, #{teamId}, #{comId}, #{score}, #{comment})")
    void insert(Score score);

    @Select("select * from score where id = #{id}")
    Score selectById(Long id);

    @Update("update score set id=#{id}, judge_id=#{judgeId}, work_code=#{workCode}, team_id=#{teamId}, " +
            "com_id=#{comId}, score=#{score}, comment=#{comment} where id=#{id}")
    void updateById(Score score);
}
