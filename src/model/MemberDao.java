package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import model.mapper.MemberMapper;

public class MemberDao {
	private Class<MemberMapper> cls = MemberMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	// ȸ�� ����
	public int insert(Member mem) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(mem);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
	
	// ���̵� �ߺ� üũ
	public int idCheck(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).checkId(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
	
	// ȸ�� ��ȸ
	public Member selectOne(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			List<Member> list = session.getMapper(cls).select(map);
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	

	// ȸ�� ��� ����
	public List<Member> list() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).select(null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// id ã��
	public String idSearch(String name,String email) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).idSearch(name,email);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// pw ã��
	public String pwSearch(String id, String email) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).pwSearch(id,email);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// picture �� �޾ƿ���
	public String getPicture(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).getPicture(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// ȸ�� ���� ����
	public int update(Member mem) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(mem);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ��й�ȣ ����
	public int updatePass(String id, String pass) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).updatePass(id,pass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ȸ�� Ż��
	public int delete(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
}
