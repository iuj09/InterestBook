package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import vo.Article;

public class ArticleDaoTest {

	public static void main(String[] args) throws SQLException {
		ArticleDao<Article> aDao = new ArticleDao<>();

		// 게시물 목록
		ArrayList<Article> list1 = aDao.select(null);
		System.out.println(list1);
		for (Article a : list1) {
			System.out.println(a);
		}
		
//		// 게시물 20개 만들기
//		for (int i = 1; i <= 22; i += 2) {
//			aDao.insert(new Article(0, "title" + i, "content" + i, 0, null, null, 1, 1));
//			aDao.insert(new Article(0, "title" + (i + 1), "content" + (i + 1), 0, null, null, 2, 2));
//		}

//		
//		// 게시물 1개 조회
//		System.out.println("=== 1번 게시글 조회 ===");
//		System.out.println(aDao.select(1));
//
//		// 작성자 aaa 검색
//		System.out.println("=== 작성자 \"aaa\" 검색 ===");
//		ArrayList<Article> list2 = aDao.selectByWriter("aaa");
//		for (Article a : list2) {
//			System.out.println(a);
//		}
//		
//		// 제목 le1 검색
//		System.out.println("=== 제목 \"le1\" 검색 ===");
//		ArrayList<Article> list3 = aDao.selectByTitle("le1");
//		for (Article a : list3) {
//			System.out.println(a);
//		}
	}
}