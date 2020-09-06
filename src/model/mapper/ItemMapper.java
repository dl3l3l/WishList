package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Item;

public interface ItemMapper {
	@Select("select IFNULL(max(itemnum),0) from item")
	int maxnum();
	
	@Insert("insert into item " 
			+ "(itemnum,categorynum,id,itemname,price,url,memo,purchase) "
			+ "values(#{itemnum},#{categorynum},#{id},#{itemname},#{price},#{url},#{memo},0)")
	int insert(Item item);
	
	@Select({"<script>",
			"select * from item ",
			"<if test='itemnum !=null'>where itemnum=#{itemnum}</if>",
			"<if test='categorynum !=null'>where categorynum=#{categorynum}</if>",
			"<if test='purchase !=null'>and purchase=#{purchase}</if>",
			"</script>"})
	List<Item> select(Map<String, Object> map);
	
	@Select({"<script>",
			"SELECT c.categoryname 'memo', i.itemnum 'itemnum', i.id 'id',",
			"i.itemname 'itemname', i.price 'price', i.purchase 'purchase' ", 
			"FROM category c, item i ",
			"WHERE c.categorynum = i.categorynum ", 
			"AND i.id = #{id} ",
			"<if test='purchase !=null'>AND purchase=#{purchase}</if>",
			"</script>"})
	List<Item> allList(Map<String, Object> map);

	@Update("update item "
			+ "set itemname=#{itemname}, price=#{price}, url=#{url}, memo=#{memo}, purchase=#{purchase} "
			+ "where itemnum=#{itemnum}")
	int update(Item item);

	@Update("update item set purchase=#{purchase} where itemnum=#{itemnum}")
	int updatePurchase(@Param("itemnum")int itemnum, @Param("purchase")boolean purchase);

	@Delete("delete from item where itemnum=#{itemnum}")
	int delete(@Param("itemnum")int itemnum);
	
}
