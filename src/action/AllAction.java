package action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import model.Board;
import model.BoardDao;
import model.Category;
import model.CategoryDao;
import model.Comment;
import model.CommentDao;
import model.Item;
import model.ItemDao;
import model.Member;
import model.MemberDao;

public class AllAction {
	String msg = "";
	String url = "";

	/* ================== 메인 main ===================== */
	// 메인화면 게시글 목록
	public ActionForward main(HttpServletRequest request, HttpServletResponse response) {
		List<Board> review = bdao.mainList(1); // 후기 게시판
		List<Board> inform = bdao.mainList(2); // 특가정보 게시판
		List<Board> free = bdao.mainList(3); // 자유 게시판
		// 오늘 날짜
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sf.format(new Date());

		request.setAttribute("review", review);
		request.setAttribute("inform", inform);
		request.setAttribute("free", free);
		request.setAttribute("today", today);
		return new ActionForward();
	}

	/* ================== 회원 관리 member ================== */
	MemberDao dao = new MemberDao();

	// 회원가입
	public ActionForward join(HttpServletRequest request, HttpServletResponse response) {
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));

		msg = "회원가입 실패";
		url = "joinForm.do";
		// model 클래스

		// 2. Member 객체의 정보를 db에 저장
		int result = dao.insert(mem);
		// 3. 회원가입 성공
		if (result > 0) {
			msg = mem.getId() + "님 회원가입을 완료하였습니다.";
			url = "../main/index.do";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// 아이디 중복 검사
	public ActionForward idCheck(HttpServletRequest request, HttpServletResponse response) {
		String chkid = request.getParameter("id");

		if (dao.idCheck(chkid) > 0) {
			msg = "존재하는 아이디입니다.";
			chkid = "";
		} else {
			msg = "사용 가능한 아이디입니다.";
		}
		url = "idCheckForm.do?chkid=" + chkid;

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		request.setAttribute("chkid", chkid);
		return new ActionForward(false, "../alert.jsp");
	}

	// 로그인
	public ActionForward login(HttpServletRequest request, HttpServletResponse response) {
		msg = "아이디를 확인하세요";
		url = "../main/index.do";
		// 1. 파라미터 저장
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		// 2. db 정보 읽기
		Member mem = new MemberDao().selectOne(id);
		// 3. 아이디, 비밀번호 검증
		if (mem != null) {
			if (pass.equals(mem.getPass())) {
				request.getSession().setAttribute("login", id);
				msg = mem.getName() + "님이 로그인 했습니다.";
			} else {
				msg = "비밀번호가 틀립니다.";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// 로그아웃
	public ActionForward logout(HttpServletRequest request, HttpServletResponse response) {
		msg = "로그아웃되었습니다.";
		url = "../main/index.do";
		request.getSession().invalidate();

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// 아이디 찾기
	public ActionForward id(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		String id = dao.idSearch(name, email);

		if (id != null) { // id 찾은 경우
			String sendId = id.substring(0, id.length() - 2);
			request.setAttribute("sendId", sendId);
			return new ActionForward();
		} else { // 못 찾은 경우
			request.setAttribute("msg", "정보에 맞는 아이디가 없습니다.");
			request.setAttribute("url", "idForm.do");
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 비밀번호 찾기
	public ActionForward pw(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String pass = dao.pwSearch(id, email);
		if (pass != null) {
			String sendPw = pass.substring(2, pass.length());
			request.setAttribute("sendPw", sendPw);
			return new ActionForward();
		} else {
			request.setAttribute("msg", "입력하신 정보를 확인해주세요.");
			request.setAttribute("url", "pwForm.do");
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 회원 정보 보기
	public ActionForward info(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Member mem = new MemberDao().selectOne(id);
			request.setAttribute("mem", mem);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 이미지 업로드
	public ActionForward picture(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("/") + "member/picture/";
		String fname = null;
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs(); // 폴더 생성
			}
			MultipartRequest multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
			// fname : 업로드된 이미지 파일 이름
			fname = multi.getFilesystemName("picture");

			// 섬네일 이미지 생성
			// new File(path+fname) : 업로드된 원본 파일
			// bi : 메모리에 로드 정보
			BufferedImage bi = ImageIO.read(new File(path + fname));
			int width = bi.getWidth() / 3;
			int height = bi.getHeight() / 3;
			// thumb : 빈이미지. 그림이 없는 도화지
			BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// g : 그리기 도구
			Graphics2D g = thumb.createGraphics();
			// 그림 그리기
			// thumb : 이미지 그림
			g.drawImage(bi, 0, 0, width, height, null);
			f = new File(path + "sm_" + fname);
			// thumb 이미지 f 파일로 저장
			ImageIO.write(thumb, "jpg", f); // 이미지 파일에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("fname", fname);
		return new ActionForward();
	}

	// 회원 정보 수정
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Member mem = new Member();
			mem.setId(request.getParameter("id"));
			mem.setPass(request.getParameter("pass"));
			mem.setName(request.getParameter("name"));
			mem.setEmail(request.getParameter("email"));
			mem.setPicture(request.getParameter("picture"));

			msg = "비밀번호가 틀렸습니다.";
			url = "updateForm.do?id=" + mem.getId();
			Member memInfo = dao.selectOne(mem.getId());

			if (login.equals("admin") || mem.getPass().equals(memInfo.getPass())) {
				int result = dao.update(mem);
				if (result > 0) {
					return new ActionForward(true, "info.do?id=" + mem.getId());
				} else {
					msg = "수정을 실패하였습니다.";
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 비밀번호 수정
	public ActionForward password(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			boolean closer = false;
			String login = (String) request.getSession().getAttribute("login");
			String pass = request.getParameter("pass");
			String chgpass = request.getParameter("chgpass");

			Member mem = dao.selectOne(login);

			msg = "비밀번호가 틀렸습니다.";
			url = "passwordForm.do";

			if (pass.equals(mem.getPass())) { // 입력된 비밀번호와 db에 저장된 비밀번호가 같으면
				closer = true;
				if (dao.updatePass(login, chgpass) > 0) {
					msg = "비밀번호가 변경되었습니다.";
					url = "updateForm.do?id=" + login;
				} else {
					msg = "비밀번호 변경시 오류가 발생했습니다.";
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			request.setAttribute("closer", closer);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 회원 탈퇴
	public ActionForward deleteForm(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		id = request.getParameter("id");
		String pass = request.getParameter("pass"); // 파라미터 정보
		msg = null;
		url = null;
		boolean closer = true;
		boolean reload = true;
		if (id.equals("admin")) {
			msg = "관리자는 탈퇴할 수 없습니다.";
			url = "memberList.do";
		} else {
			Member dbmem = dao.selectOne(id); // db정보 조회
			if (login.equals("admin") || pass.equals(dbmem.getPass())) {
				if (dao.delete(id) > 0) { // 삭제 성공
					if (login.equals("admin")) { // 관리자인 경우
						msg = id + " 사용자 강제 탈퇴 성공";
						url = "memberList.do";
					} else { // 일반 사용자인 경우
						msg = id + "님의 회원탈퇴가 완료되었습니다.";
						url = "../main/index.do";
						request.getSession().invalidate(); // 로그아웃
					}
				} else { // 삭제 실패
					msg = id + "님의 탈퇴시 오류 발생.";
					if (login.equals("admin")) { // 관리자인 경우
						url = "memberList.do";
					} else { // 일반 사용자인 경우
						url = "info.do?id=" + id;
					}
				}
			} else { // 일반 사용자의 비밀번호가 틀린 경우
				msg = id + "님의 비밀번호가 틀립니다.";
				url = "deleteForm.do?id=" + id;
				closer = false;
				reload = false;
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		request.setAttribute("closer", closer);
		request.setAttribute("reload", reload);
		return new ActionForward(false, "../alert.jsp");
	}

	// 관리자 - 회원 목록 보기
	public ActionForward memberList(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			Member mem = new MemberDao().selectOne("admin");
			request.setAttribute("mem", mem);
			request.setAttribute("list", new MemberDao().list());
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	/* ================= 로그인 상태 체크 ================ */
	// 회원 로그인 체크
	String login;
	String id;

	private boolean UserLoginCheck(HttpServletRequest request) {
		login = (String) request.getSession().getAttribute("login");
		id = request.getParameter("id");
		if (login == null) {
			request.setAttribute("msg", "로그인 후 가능합니다.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		if (id != null && !id.equals(login) && !login.equals("admin")) {
			request.setAttribute("msg", "본인만 가능합니다.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		return true;
	}

	// 관리자인지 체크
	private boolean AdminLoginCheck(HttpServletRequest request) {
		String login;
		login = (String) request.getSession().getAttribute("login");
		if (login == null) {
			request.setAttribute("msg", "로그인이 필요합니다.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		if (!login.equals("admin")) {
			request.setAttribute("msg", "관리자만 가능합니다.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		return true;
	}

	/* ======================= wishList ========================== */
	// ============ category
	CategoryDao cdao = new CategoryDao();

	// 카테고리 추가
	public ActionForward categoryAdd(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			msg = "추가 실패";
			url = "categoryAddForm.do";

			Category ca = new Category();
			int num = cdao.maxnum();
			ca.setCategorynum(++num);
			ca.setCategoryname(request.getParameter("categoryname"));
			ca.setId(login);

			if (cdao.insert(ca) > 0) {
				msg = ca.getCategoryname() + " 추가 완료";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 카테고리 리스트 (메인)
	public ActionForward categoryList(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			List<Category> list = cdao.list(id);
			request.setAttribute("list", list);
			// 카테고리에 속한 물품 개수
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 카테고리 정보
	public ActionForward categoryInfo(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);
			request.setAttribute("ca", ca);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 카테고리명 수정
	public ActionForward categoryEdit(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			msg = "카테고리명 변경 실패";
			boolean closer = false;
			boolean reload = false;

			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);
			ca.setCategoryname(request.getParameter("categoryname"));

			if (cdao.update(ca) > 0) {
				msg = "카테고리명 변경 완료";
				closer = true;
				reload = true;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("closer", closer);
			request.setAttribute("reload", reload);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 카테고리 삭제
	public ActionForward categoryRemove(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);

			msg = "카테고리 삭제 실패";
			url = "categoryList.do?id=" + login;
			if (cdao.delete(categorynum) > 0) {
				msg = ca.getCategoryname() + " 삭제 완료";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ============== item
	ItemDao idao = new ItemDao();

	// 물품 추가
	public ActionForward itemAdd(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Item item = new Item();
			int num = idao.maxnum();
			boolean closer = false;
			boolean reload = false;
			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			int price;
			if (request.getParameter("price").equals("")) {
				price = 0;
			} else {
				price = Integer.parseInt(request.getParameter("price"));
			}
			item.setItemnum(++num);
			item.setCategorynum(categorynum);
			item.setId(login);
			item.setItemname(request.getParameter("itemname"));
			item.setPrice(price);
			item.setUrl(request.getParameter("url"));
			item.setMemo(request.getParameter("memo"));

			msg = "물품 추가 실패";

			if (idao.insert(item) > 0) {
				msg = item.getItemname() + " 추가 완료";
				closer = true;
				reload = true;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("closer", closer);
			request.setAttribute("reload", reload);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 카테고리별 물품 리스트
	public ActionForward itemList(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);
			List<Item> list;

			if (!request.getParameter("purchase").equals("")) {
				int purchase = Integer.parseInt(request.getParameter("purchase"));
				if (purchase < 2) {
					list = idao.purchaseList(categorynum, purchase);
				} else {
					list = idao.list(categorynum);
				}

			} else {
				list = idao.list(categorynum);
			}
			request.setAttribute("ca", ca);
			request.setAttribute("itemlist", list);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 물품 전체 조회
	public ActionForward itemAllList(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			String id = request.getParameter("id");
			List<Item> itemlist;

			if (!request.getParameter("purchase").equals("")) {
				int purchase = Integer.parseInt(request.getParameter("purchase"));
				if (purchase < 2) {
					itemlist = idao.purchaseAllList(id, purchase);
				} else {
					itemlist = idao.allList(id);
				}

			} else {
				itemlist = idao.allList(id);
			}
			request.setAttribute("itemlist", itemlist);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 물품 정보 조회
	public ActionForward itemInfo(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int itemnum = Integer.parseInt(request.getParameter("itemnum"));
			Item item = idao.selectOne(itemnum);
			Category ca = cdao.selectOne(item.getCategorynum());

			request.setAttribute("ca", ca);
			request.setAttribute("item", item);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 물품 정보 수정
	public ActionForward itemEdit(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int itemnum = Integer.parseInt(request.getParameter("itemnum"));
			Item item = idao.selectOne(itemnum);
			int price;
			if (request.getParameter("price").equals("")) {
				price = 0;
			} else {
				price = Integer.parseInt(request.getParameter("price"));
			}

			msg = "수정 실패";
			url = "itemInfo.do?itemnum=" + itemnum;

			item.setItemname(request.getParameter("itemname"));
			item.setPrice(price);
			item.setUrl(request.getParameter("url"));
			item.setMemo(request.getParameter("memo"));
			item.setPurchase(Integer.parseInt(request.getParameter("purchase")));

			if (idao.update(item) > 0) {
				return new ActionForward(true, url);
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// 물품 삭제
	public ActionForward itemDelete(HttpServletRequest request, HttpServletResponse response) {
		int itemnum = Integer.parseInt(request.getParameter("itemnum"));
		Item item = idao.selectOne(itemnum);

		msg = item.getItemname() + " 삭제 실패";
		url = "itemInfo.do?itemnum" + itemnum;

		if (idao.delete(itemnum) > 0) {
			msg = item.getItemname() + " 삭제";
			url = "itemList.do?categorynum=" + item.getCategorynum() + "&purchase=2";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	/* ======================= 게시판 board ========================= */
	BoardDao bdao = new BoardDao();

	// 게시글 작성 화면
	public ActionForward writeForm(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int type = Integer.parseInt(request.getParameter("type"));
			request.setAttribute("type", type);

			if (request.getParameter("itemnum") != null) {
				int itemnum = Integer.parseInt(request.getParameter("itemnum"));
				Item item = idao.selectOne(itemnum);
				request.setAttribute("item", item);
			}

			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 게시글 작성
	public ActionForward write(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			// 파일
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi;

			String url = "wirteForm.do?id=" + id;

			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
				Board b = new Board();
				int num = bdao.maxnum(); // b table에서 num컬럼의 최대값 리턴. 없는 경우 0으로
				b.setPostnum(++num);
				b.setId(id);
				b.setSubject(multi.getParameter("subject"));
				b.setContent(multi.getParameter("content"));
				b.setFile(multi.getFilesystemName("file"));
				b.setType(Integer.parseInt(multi.getParameter("type")));

				if (bdao.insert(b)) {
					url = "postInfo.do?postnum=" + b.getPostnum();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 이미지 업로드
	public ActionForward imgupload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletContext().getRealPath("/") + "board/imgfile/";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		MultipartRequest multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
		String fileName = multi.getFilesystemName("upload");
		request.setAttribute("fileName", fileName);
		request.setAttribute("CKEditorFuncNum", request.getParameter("CKEditorFuncNum"));
		return new ActionForward(false, "ckeditor.jsp");
	}

	// 내 게시글 목록 조회
	public ActionForward myBoard(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int limit = 10; // 한 페이지당 출력할 게시물 건수
			int boardcount; // boardcount : 등록된 전체 게시물의 건수 또는 검색된 게시물의 건수
			List<Board> list; // list : 화면에 출력할 게시물의 목록
			String id = request.getParameter("id");

			int pageNum = 1; // 페이지 번호 초기화
			try {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			} catch (NumberFormatException e) {
			} // 예외처리 하지 않음

			boardcount = bdao.myBoardCount(id);
			list = bdao.myBoard(id, pageNum, limit);

			int maxpage = (int) ((double) boardcount / limit + 0.95);
			int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // 시작페이지 번호
			int endpage = startpage + 9; // 종료 페이지 번호
			if (endpage > maxpage)
				endpage = maxpage;
			int boardnum = boardcount - (pageNum - 1) * limit;
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sf.format(new Date());

			request.setAttribute("id", id);
			request.setAttribute("boardcount", boardcount);
			request.setAttribute("list", list);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("maxpage", maxpage);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("boardnum", boardnum);
			request.setAttribute("today", today);
			return new ActionForward();
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 게시글 목록 조회
	public ActionForward boardList(HttpServletRequest request, HttpServletResponse response) {
		int boardcount; // boardcount : 등록된 전체 게시물의 건수 또는 검색된 게시물의 건수
		List<Board> list; // list : 화면에 출력할 게시물의 목록
		String bt = ""; // bt : 게시판 유형 이름
		int boardtype = Integer.parseInt(request.getParameter("type"));

		// 한 페이지에 10개씩 출력
		int pageNum = 1; // 페이지 번호 초기화
		int limit = 10; // 한 페이지당 출력할 게시물 건수
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch (NumberFormatException e) {
		} // 예외처리 하지 않음

		// 검색 기능 추가
		String column = request.getParameter("column");
		String find = request.getParameter("find");
		if (column == null || column.trim().contentEquals("")) { // column값이 없는 경우
			column = null;
			find = null;
		}
		if (find == null || find.trim().contentEquals("")) { // 입력값(find)이 없는 경우
			column = null;
			find = null;
		}

		// 게시판 타입이 0이면 전체조회
		if (request.getParameter("type").equals("0")) {// 3. type값이 없으면 전체 게시판 조회
			bt = "전체";
			boardcount = bdao.boardAllCount(column, find);
			list = bdao.allList(pageNum, limit, column, find);
		} else if (boardtype % 10 == 0) {
			// 게시판 유형 => 타입값에 따라 게시판명 다름
			bt = bdao.typeName(boardtype);
			boardcount = bdao.boardCount(boardtype / 10, column, find);
			list = bdao.list(boardtype / 10, pageNum, limit, column, find);
		} else {
			bt = bdao.typeName(boardtype);
			boardcount = bdao.boardTypeCount(boardtype, column, find);
			list = bdao.typeList(boardtype, pageNum, limit, column, find);
		}
		// 하단에 게시글 목록 페이지
		int maxpage = (int) ((double) boardcount / limit + 0.95);
		int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // 시작페이지 번호
		int endpage = startpage + 9; // 종료 페이지 번호
		if (endpage > maxpage)
			endpage = maxpage;
		int boardnum = boardcount - (pageNum - 1) * limit;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sf.format(new Date());

		request.setAttribute("boardtype", boardtype);
		request.setAttribute("bt", bt);
		request.setAttribute("boardcount", boardcount);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("boardnum", boardnum);
		request.setAttribute("today", today);

		return new ActionForward();
	}

	// 게시글 상세 조회
	public ActionForward postInfo(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int num = Integer.parseInt(request.getParameter("postnum"));
			Board b = bdao.selectOne(num);
			// 해당 게시글 작성자와 현재 로그인 아이디가 같지 않으면 조회수 증가
			if (request.getRequestURI().contains("board/postInfo.do") && !b.getId().equals(login)) {
				bdao.readcntAdd(num);
			}
			// 작성자 정보
			Member writer = dao.selectOne(b.getId());
			// 게시판 유형
			int btype = (b.getType() / 10) * 10;
			String bt = bdao.typeName(btype);
			String dt = bdao.typeName(b.getType());
			// 댓글
			List<Comment> clist = codao.list(b.getPostnum());

			request.setAttribute("bt", bt);
			request.setAttribute("dt", dt);
			request.setAttribute("btype", btype);
			request.setAttribute("writer", writer);
			request.setAttribute("b", b);
			request.setAttribute("clist", clist);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 게시글 수정
	public ActionForward postUpdate(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Board b = new Board();
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			MultipartRequest multi;

			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
				b.setPostnum(Integer.parseInt(multi.getParameter("postnum")));
				b.setSubject(multi.getParameter("subject"));
				b.setContent(multi.getParameter("content"));
				b.setFile(multi.getFilesystemName("file"));
				b.setType(Integer.parseInt(multi.getParameter("type")));

				if (b.getFile() == null || b.getFile().equals("")) {
					b.setFile(multi.getParameter("file2"));
				}
				if (bdao.update(b)) {
					url = "postInfo.do?postnum=" + b.getPostnum();
				} else {
					msg = "게시물 수정 실패";
					url = "updateForm.do?postnum=" + b.getPostnum();
					request.setAttribute("msg", msg);
					return new ActionForward(false, "../alert.jsp");
				}
				request.setAttribute("url", url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 공지사항 작성 화면
	public ActionForward noticeForm(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// 공지사항 작성
	public ActionForward noticeWrite(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			// 파일
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi;

			String url = "noticeForm.do?id=" + login;

			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
				Board b = new Board();
				int num = bdao.maxnum(); // b table에서 num컬럼의 최대값 리턴. 없는 경우 0으로

				b.setPostnum(++num);
				b.setId(multi.getParameter("id"));
				b.setSubject(multi.getParameter("subject"));
				b.setContent(multi.getParameter("content"));
				b.setFile(multi.getFilesystemName("file"));
				b.setType(90);

				if (bdao.insert(b)) {
					url = "postInfo.do?postnum=" + b.getPostnum();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			request.setAttribute("url", url);

		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 게시글 삭제
	public ActionForward postDelete(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int postnum = Integer.parseInt(request.getParameter("postnum"));

			Board b = bdao.selectOne(postnum);

			if (bdao.delete(postnum)) {
				url = "boardList.do?type=" + (b.getType() / 10) * 10;
			} else {
				url = "postInfo.do?postnum=" + postnum;
			}
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	/* ======================= 댓글 Comment ========================= */
	CommentDao codao = new CommentDao();

	// 댓글 작성
	public ActionForward reply(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Comment co = new Comment();
			int num = codao.maxnum();
			int postnum = Integer.parseInt(request.getParameter("postnum"));
			co.setCommentnum(++num);
			co.setPostnum(postnum);
			co.setId(login);
			co.setContent(request.getParameter("content"));

			if (codao.insert(co)) {
				url = "postInfo.do?postnum=" + postnum;
			} else {
				url = "boardList.do?type=" + (postnum / 10) * 10;
			}
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 댓글 수정 화면
	public ActionForward commentInfo(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int commentnum = Integer.parseInt(request.getParameter("commentnum"));
			Comment c = codao.selectOne(commentnum);

			request.setAttribute("c", c);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}

	}

	// 댓글 수정
	public ActionForward commentEdit(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			boolean closer = false;
			boolean reload = false;
			int commentnum = Integer.parseInt(request.getParameter("commentnum"));
			String content = request.getParameter("content");

			if (codao.update(commentnum, content)) {
				closer = true;
				reload = true;
			}
			request.setAttribute("closer", closer);
			request.setAttribute("reload", reload);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 댓글 삭제
	public ActionForward commentDelete(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int commentnum = Integer.parseInt(request.getParameter("commentnum"));
			Comment co = codao.selectOne(commentnum);

			codao.delete(commentnum);
			url = "postInfo.do?postnum=" + co.getPostnum();

			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 내가 쓴 댓글 조회
	public ActionForward myComment(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			id = request.getParameter("id");
			int pageNum = 1;
			int limit = 10;

			try {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			} catch (NumberFormatException e) {
			} // 예외처리 하지 않음

			List<Comment> clist = codao.myComment(id, pageNum, limit);
			int replycount = codao.myCommentCount(id);

			int maxpage = (int) ((double) replycount / limit + 0.95);
			int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // 시작페이지 번호
			int endpage = startpage + 9; // 종료 페이지 번호
			if (endpage > maxpage)
				endpage = maxpage;

			request.setAttribute("id", id);
			request.setAttribute("replycount", replycount);
			request.setAttribute("clist", clist);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("maxpage", maxpage);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);

			return new ActionForward();
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// 게시판 이용 현황
	public ActionForward boardUsage(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			List<String> wlist = bdao.writerList();

			request.setAttribute("wlist", wlist);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// ============= 그래프 ==================
	public ActionForward graph(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Integer>> list = bdao.boardgraph();
		request.setAttribute("list", list);
		return new ActionForward();
	}

	public ActionForward graph2(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Integer>> list = bdao.boardgraph2();
		request.setAttribute("list", list);
		return new ActionForward();
	}
	
	public ActionForward graph3(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Integer>> list = bdao.boardgraph3();
		request.setAttribute("list", list);
		return new ActionForward();
	}
}
