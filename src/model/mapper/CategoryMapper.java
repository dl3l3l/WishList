package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Category;

public interface CategoryMapper {
	@Select("select IFNULL(max(categorynum),0) from category")
	int maxnum();
	
	@Insert("insert into category " 
			+ "(categorynum,id,categoryname) "
			+ "values(#{categorynum},#{id},#{categoryname})")
	int insert(Category ca);
	
	@Select({"<script>",
			"select * from category ",
			"<if test='id !=null'>where binary id=#{id}</if>",
			"<if test='categorynum !=null'>where binary categorynum=#{categorynum}</if>",
			"</script>"})
	List<Category> select(Map<String, Object> map);
	
	@Select("SELECT c.categorynum 'categorynum', c.categoryname'categoryname', ifnull(count(i.itemnum),0) 'id' " + 
			"FROM category c left join item i " + 
			"ON c.categorynum= i.categorynum " + 
			"WHERE c.id = #{id} " + 
			"GROUP BY categorynum")
	List<Category> list(@Param("id")String id);


	@Update("update category set categoryname=#{categoryname}"
			+ "where categorynum=#{categorynum}")
	int update(Category ca);

	@Delete("delete from Category where categorynum=#{categorynum}")
	int delete(@Param("categorynum")int categorynum);
	
}
