package dao;

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
				list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
						rs.getInt(7), rs.getInt(8)));
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
			list.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6),
					rs.getInt(7), rs.getInt(8)));
		}

		return list;
	}

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

	@Override
	public void close() throws SQLException {
		if (rs != null)
			rs.close();
		ps.close();
		conn.close();
		System.out.println("close() 작동!");
	}

}