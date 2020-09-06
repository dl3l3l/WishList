package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.CommentMapper;

public class CommentDao {
	Class<CommentMapper> cls = CommentMapper.class;
	Map<String, Object> map = new HashMap<>();

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

	// �Խñ��� ��� ��
	public int commentCount(int postnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("postnum", postnum);
			return session.getMapper(cls).commentCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	// ��� �ۼ�
	public boolean insert(Comment co) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).insert(co);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// ��� ����
	public boolean update(int commentnum, String content) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("commentnum", commentnum);
			map.put("content", content);
			session.getMapper(cls).update(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// ��� ����
	public boolean delete(int commentnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			session.getMapper(cls).delete(commentnum);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// ��� ����Ʈ
	public List<Comment> list(int postnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("postnum", postnum);
			return session.getMapper(cls).select(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public Comment selectOne(int commentnum) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("commentnum", commentnum);
			return session.getMapper(cls).select(map).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ���� �ۼ��� ���
	public List<Comment> myComment(String id, int pageNum, int limit) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			return session.getMapper(cls).select(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// ȸ�� �ۼ� ��� ��
	public int myCommentCount(String id) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			return session.getMapper(cls).commentCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

}
