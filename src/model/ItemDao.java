package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.ItemMapper;

public class ItemDao {
	private Class<ItemMapper> cls = ItemMapper.class;
	private Map<String, Object> map = new HashMap<>();

	// ��ǰ �ִ� ��ȣ
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

	// ��ǰ �߰�
	public int insert(Item item) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(item);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ī�װ��� ��ǰ ���
	public List<Item> list(int categorynum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("categorynum", categorynum);
			return session.getMapper(cls).select(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ���� ���ο� ���� ī�װ��� ��ǰ ���
	public List<Item> purchaseList(int categorynum, int purchase) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("categorynum", categorynum);
			map.put("purchase", purchase);
			return session.getMapper(cls).select(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ��ǰ ��ü ���
	public List<Item> allList(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			return session.getMapper(cls).allList(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ���� ���ο� ���� ī�װ��� ��ǰ ���
	public List<Item> purchaseAllList(String id, int purchase) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("purchase", purchase);
			return session.getMapper(cls).allList(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ��ǰ ���� ��ȸ
	public Item selectOne(int itemnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("itemnum", itemnum);
			List<Item> list = session.getMapper(cls).select(map);
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ��ǰ ���� ����
	public int update(Item item) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(item);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ��ǰ ����
	public int delete(int itemnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(itemnum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

}
