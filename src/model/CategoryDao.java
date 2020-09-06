package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import model.mapper.CategoryMapper;

public class CategoryDao {
	private Class<CategoryMapper> cls = CategoryMapper.class;
	private Map<String, Object> map = new HashMap<>();

	// 카테고리 최대 번호
	public int maxnum() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).maxnum();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// 카테고리 추가
	public int insert(Category ca) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(ca);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
	
	// 회원별 카테고리 목록 (메인) 
	public List<Category> list(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// 해당 카테고리 조회
	public Category selectOne(int categorynum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("categorynum",categorynum);
			List<Category> list = session.getMapper(cls).select(map);
			return list.get(0);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// 카테고리명 변경
	public int update(Category ca) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(ca);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// 카테고리 삭제
	public int delete(int categorynum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(categorynum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
}
