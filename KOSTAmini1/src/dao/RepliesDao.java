package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.Replies;

public class RepliesDao<T extends Replies> extends CRUD<Replies> {

	public RepliesDao(Manager manager) {
		super(manager);
		db = DBConnect.getInstance();
	}

	// 댓글 추가
	public void insert(Replies r) throws SQLException {
		conn = db.conn();

		sql = "insert into Replies values (replies_no_sequence.nextval,?,sysdate,sysdate,?,?,?)";

		ps = conn.prepareStatement(sql);
		ps.setString(1, r.getContent());
		ps.setInt(2, r.getHeart());
		ps.setInt(3, r.getArticle_no());
		ps.setInt(4, r.getMember_no());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 댓글이 작성됨");
		System.out.println();
	}

	// 댓글 번호로 검색
	public Replies selectByNo(int no) throws SQLException {
		conn = db.conn();

		sql = "select * from Replies where no = ?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, no);
		rs = ps.executeQuery();
		if (rs.next()) {
			return new Replies(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6),
					rs.getInt(7));
		}
		return null;
	}

	// 게시글 번호로 검색
	public ArrayList<Replies> selectByArtNo(int no) throws SQLException {
		ArrayList<Replies> list = new ArrayList<Replies>();
		conn = db.conn();

		sql = "select * from Replies where articles_no = ?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, no);
		rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Replies(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7)));
		}
		return list;
	}

	// 해당 게시글에 있는 댓글만 리스트에 담기
	public ArrayList<Replies> selectByArticle(int no) throws SQLException {
		ArrayList<Replies> list = new ArrayList<Replies>();
		conn = db.conn();

		sql = "select * from Replies where articles_no = ?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, no);
		rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new Replies(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7)));
		}
		return list;
	}

	// 댓글 보기
	public ArrayList<Replies> selectAll() throws SQLException {
		ArrayList<Replies> list = new ArrayList<Replies>();
		conn = db.conn();

		sql = "select * from Replies";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new Replies(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7)));
		}
		return list;
	}

	// 댓글 수정
	public void update(Replies r) throws SQLException {
		conn = db.conn();

		sql = "update Replies set content = ?, e_date = sysdate where no= ?and articles_no=? and members_no=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, r.getContent());
		ps.setInt(2, r.getNo());
		ps.setInt(3, r.getArticle_no());
		ps.setInt(4, r.getMember_no());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 댓글이 수정됨");
		System.out.println();
	}

	// 댓글 삭제
	public void delete(Replies r) throws SQLException {
		conn = db.conn();
		sql = "delete from Replies where no=? and members_no=? and articles_no=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, r.getNo());
		ps.setInt(2, r.getMember_no());
		ps.setInt(3, r.getArticle_no());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 댓글이 삭제됨");
		System.out.println();
	}

	// 좋아요 카운트추가
	public void updateHeart(Replies r) throws SQLException {
		conn = db.conn();

		sql = "update Replies set heart = ? where no=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, r.getHeart());
		ps.setInt(2, r.getNo());
		int cnt = ps.executeUpdate();
	}

	// 좋아요 중복 체크
	public boolean isLike(int mId, int aId, int rId) {
		conn = db.conn();

		sql = "SELECT * FROM REPLIES_like WHERE MEMBERS_NO = ? AND ARTICLES_NO = ? AND REPLIES_NO = ?";
		try {
			ps = conn.prepareStatement(sql);
			// 멤버 번호, 게시글 번호, 댓글 번호
			ps.setInt(1, mId);
			ps.setInt(2, aId);
			ps.setInt(3, rId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 좋아요 추가
	public void likeReply(int mId, int aId, int rId) {
		conn = db.conn();

		sql = "INSERT INTO REPLIES_like VALUES (?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, mId);
			ps.setInt(2, aId);
			ps.setInt(3, rId);

			int cnt = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 좋아요 취소
	public void dislikeReply(int mId, int aId, int rId) {
		conn = db.conn();

		sql = "DELETE REPLiES_like WHERE MEMBERS_NO = ? AND ARTICLES_NO = ? AND REPLIES_NO = ?";

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, mId);
			ps.setInt(2, aId);
			ps.setInt(3, rId);

			int cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		if (rs != null)
			rs.close();
		ps.close();
		conn.close();
	}

	@Override
	public ArrayList<Replies> select(HashMap<String, String> args) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int no) throws SQLException {
		// TODO Auto-generated method stub

	}

}
