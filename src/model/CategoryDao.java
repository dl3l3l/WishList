package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import model.mapper.CategoryMapper;

public class CategoryDao {
	private Class<CategoryMapper> cls = CategoryMapper.class;
	private Map<String, Object> map = new HashMap<>();

	// ī�װ� �ִ� ��ȣ
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

	// ī�װ� �߰�
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
	
	// ȸ���� ī�װ� ��� (����) 
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
	
	// �ش� ī�װ� ��ȸ
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
	
	// ī�װ��� ����
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

	// ī�װ� ����
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
