package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.BoardMapper;

public class BoardDao {
	Class<BoardMapper> cls = BoardMapper.class;
	Map<String, Object> map = new HashMap<>();

	// 게시물의 최대 번호 리턴
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

	// 게시물 등록
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

	// 게시물 수정
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

	// 게시물 삭제
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

	// 게시글 수
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

	// 전체 게시글 수
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

	// 내 게시글 수
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

	// 카테고리별 게시글 조회
	public List<Board> list(int type, int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("type", type);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // 검색 조건이 여러개일 수도 있으므로 split 이용하여 배열로 받기
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
	// 세부 타입별로 게시글 목록
	public List<Board> typeList(int dtype, int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("dtype", dtype);
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // 검색 조건이 여러개일 수도 있으므로 split 이용하여 배열로 받기
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
	// 메인 화면 최신 게시글
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

	// 전체 게시글 조회
	public List<Board> allList(int pageNum, int limit, String column, String find) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum - 1) * limit);
			map.put("limit", limit);
			if (column != null) { // 검색 조건이 여러개일 수도 있으므로 split 이용하여 배열로 받기
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

	// 내 게시글 조회
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

	// 해당 게시물 조회
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

	// 해당 게시물의 조회 건수 1 증가
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

	// 카테고리명 가져오기
	public String typeName(int type) {
		String t = "";
		switch (type) {
		case 10 : t = "후기"; break;
		case 11 : t = "가전"; break;
		case 12 : t = "가구"; break;
		case 13 : t = "식품/건강"; break;
		case 14 : t = "생필품"; break;
		case 15 : t = "패션"; break;
		case 16 : t = "뷰티"; break;
		case 17 : t = "육아"; break;
		case 18 : t = "기타"; break;
		case 20 : t = "특가정보"; break;
		case 21 : t = "국내"; break;
		case 22 : t = "해외"; break;
		case 30 : t = "자유"; break;
		case 90 : t = "공지사항"; break;
		}
		return t;
	}
	
	// 내가 댓글 단 글
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
	
	// 그래프 - id별
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
	// 그래프 - 날짜별
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
	// 그래프 - 타입별
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
