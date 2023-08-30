package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import common.SERVICE;
import dao.ArticleDao;
import vo.Article;

public class ArticleService extends SERVICE<Article> {
	public ArticleService(Scanner sc, ArticleDao<Article> dao) {
		super(sc, dao);
		this.sc = sc;
		this.dao = dao;
		System.out.println("Aricle Service 클래스 생성!");
	}

	// 글 작성.
	public void addArticle(Scanner sc) {
		System.out.println("=== 글 작성 ===");

		System.out.print("title:");
		String title = sc.next();
		System.out.print("content:");
		String content = sc.next();

		try {
			dao.insert(new Article(0, title, content, 0, null, null, 0, 0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("글 작성 완료.");
	}

	public void selectArticle() {
		ArrayList<Article> articles;

		try {
			articles = dao.select(null);
			printAll(articles);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 전체 출력
	public void printAll(ArrayList<Article> list) {
		for (Article a : list) {
			System.out.println(a);
		}
	}

	// 페이지네이션하여 게시글 목록 반환(최신순)
	public void getPagedArticles(int page) {

	}

	// 페이지네이션하여 게시글 목록 출력(최신순)
	public void printArticle(int page) {
		System.out.println("================= 게시판 =================");
		System.out.println("번호   제목               작성자   작성일  ");
		System.out.println("----------------------------------------");

		try {
			List<Article> Articles = dao.select(null);
			List<Article> sortedArticles = reverseList(Articles);
			List<Article> pageArticles = pagedList(sortedArticles, page);
			int totalPageCount = (int) Math.ceil((double) sortedArticles.size() / 5);

			if (pageArticles == null) {
				System.out.println("잘못된 페이지 번호입니다. 전체 페이지 수: " + totalPageCount);
				return;
			}
			for (Article a : pageArticles) {
				System.out.printf(" %-3d | %-15s | %3s | %-1s\n", pageArticles.indexOf(a) + 1, a.getTitle(),
						a.getWriter(), a.getwDate());
			} // 날짜 출력 형식

			System.out.println("----------------------------------------");
			System.out.println(page + " / " + totalPageCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 페이지에서 게시물 선택
	public void selectArticle(Scanner sc, int page, int num, List<Article> List, HashMap<String, String> args) {
		try {
			List<Article> Articles = dao.select(args);
			List<Article> sortedArticles = reverseList(Articles);
			List<Article> pageArticles = pagedList(sortedArticles, page);

			if (pageArticles.size() == 0) {
				System.out.println("글이 없습니다.");
				return;
			}

			Article a = pageArticles.get(num - 1);
			System.out.println(a.getNum());
			System.out.println("제목  : " + a.getTitle());
			System.out.println("작성일 : " + a.getwDate());
			System.out.println("작성자 : " + a.getWriter());
			System.out.println("-------------------------------------------");
			System.out.println(a.getContent());
			System.out.println("-------------------------------------------");

			// 댓글 추가

			System.out.println("1.수정 2.삭제 3.목록");
			System.out.print("> 메뉴: ");
			int cmd = sc.nextInt();
			switch (cmd) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 원본list와 페이지num을 param으로 받아서 페이지네이션된 리스트 반환
	public static <T> List<T> pagedList(List<T> list, int page) {
		List<T> pagedList = new ArrayList<>(5);

		int perPage = 5;
		int startArticleIndex = (page - 1) * perPage;
		int endArticleIndex = Math.min(page * perPage, list.size());

		if (startArticleIndex >= endArticleIndex || startArticleIndex < 0) {
			return null;
		}

		pagedList = list.subList(startArticleIndex, endArticleIndex);

		return pagedList;
	}

	// 정렬이 반대인 복사본 리스트를 반환.
	public static <T> List<T> reverseList(List<T> list) {
		List<T> reverse = new ArrayList<>(list.size());

		for (int i = list.size() - 1; i >= 0; i--) {
			reverse.add(list.get(i));
		}
		return reverse;
	}

}

//public class ArticleService {
//	private ArticleDao<Article> aDao;
//
//	public ArticleService() {
//		aDao = new ArticleDao<>();
//	}
//
//	
//	
//	public void test() {
//		// 게시물 1개 만들기
//		try {
//			aDao.insert(new Article(0, "title1", "content1", 0, null, null, 1, 1));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}