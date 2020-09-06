package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.ItemMapper;

public class ItemDao {
	private Class<ItemMapper> cls = ItemMapper.class;
	private Map<String, Object> map = new HashMap<>();

	// 물품 최대 번호
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

	// 물품 추가
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

	// 카테고리별 물품 목록
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

	// 구매 여부에 따른 카테고리별 물품 목록
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

	// 물품 전체 목록
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

	// 구매 여부에 따른 카테고리별 물품 목록
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

	// 물품 정보 조회
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

	// 물품 정보 수정
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

	// 물품 삭제
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
