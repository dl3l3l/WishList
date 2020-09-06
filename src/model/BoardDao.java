package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.BoardMapper;

public class BoardDao {
	Class<BoardMapper> cls = BoardMapper.class;
	Map<String, Object> map = new HashMap<>();

	// �Խù��� �ִ� ��ȣ ����
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

	// �Խù� ���
	public boolean insert(Board b) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).insert(b);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// �Խù� ����
	public boolean update(Board b) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).update(b);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// �Խù� ����
	public boolean delete(int num) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).delete(num);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// �Խñ� ��
	public int boardCount(int type, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			if (column != null) {
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			map.put("type", type);
			return session.getMapper(cls).boardCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	public int boardTypeCount(int dtype, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			if (column != null) {
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			map.put("dtype", dtype);
			return session.getMapper(cls).boardCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ��ü �Խñ� ��
	public int boardAllCount(String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			if (column != null) {
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			return session.getMapper(cls).boardCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// �� �Խñ� ��
	public int myBoardCount(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			return session.getMapper(cls).boardCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ī�װ��� �Խñ� ��ȸ
	public List<Board> list(int type, int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("type", type);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // �˻� ������ �������� ���� �����Ƿ� split �̿��Ͽ� �迭�� �ޱ�
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	// ���� Ÿ�Ժ��� �Խñ� ���
	public List<Board> typeList(int dtype, int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("dtype", dtype);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // �˻� ������ �������� ���� �����Ƿ� split �̿��Ͽ� �迭�� �ޱ�
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	// ���� ȭ�� �ֽ� �Խñ�
	public List<Board> mainList(int type) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			if(type==1) {
				map.put("file","");
			}
			map.put("type", type);
			map.put("start", 0);
			map.put("limit", 5);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ��ü �Խñ� ��ȸ
	public List<Board> allList(int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // �˻� ������ �������� ���� �����Ƿ� split �̿��Ͽ� �迭�� �ޱ�
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if (cols.length > 1) {
					map.put("col2", cols[1]);
				}
			}
			map.put("find", find);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// �� �Խñ� ��ȸ
	public List<Board> myBoard(String id, int pageNum, int limit) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// �ش� �Խù� ��ȸ
	public Board selectOne(int num) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("postnum", num);
			return session.getMapper(cls).select(map).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// �ش� �Խù��� ��ȸ �Ǽ� 1 ����
	public void readcntAdd(int num) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).readcntAdd(num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
	}

	// ī�װ��� ��������
	public String typeName(int type) {
		String t = "";
		switch (type) {
		case 10 : t = "�ı�"; break;
		case 11 : t = "����"; break;
		case 12 : t = "����"; break;
		case 13 : t = "��ǰ/�ǰ�"; break;
		case 14 : t = "����ǰ"; break;
		case 15 : t = "�м�"; break;
		case 16 : t = "��Ƽ"; break;
		case 17 : t = "����"; break;
		case 18 : t = "��Ÿ"; break;
		case 20 : t = "Ư������"; break;
		case 21 : t = "����"; break;
		case 22 : t = "�ؿ�"; break;
		case 30 : t = "����"; break;
		case 90 : t = "��������"; break;
		}
		return t;
	}
	
	// ���� ��� �� ��
	public List<Board> replyPost(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).replyPost(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// �׷��� - id��
	public List<Map<String, Integer>> boardgraph() {
		SqlSession session = MyBatisConnection.getConnection();
		List<Map<String,Integer>> list = null;
		try {
			list = session.getMapper(cls).graph();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return list;
	}
	// �׷��� - ��¥��
	public List<Map<String, Integer>> boardgraph2() {
		SqlSession session = MyBatisConnection.getConnection();
		List<Map<String,Integer>> list = null;
		try {
			list = session.getMapper(cls).graph2();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return list;
	}
	// �׷��� - Ÿ�Ժ�
		public List<Map<String, Integer>> boardgraph3() {
			SqlSession session = MyBatisConnection.getConnection();
			List<Map<String,Integer>> list = null;
			try {
				list = session.getMapper(cls).graph3();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(session);
			}
			return list;
		}
	
	
	public List<String> writerList() {
		SqlSession session = MyBatisConnection.getConnection();
		List<String> list = null;
		try {
			list = session.getMapper(cls).writer();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return list;
	}
	
}
