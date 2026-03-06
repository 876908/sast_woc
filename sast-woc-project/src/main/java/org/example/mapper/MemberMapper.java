package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Member;
import org.example.pojo.VO.MemberVO;

import java.util.List;
@Mapper
public interface MemberMapper {
    @Select("select student_id,member.name,academy.name as academy from member,academy where team_id=#{teamId} and member.academy_id=academy.id")
    List<MemberVO> getByTeamId(Integer teamId);

    @Select("Select id from member where team_id=#{teamId}")
    List<Integer> getStudentIds(Long teamId);

    @Insert("insert into member(id, team_id, student_id, name, academy_id, phone, is_captain) " +
            "values(#{id}, #{teamId}, #{studentId}, #{name}, #{academyId}, #{phone}, #{isCaptain})")
    void insert(Member member);

    @Select("select id, team_id, student_id, name, academy_id, phone, is_captain from member where id = #{id}")
    Member selectById(Long id);

    @Delete("delete from member where id = #{id}")
    void deleteById(Long id);

    @Update("update member set name=#{name}, student_id=#{studentId}, team_id=#{teamId}, " +
            "academy_id=#{academyId}, phone=#{phone}, is_captain=#{isCaptain} where id=#{id}")
    void updateById(Member member);

    @Select("select id from member where team_id = #{teamId}")
    List<Integer> selectIdsByTeamId(Long teamId);

    @Update("update member set team_id = #{newTeamId} where id = #{memberId}")
    void updateTeamId(@Param("memberId") Long memberId, @Param("newTeamId") Long newTeamId);
}

