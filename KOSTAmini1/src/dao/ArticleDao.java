package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import common.DBConnect;
import vo.Article;

public class ArticleDao<T extends Article> extends CRUD<Article> {
	public ArticleDao() {
		db = DBConnect.getInstance();
	}

	// 글 작성
	@Override
	public void insert(Article a) throws SQLException {
		conn = db.conn();

		sql = "INSERT INTO articles VALUES (seq_articles.nextval, ?, ?, sysdate, sysdate, ?, ?)";

		ps = conn.prepareStatement(sql);

		ps.setString(1, a.getTitle());
		ps.setString(2, a.getContent());
		ps.setString(3, a.getWriter());
		ps.setInt(4, a.getCategory());

		int cnt = ps.executeUpdate();
		System.out.println(cnt + " 줄 추가 됨.");

	}

	@Override
  public ArrayList<Article> select(HashMap<String, String> args) throws SQLException {
		return null;
	}

  @Override
  public void update(Article a) throws SQLException {
  	
  }

  // 삭제: DELETE
  @Override
  public void delete(int num) throws SQLException {
  	
  }

  @Override
  public void close() throws SQLException {
      if (rs != null)
          rs.close();
      ps.close();
      conn.close();
  }

}