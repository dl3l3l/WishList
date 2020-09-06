package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Member;

public interface MemberMapper {
	@Insert("insert into member " 
			+ "(id,pass,name,email,picture) "
			+ "values(#{id},#{pass},#{name},#{email},#{picture})")
	int insert(Member mem);
	
	@Select({"<script>",
			"select * from member ",
			"<if test='id !=null'>where binary id=#{id}</if>",
			"</script>"})
	List<Member> select(Map<String, Object> map);
	
	@Select("select count(*) from member where binary id=#{id}")
	int checkId(@Param("id")String id);

	@Select("select id from member "
			+ "where name=#{name} and email=#{email}")
	String idSearch(@Param("name")String email, @Param("email")String tel);

	@Select("select pass from member "
			+ "where id=#{id} and email=#{email}")
	String pwSearch(@Param("id")String id, @Param("email")String email);

	@Select("select picture from member "
			+ "where id=#{value}")
	String getPicture(String id);
	
	@Update("update member set name=#{name}, email=#{email}, picture=#{picture}"
			+ "where id=#{id}")
	int update(Member mem);

	@Update("update member set pass=#{pass} where id=#{id}")
	int updatePass(@Param("id")String id, @Param("pass")String pass);

	@Delete("delete from member where id=#{id}")
	int delete(@Param("id")String id);

	
	
}
