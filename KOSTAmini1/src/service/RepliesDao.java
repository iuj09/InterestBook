package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import common.DBConnect;
import vo.Replies;

public class RepliesDao<T extends Replies> extends CRUD<Replies> {

	public RepliesDao() {
		db = DBConnect.getInstance();
	}

	// 댓글 추가
	public void insert(Replies r) throws SQLException {
		conn = db.conn();

		sql = "insert into Replies values (replies_no_sequence.nextval,?,sysdate,sysdate,?,?,?)";
		// 마지막 물음표 2개는 Article_no, Member_no 참조해야함
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
	public Replies select(int no) throws SQLException {
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

	// 댓글 보기
	// 시간순, 좋아요순 정렬(추가? // group by절)
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

		sql = "update Replies set content = ?, e_date=sysdate where no=? and member_no=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, r.getContent());
		ps.setInt(2, r.getNo());
		ps.setInt(3, r.getMember_no());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 댓글이 수정됨");
		System.out.println();
	}

	// 댓글 삭제
	public void delete(Replies r) throws SQLException {
		conn = db.conn();
		sql = "delete from Replies where no=? and member_no=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, r.getNo());
		ps.setInt(2, r.getMember_no());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 댓글이 삭제됨");
		System.out.println();
	}

	// 좋아요 추가
	public void updateHeart(Replies r) throws SQLException {
		conn = db.conn();

		sql = "update Replies set heart = ? where no=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, r.getHeart());
		ps.setInt(2, r.getNo());
		int cnt = ps.executeUpdate();

		System.out.println(cnt + "개의 좋아요 추가됨");
		System.out.println();
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
