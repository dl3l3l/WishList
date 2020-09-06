package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Board;

public interface BoardMapper {
	@Select("select ifnull(max(postnum),0) from board")
	int maxnum();

	@Insert("insert into board"
			+ "(postnum,id,subject,content,file,regdate,readcnt,type) "
			+ "value(#{postnum},#{id},#{subject},#{content},#{file},now(),0,#{type})")
	void insert(Board b);

	@Select({"<script>",
			"select ifnull(count(*),0) from board ",
			"<if test='id != null'>where id=#{id}</if>",
			"<choose>",
			"<when test='type != null'>where type like '${type}_'",
				"<if test='col1 != null'>and ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if></when>",	
			"<when test='dtype != null'>where type=#{dtype}",
				"<if test='col1 != null'>and ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if></when>",
			"<when test='type == null'>",
				"<if test='col1 != null'>where ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if></when>",
			"</choose>",
			"</script>"})
	int boardCount(Map<String,Object> map);

	@Select({"<script>",
			"select * from board ",
			"<if test='id != null'>where id=#{id}</if>",
			"<choose>",
			"<when test='type != null'>where type like '${type}_'",
				"<if test='col1 != null'>and ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if>",
				"<if test='file != null'>and file !='' </if></when>",	
			"<when test='dtype != null'>where type=#{dtype}",
				"<if test='col1 != null'>and ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if></when>",
			"<when test='type == null'>",
				"<if test='col1 != null'>where ${col1} like '%${find}%'</if>",
				"<if test='col2 != null'>or ${col2} like '%${find}%'</if></when>",
			"</choose>",
			"ORDER BY regdate desc limit #{start},#{limit}",
			"</script>"})
	List<Board> list(Map<String,Object> map);

	@Select("SELECT * from board " + 
			"where postnum IN (select postnum FROM comment WHERE id=#{value})")
	List<Board> replyPost(String id);
	
	@Select({"<script>",
		"select * from board ",
		"<if test='postnum != null'>where postnum=#{postnum}</if>",
		"</script>"})
	List<Board> select(Map<String,Object> map);
	
	@Update("update board set readcnt=readcnt+1 "
			+ "where postnum=#{value}")
	void readcntAdd(int postnum);

	@Update("update board "
			+ "set subject=#{subject}, content=#{content}, file=#{file}, type=#{type} "
			+ "where postnum=#{postnum}")
	int update(Board b);

	@Delete("delete from board where postnum=#{value}")
	int delete(int postnum);
	
	@Select("select id, count(*) cnt from board group by id "
			+ "having count(*) > 1 order by cnt desc")
	List<Map<String, Integer>> graph();
	
	@Select("SELECT date_format(regdate,'%Y-%m-%d') date, COUNT(*) cnt "
			+ "FROM board GROUP BY date_format(regdate,'%Y-%m-%d')"
			+ "ORDER BY date desc LIMIT 0,7")
	List<Map<String, Integer>> graph2();
	
	@Select("SELECT round(TYPE/10) type, COUNT(*) cnt " + 
			"FROM board GROUP BY round(TYPE/10) ORDER BY type")
	List<Map<String, Integer>> graph3();
	
	@Select("SELECT distinct id FROM board ")
	List<String> writer();
}
	

