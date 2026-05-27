package org.example.provider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class TeamSqlProvider {

    public String selectByComIdAndAcademy(@Param("academyId") Long academyId,
                                          @Param("comId") Integer comId) {
        return new SQL() {{
            SELECT("t.*");
            FROM("team t");
            JOIN("user u ON t.captain_id = u.id");
            WHERE("u.academy_id = #{academyId}");
            if (comId != null) {
                WHERE("t.com_id = #{comId}");
            }
        }}.toString();
    }
}