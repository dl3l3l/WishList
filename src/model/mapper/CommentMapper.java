package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Comment;

public interface CommentMapper {
	@Select("select ifnull(max(commentnum),0) from comment")
	int maxnum();
	
	@Select({"<script>",
			"SELECT IFNULL(COUNT(*),0) FROM comment",
			"<if test='postnum != null'>WHERE postnum=#{postnum}</if>",
			"<if test='id != null'>WHERE id=#{id}</if>",
			"</script>"})
	int commentCount(Map<String, Object> map);
	
	@Insert("insert into comment " 
			+ "(commentnum,postnum,id,content,regdate) "
			+ "values(#{commentnum},#{postnum},#{id},#{content},now())")
	int insert(Comment co);
	
	@Select({"<script>",
			"select * from comment ",
			"<if test='id !=null'>where binary id=#{id} ORDER BY regdate desc limit #{start},#{limit}</if>",
			"<if test='postnum !=null'>where postnum=#{postnum}</if>",
			"<if test='commentnum !=null'>where commentnum=#{commentnum}</if>",
			"</script>"})
	List<Comment> select(Map<String, Object> map);
	
	@Update("update comment set content=#{content}"
			+ "where commentnum=#{commentnum}")
	int update(Map<String, Object> map);
	
	@Delete("delete from comment where commentnum=#{commentnum}")
	int delete(@Param("commentnum")int commentnum);

}
