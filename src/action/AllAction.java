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

	/* ================== ���� main ===================== */
	// ����ȭ�� �Խñ� ���
	public ActionForward main(HttpServletRequest request, HttpServletResponse response) {
		List<Board> review = bdao.mainList(1); // �ı� �Խ���
		List<Board> inform = bdao.mainList(2); // Ư������ �Խ���
		List<Board> free = bdao.mainList(3); // ���� �Խ���
		// ���� ��¥
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sf.format(new Date());

		request.setAttribute("review", review);
		request.setAttribute("inform", inform);
		request.setAttribute("free", free);
		request.setAttribute("today", today);
		return new ActionForward();
	}

	/* ================== ȸ�� ���� member ================== */
	MemberDao dao = new MemberDao();

	// ȸ������
	public ActionForward join(HttpServletRequest request, HttpServletResponse response) {
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));

		msg = "ȸ������ ����";
		url = "joinForm.do";
		// model Ŭ����

		// 2. Member ��ü�� ������ db�� ����
		int result = dao.insert(mem);
		// 3. ȸ������ ����
		if (result > 0) {
			msg = mem.getId() + "�� ȸ�������� �Ϸ��Ͽ����ϴ�.";
			url = "../main/index.do";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// ���̵� �ߺ� �˻�
	public ActionForward idCheck(HttpServletRequest request, HttpServletResponse response) {
		String chkid = request.getParameter("id");

		if (dao.idCheck(chkid) > 0) {
			msg = "�����ϴ� ���̵��Դϴ�.";
			chkid = "";
		} else {
			msg = "��� ������ ���̵��Դϴ�.";
		}
		url = "idCheckForm.do?chkid=" + chkid;

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		request.setAttribute("chkid", chkid);
		return new ActionForward(false, "../alert.jsp");
	}

	// �α���
	public ActionForward login(HttpServletRequest request, HttpServletResponse response) {
		msg = "���̵� Ȯ���ϼ���";
		url = "../main/index.do";
		// 1. �Ķ���� ����
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		// 2. db ���� �б�
		Member mem = new MemberDao().selectOne(id);
		// 3. ���̵�, ��й�ȣ ����
		if (mem != null) {
			if (pass.equals(mem.getPass())) {
				request.getSession().setAttribute("login", id);
				msg = mem.getName() + "���� �α��� �߽��ϴ�.";
			} else {
				msg = "��й�ȣ�� Ʋ���ϴ�.";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// �α׾ƿ�
	public ActionForward logout(HttpServletRequest request, HttpServletResponse response) {
		msg = "�α׾ƿ��Ǿ����ϴ�.";
		url = "../main/index.do";
		request.getSession().invalidate();

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	// ���̵� ã��
	public ActionForward id(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		String id = dao.idSearch(name, email);

		if (id != null) { // id ã�� ���
			String sendId = id.substring(0, id.length() - 2);
			request.setAttribute("sendId", sendId);
			return new ActionForward();
		} else { // �� ã�� ���
			request.setAttribute("msg", "������ �´� ���̵� �����ϴ�.");
			request.setAttribute("url", "idForm.do");
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// ��й�ȣ ã��
	public ActionForward pw(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String pass = dao.pwSearch(id, email);
		if (pass != null) {
			String sendPw = pass.substring(2, pass.length());
			request.setAttribute("sendPw", sendPw);
			return new ActionForward();
		} else {
			request.setAttribute("msg", "�Է��Ͻ� ������ Ȯ�����ּ���.");
			request.setAttribute("url", "pwForm.do");
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// ȸ�� ���� ����
	public ActionForward info(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Member mem = new MemberDao().selectOne(id);
			request.setAttribute("mem", mem);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// �̹��� ���ε�
	public ActionForward picture(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("/") + "member/picture/";
		String fname = null;
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs(); // ���� ����
			}
			MultipartRequest multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
			// fname : ���ε�� �̹��� ���� �̸�
			fname = multi.getFilesystemName("picture");

			// ������ �̹��� ����
			// new File(path+fname) : ���ε�� ���� ����
			// bi : �޸𸮿� �ε� ����
			BufferedImage bi = ImageIO.read(new File(path + fname));
			int width = bi.getWidth() / 3;
			int height = bi.getHeight() / 3;
			// thumb : ���̹���. �׸��� ���� ��ȭ��
			BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// g : �׸��� ����
			Graphics2D g = thumb.createGraphics();
			// �׸� �׸���
			// thumb : �̹��� �׸�
			g.drawImage(bi, 0, 0, width, height, null);
			f = new File(path + "sm_" + fname);
			// thumb �̹��� f ���Ϸ� ����
			ImageIO.write(thumb, "jpg", f); // �̹��� ���Ͽ� ����
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("fname", fname);
		return new ActionForward();
	}

	// ȸ�� ���� ����
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			Member mem = new Member();
			mem.setId(request.getParameter("id"));
			mem.setPass(request.getParameter("pass"));
			mem.setName(request.getParameter("name"));
			mem.setEmail(request.getParameter("email"));
			mem.setPicture(request.getParameter("picture"));

			msg = "��й�ȣ�� Ʋ�Ƚ��ϴ�.";
			url = "updateForm.do?id=" + mem.getId();
			Member memInfo = dao.selectOne(mem.getId());

			if (login.equals("admin") || mem.getPass().equals(memInfo.getPass())) {
				int result = dao.update(mem);
				if (result > 0) {
					return new ActionForward(true, "info.do?id=" + mem.getId());
				} else {
					msg = "������ �����Ͽ����ϴ�.";
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ��й�ȣ ����
	public ActionForward password(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			boolean closer = false;
			String login = (String) request.getSession().getAttribute("login");
			String pass = request.getParameter("pass");
			String chgpass = request.getParameter("chgpass");

			Member mem = dao.selectOne(login);

			msg = "��й�ȣ�� Ʋ�Ƚ��ϴ�.";
			url = "passwordForm.do";

			if (pass.equals(mem.getPass())) { // �Էµ� ��й�ȣ�� db�� ����� ��й�ȣ�� ������
				closer = true;
				if (dao.updatePass(login, chgpass) > 0) {
					msg = "��й�ȣ�� ����Ǿ����ϴ�.";
					url = "updateForm.do?id=" + login;
				} else {
					msg = "��й�ȣ ����� ������ �߻��߽��ϴ�.";
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			request.setAttribute("closer", closer);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ȸ�� Ż��
	public ActionForward deleteForm(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		id = request.getParameter("id");
		String pass = request.getParameter("pass"); // �Ķ���� ����
		msg = null;
		url = null;
		boolean closer = true;
		boolean reload = true;
		if (id.equals("admin")) {
			msg = "�����ڴ� Ż���� �� �����ϴ�.";
			url = "memberList.do";
		} else {
			Member dbmem = dao.selectOne(id); // db���� ��ȸ
			if (login.equals("admin") || pass.equals(dbmem.getPass())) {
				if (dao.delete(id) > 0) { // ���� ����
					if (login.equals("admin")) { // �������� ���
						msg = id + " ����� ���� Ż�� ����";
						url = "memberList.do";
					} else { // �Ϲ� ������� ���
						msg = id + "���� ȸ��Ż�� �Ϸ�Ǿ����ϴ�.";
						url = "../main/index.do";
						request.getSession().invalidate(); // �α׾ƿ�
					}
				} else { // ���� ����
					msg = id + "���� Ż��� ���� �߻�.";
					if (login.equals("admin")) { // �������� ���
						url = "memberList.do";
					} else { // �Ϲ� ������� ���
						url = "info.do?id=" + id;
					}
				}
			} else { // �Ϲ� ������� ��й�ȣ�� Ʋ�� ���
				msg = id + "���� ��й�ȣ�� Ʋ���ϴ�.";
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

	// ������ - ȸ�� ��� ����
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

	/* ================= �α��� ���� üũ ================ */
	// ȸ�� �α��� üũ
	String login;
	String id;

	private boolean UserLoginCheck(HttpServletRequest request) {
		login = (String) request.getSession().getAttribute("login");
		id = request.getParameter("id");
		if (login == null) {
			request.setAttribute("msg", "�α��� �� �����մϴ�.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		if (id != null && !id.equals(login) && !login.equals("admin")) {
			request.setAttribute("msg", "���θ� �����մϴ�.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		return true;
	}

	// ���������� üũ
	private boolean AdminLoginCheck(HttpServletRequest request) {
		String login;
		login = (String) request.getSession().getAttribute("login");
		if (login == null) {
			request.setAttribute("msg", "�α����� �ʿ��մϴ�.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		if (!login.equals("admin")) {
			request.setAttribute("msg", "�����ڸ� �����մϴ�.");
			request.setAttribute("url", "../main/index.do");
			return false;
		}
		return true;
	}

	/* ======================= wishList ========================== */
	// ============ category
	CategoryDao cdao = new CategoryDao();

	// ī�װ� �߰�
	public ActionForward categoryAdd(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			msg = "�߰� ����";
			url = "categoryAddForm.do";

			Category ca = new Category();
			int num = cdao.maxnum();
			ca.setCategorynum(++num);
			ca.setCategoryname(request.getParameter("categoryname"));
			ca.setId(login);

			if (cdao.insert(ca) > 0) {
				msg = ca.getCategoryname() + " �߰� �Ϸ�";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ī�װ� ����Ʈ (����)
	public ActionForward categoryList(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			List<Category> list = cdao.list(id);
			request.setAttribute("list", list);
			// ī�װ��� ���� ��ǰ ����
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// ī�װ� ����
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

	// ī�װ��� ����
	public ActionForward categoryEdit(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			msg = "ī�װ��� ���� ����";
			boolean closer = false;
			boolean reload = false;

			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);
			ca.setCategoryname(request.getParameter("categoryname"));

			if (cdao.update(ca) > 0) {
				msg = "ī�װ��� ���� �Ϸ�";
				closer = true;
				reload = true;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("closer", closer);
			request.setAttribute("reload", reload);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ī�װ� ����
	public ActionForward categoryRemove(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int categorynum = Integer.parseInt(request.getParameter("categorynum"));
			Category ca = cdao.selectOne(categorynum);

			msg = "ī�װ� ���� ����";
			url = "categoryList.do?id=" + login;
			if (cdao.delete(categorynum) > 0) {
				msg = ca.getCategoryname() + " ���� �Ϸ�";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ============== item
	ItemDao idao = new ItemDao();

	// ��ǰ �߰�
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

			msg = "��ǰ �߰� ����";

			if (idao.insert(item) > 0) {
				msg = item.getItemname() + " �߰� �Ϸ�";
				closer = true;
				reload = true;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("closer", closer);
			request.setAttribute("reload", reload);
		}
		return new ActionForward(false, "../alert.jsp");
	}

	// ī�װ��� ��ǰ ����Ʈ
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

	// ��ǰ ��ü ��ȸ
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

	// ��ǰ ���� ��ȸ
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

	// ��ǰ ���� ����
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

			msg = "���� ����";
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

	// ��ǰ ����
	public ActionForward itemDelete(HttpServletRequest request, HttpServletResponse response) {
		int itemnum = Integer.parseInt(request.getParameter("itemnum"));
		Item item = idao.selectOne(itemnum);

		msg = item.getItemname() + " ���� ����";
		url = "itemInfo.do?itemnum" + itemnum;

		if (idao.delete(itemnum) > 0) {
			msg = item.getItemname() + " ����";
			url = "itemList.do?categorynum=" + item.getCategorynum() + "&purchase=2";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	/* ======================= �Խ��� board ========================= */
	BoardDao bdao = new BoardDao();

	// �Խñ� �ۼ� ȭ��
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

	// �Խñ� �ۼ�
	public ActionForward write(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			// ����
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi;

			String url = "wirteForm.do?id=" + id;

			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
				Board b = new Board();
				int num = bdao.maxnum(); // b table���� num�÷��� �ִ밪 ����. ���� ��� 0����
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

	// �̹��� ���ε�
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

	// �� �Խñ� ��� ��ȸ
	public ActionForward myBoard(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int limit = 10; // �� �������� ����� �Խù� �Ǽ�
			int boardcount; // boardcount : ��ϵ� ��ü �Խù��� �Ǽ� �Ǵ� �˻��� �Խù��� �Ǽ�
			List<Board> list; // list : ȭ�鿡 ����� �Խù��� ���
			String id = request.getParameter("id");

			int pageNum = 1; // ������ ��ȣ �ʱ�ȭ
			try {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			} catch (NumberFormatException e) {
			} // ����ó�� ���� ����

			boardcount = bdao.myBoardCount(id);
			list = bdao.myBoard(id, pageNum, limit);

			int maxpage = (int) ((double) boardcount / limit + 0.95);
			int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // ���������� ��ȣ
			int endpage = startpage + 9; // ���� ������ ��ȣ
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

	// �Խñ� ��� ��ȸ
	public ActionForward boardList(HttpServletRequest request, HttpServletResponse response) {
		int boardcount; // boardcount : ��ϵ� ��ü �Խù��� �Ǽ� �Ǵ� �˻��� �Խù��� �Ǽ�
		List<Board> list; // list : ȭ�鿡 ����� �Խù��� ���
		String bt = ""; // bt : �Խ��� ���� �̸�
		int boardtype = Integer.parseInt(request.getParameter("type"));

		// �� �������� 10���� ���
		int pageNum = 1; // ������ ��ȣ �ʱ�ȭ
		int limit = 10; // �� �������� ����� �Խù� �Ǽ�
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch (NumberFormatException e) {
		} // ����ó�� ���� ����

		// �˻� ��� �߰�
		String column = request.getParameter("column");
		String find = request.getParameter("find");
		if (column == null || column.trim().contentEquals("")) { // column���� ���� ���
			column = null;
			find = null;
		}
		if (find == null || find.trim().contentEquals("")) { // �Է°�(find)�� ���� ���
			column = null;
			find = null;
		}

		// �Խ��� Ÿ���� 0�̸� ��ü��ȸ
		if (request.getParameter("type").equals("0")) {// 3. type���� ������ ��ü �Խ��� ��ȸ
			bt = "��ü";
			boardcount = bdao.boardAllCount(column, find);
			list = bdao.allList(pageNum, limit, column, find);
		} else if (boardtype % 10 == 0) {
			// �Խ��� ���� => Ÿ�԰��� ���� �Խ��Ǹ� �ٸ�
			bt = bdao.typeName(boardtype);
			boardcount = bdao.boardCount(boardtype / 10, column, find);
			list = bdao.list(boardtype / 10, pageNum, limit, column, find);
		} else {
			bt = bdao.typeName(boardtype);
			boardcount = bdao.boardTypeCount(boardtype, column, find);
			list = bdao.typeList(boardtype, pageNum, limit, column, find);
		}
		// �ϴܿ� �Խñ� ��� ������
		int maxpage = (int) ((double) boardcount / limit + 0.95);
		int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // ���������� ��ȣ
		int endpage = startpage + 9; // ���� ������ ��ȣ
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

	// �Խñ� �� ��ȸ
	public ActionForward postInfo(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			int num = Integer.parseInt(request.getParameter("postnum"));
			Board b = bdao.selectOne(num);
			// �ش� �Խñ� �ۼ��ڿ� ���� �α��� ���̵� ���� ������ ��ȸ�� ����
			if (request.getRequestURI().contains("board/postInfo.do") && !b.getId().equals(login)) {
				bdao.readcntAdd(num);
			}
			// �ۼ��� ����
			Member writer = dao.selectOne(b.getId());
			// �Խ��� ����
			int btype = (b.getType() / 10) * 10;
			String bt = bdao.typeName(btype);
			String dt = bdao.typeName(b.getType());
			// ���
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

	// �Խñ� ����
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
					msg = "�Խù� ���� ����";
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

	// �������� �ۼ� ȭ��
	public ActionForward noticeForm(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// �������� �ۼ�
	public ActionForward noticeWrite(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			// ����
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi;

			String url = "noticeForm.do?id=" + login;

			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
				Board b = new Board();
				int num = bdao.maxnum(); // b table���� num�÷��� �ִ밪 ����. ���� ��� 0����

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

	// �Խñ� ����
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

	/* ======================= ��� Comment ========================= */
	CommentDao codao = new CommentDao();

	// ��� �ۼ�
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

	// ��� ���� ȭ��
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

	// ��� ����
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

	// ��� ����
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

	// ���� �� ��� ��ȸ
	public ActionForward myComment(HttpServletRequest request, HttpServletResponse response) {
		if (UserLoginCheck(request)) {
			id = request.getParameter("id");
			int pageNum = 1;
			int limit = 10;

			try {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			} catch (NumberFormatException e) {
			} // ����ó�� ���� ����

			List<Comment> clist = codao.myComment(id, pageNum, limit);
			int replycount = codao.myCommentCount(id);

			int maxpage = (int) ((double) replycount / limit + 0.95);
			int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1; // ���������� ��ȣ
			int endpage = startpage + 9; // ���� ������ ��ȣ
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

	// �Խ��� �̿� ��Ȳ
	public ActionForward boardUsage(HttpServletRequest request, HttpServletResponse response) {
		if (AdminLoginCheck(request)) {
			List<String> wlist = bdao.writerList();

			request.setAttribute("wlist", wlist);
			return new ActionForward();
		} else {
			return new ActionForward(false, "../alert.jsp");
		}
	}

	// ============= �׷��� ==================
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
