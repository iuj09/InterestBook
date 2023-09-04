package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.Article;

public class ArticleDao<T extends Article> extends CRUD<Article> {
	public ArticleDao(Manager manager) {
		super(manager);
		db = DBConnect.getInstance();
	}

	// 글 작성
	@Override
	public void insert(Article a) throws SQLException {
		conn = db.conn();

		sql = "INSERT INTO articles VALUES (articles_NO_sequence.nextval, ?, ?, 0, sysdate, sysdate, ?, ?)";

		ps = conn.prepareStatement(sql);

		ps.setString(1, a.getTitle());
		ps.setString(2, a.getContent());
		ps.setInt(3, a.getWriter());
		ps.setInt(4, a.getCategory());

		ps.executeUpdate();

	}

	// 전체 검색. param으로 HashMap을 받음.
	@Override
	public ArrayList<Article> select(HashMap<String, String> args) throws SQLException {
		ArrayList<Article> list = new ArrayList<>();
		conn = db.conn();
		sql = "SELECT * FROM articles";
		if (args != null) {
			sql += " WHERE ";

			int cnt = args.size() - 1;
			for (Entry<String, String> entry : args.entrySet()) {
				if (entry.getKey().equals("title") || entry.getKey().equals("content")) {
					sql += entry.getKey() + " like '%" + entry.getValue() + "%'";
				} else {
					sql += entry.getKey() + " = '" + entry.getValue() + "'";
				}
				if (cnt > 0) {
					sql += " AND ";
				}
				cnt--;
			}
		}

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 수정
	@Override
	public void update(Article a) throws SQLException {
		conn = db.conn();

		sql = "UPDATE articles SET title = ?, content = ?, e_date = sysdate WHERE no = ?";

		ps = conn.prepareStatement(sql);

		ps.setString(1, a.getTitle());
		ps.setString(2, a.getContent());
		ps.setInt(3, a.getNum());

		ps.executeUpdate();
	}

	// 삭제: DELETE
	@Override
	public void delete(int num) throws SQLException {
		conn = db.conn();
		sql = "DELETE articles WHERE no = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, num);

		ps.executeUpdate();
	}

	// 번호로 게시물 하나 get
	public Article getArticle(int num) throws SQLException {
		Article article = null;
		conn = db.conn();
		sql = "SELECT * FROM articles WHERE no = ?";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, num);
		rs = ps.executeQuery();

		if (rs.next()) {
			article = new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8));
		}

		return article;
	}

	// 이름으로 검색. 서브쿼리 필요
	public ArrayList<Article> selectByWriter(String writer) throws SQLException {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE MEMBER_NO IN (SELECT no FROM members WHERE name like ?)"; // 서브쿼리

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, "%" + writer + "%");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 제목으로 검색
	public ArrayList<Article> selectByTitle(String title) throws SQLException {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE title like ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, "%" + title + "%");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 내용으로 검색
	public ArrayList<Article> selectByContent(String content) throws SQLException {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE content LIKE ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, "%" + content + "%");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 관심사 게시물 반환
	public ArrayList<Article> selectByFavorite(int faId) throws SQLException {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE FAVORITES_NO = ?";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, faId);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 이미 좋아요 함?
	public Boolean isLike(int mId, int aId) throws SQLException {
//		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		sql = "SELECT * FROM articles_like WHERE MEMBERS_NO = ? AND ARTICLES_NO = ?";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, mId);
		ps.setInt(2, aId);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}

	}

	// 좋아요.
	public void likeArticle(int mId, int aId) throws SQLException {
		conn = db.conn();

		sql = "INSERT INTO articles_like VALUES (?, ?)";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, mId);
		ps.setInt(2, aId);

		int cnt = ps.executeUpdate();
//		System.out.println("member" + mId + " 님이 " + aId + " 글을 좋아요함.");
	}

	// 좋아요 취소.
	public void dislikeArticle(int mId, int aId) throws SQLException {
		conn = db.conn();

		sql = "DELETE articles_like WHERE MEMBERS_NO = ? AND ARTICLES_NO = ?";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, mId);
		ps.setInt(2, aId);

		int cnt = ps.executeUpdate();
