package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import vo.Article;
import vo.Member;

public class ArticleDao<T extends Article> extends CRUD<Article> {
	public ArticleDao() {
		System.out.println("ArticleDao 클래스 생성!");
		db = DBConnect.getInstance();
	}

	// 글 작성
	@Override
	public void insert(Article a) throws SQLException {
		conn = db.conn();

		sql = "INSERT INTO articles VALUES (seq_articles.nextval, ?, ?, 0, sysdate, sysdate, ?, ?)";

		ps = conn.prepareStatement(sql);

		ps.setString(1, a.getTitle());
		ps.setString(2, a.getContent());
		ps.setInt(3, a.getWriter());
		ps.setInt(4, a.getCategory());

		int cnt = ps.executeUpdate();
		System.out.println(cnt + " 줄 추가 됨.");

	}

	// 전체 검색. 번호 검색?
	@Override
	public ArrayList<Article> select(HashMap<String, String> args) throws SQLException {
		System.out.println("select");
		ArrayList<Article> list = new ArrayList<>();

		conn = db.conn();

		if (args == null) {
			sql = "SELECT * FROM articles";

			ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
						rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}

			return list;
		}

		sql = "SELECT * FROM articles WHERE ";

		int cnt = args.size() - 1;
		for (Entry<String, String> entry : args.entrySet()) {
			if (cnt > 0)
				sql += entry.getKey() + " = \'" + entry.getValue() + "\' AND ";
			else
				sql += entry.getKey() + " = \'" + entry.getValue() + "\'";
			cnt--;
		}

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
					rs.getDate(6), rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

	// 수정
	@Override
	public void update(Article a) throws SQLException {
		conn = db.conn();

		sql = "UPDATE articles SET title = ?, content = ? WHERE no = ?";

		ps = conn.prepareStatement(sql);

		ps.setString(1, a.getTitle());
		ps.setString(2, a.getContent());
		ps.setInt(3, a.getNum());

		int cnt = ps.executeUpdate();
		System.out.println(cnt + " 줄 수정 됨.");
	}

	// 삭제: DELETE
	@Override
	public void delete(int num) throws SQLException {
		conn = db.conn();
		sql = "DELETE articles WHERE num = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, num);

		int cnt = ps.executeUpdate();
		System.out.println(cnt + " 줄 삭제 됨.");
	}

	// 이름으로 검색. 서브쿼리 필요
	public ArrayList<Article> selectByWriter(String writer) {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE MEMBER_NO IN (SELECT no FROM members WHERE name like ?)"; // 서브쿼리

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + writer + "%");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
						rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	// 제목으로 검색
	public ArrayList<Article> selectByTitle(String title) {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE title like ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + title + "%");
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
						rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}
	
	// 관심사 게시물 반환
	public ArrayList<Article> selectByFavorite(int faId) {
		ArrayList<Article> list = new ArrayList<Article>();

		conn = db.conn();

		String sql = "SELECT * FROM articles WHERE FAVORITE_NO = ?"; // 서브쿼리

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, faId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
						rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}
	
	// 이미 좋아요 함?
	public Boolean isLike(int mId, int aId) {
//		ArrayList<Article> list = new ArrayList<Article>();
		
		conn = db.conn();
		
		sql = "SELECT * FROM board_like WHERE MEMBER_NO = ? AND ARTICLE_NO = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mId);
			ps.setInt(2, aId);
			
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	// 좋아요.
	public void likeArticle(int mId, int aId) {
		conn = db.conn();
		
		sql = "INSERT INTO board_like VALUES (?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, mId);
			ps.setInt(2, aId);

			int cnt = ps.executeUpdate();
			System.out.println("member" + mId + " 님이 " + aId + " 글을 좋아요함.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 좋아요 취소.
	public void dislikeArticle(int mId, int aId) {
		conn = db.conn();
		
		sql = "DELETE board_like WHERE MEMBER_NO = ? AND ARTICLE_NO = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mId);
			ps.setInt(2, aId);
			
			int cnt = ps.executeUpdate();
			System.out.println("member" + mId + " 님이 " + aId + " 글을 좋아요 취소함.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws SQLException {
		if (rs != null)
			rs.close();
		ps.close();
		conn.close();
		System.out.println("close() 작동!");
	}

}