//		System.out.println("member" + mId + " 님이 " + aId + " 글을 좋아요 취소함.");
	}

	// 좋아요 개수
	public int likeCount(int aId) throws SQLException {
		conn = db.conn();

		sql = "SELECT COUNT(*) FROM articles_like WHERE ARTICLES_NO = ?";

		int cnt = 0;
		ps = conn.prepareStatement(sql);

		ps.setInt(1, aId);

		ResultSet rs = ps.executeQuery();
		rs.next();
		cnt = rs.getInt(1);
		return cnt;
	}

	// object를 arg로 받는 새로운 select1
	public ArrayList<Article> select1(HashMap<String, Object> args) throws SQLException {
		ArrayList<Article> list = new ArrayList<>();
		conn = db.conn();
		sql = "SELECT * FROM articles";
		if (args != null) {
			sql += " WHERE ";

			int cnt = args.size() - 1;
			for (Entry<String, Object> entry : args.entrySet()) {
				if (entry.getKey().equals("title") || entry.getKey().equals("content")) {
					sql += entry.getKey() + " like '%" + entry.getValue() + "%'";
				} else {
					sql += entry.getKey() + " = '" + entry.getValue() + "'";
				}
				if (cnt > 0) {
					sql += " AND ";
				}
				cnt--;
			}
		}

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// search
	public ArrayList<Article> search(HashMap<String, Object> args) throws SQLException {
		ArrayList<Article> list = new ArrayList<>();
		conn = db.conn();
		sql = "SELECT * FROM articles";
		if (args != null) {
			sql += " WHERE ";

			int cnt = args.size() - 1;
			for (Entry<String, Object> entry : args.entrySet()) {
				if (entry.getKey().equals("title") || entry.getKey().equals("content")) {
					sql += entry.getKey() + " like '%" + entry.getValue() + "%'";
				} else {
					sql += entry.getKey() + " = '" + entry.getValue() + "'";
				}
				if (cnt > 0) {
					sql += " OR ";
				}
				cnt--;
			}
		}

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 댓글 개수
	public int repliesCount(int aId) throws SQLException {
		conn = db.conn();

		sql = "SELECT COUNT(*) FROM replies WHERE articles_no = ?";

		int cnt = 0;
		ps = conn.prepareStatement(sql);

		ps.setInt(1, aId);

		ResultSet rs = ps.executeQuery();
		rs.next();
		cnt = rs.getInt(1);
		return cnt;
	}

	// 좋아요한 게시물
	public ArrayList<Article> getLikedArticles(int num) throws SQLException {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE MEMBER_NO = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, num);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// search1. 회원이면 관심사만, 비회원이면 전부. 관리자 처리는 다른 매서드하기.
	// fId가 0이면 전부, 1 이상이면 관심사만.
	public ArrayList<Article> searchJoinMember(HashMap<String, Object> args, int fId) throws SQLException {
		ArrayList<Article> list = new ArrayList<>();
		HashSet<String> like = new HashSet<>() {
			{
				add("title");
				add("content");
				add("name");
			}
		};
		conn = db.conn();
		sql = "SELECT a.*, m.* FROM articles a, members m WHERE a.members_no = m.no";
		if (fId > 0) {
			sql += " AND a.favorites_no = " + fId;
		}
		if (args.size() > 0) {
			sql += " AND (";

			int cnt = args.size() - 1;
			for (Entry<String, Object> entry : args.entrySet()) {
				if (like.contains(entry.getKey())) {
					sql += entry.getKey() + " like '%" + entry.getValue() + "%'";
				} else {
					sql += entry.getKey() + " = '" + entry.getValue() + "'";
				}
				if (cnt > 0) {
					sql += " OR ";
				} else {
					sql += ")";
				}
				cnt--;
			}
		}
		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	@Override
	public void close() throws SQLException {
		if (rs != null)
			rs.close();
		ps.close();
		conn.close();
	}
}