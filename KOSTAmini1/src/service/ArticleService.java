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
		System.out.println("Aricle Service 클래스 생성!");
	}

	// 글 작성.
	public void addArticle(Scanner sc) {
		System.out.println("=== 글 작성 ===");

		System.out.print("title: ");
		String title = sc.next();
		System.out.print("content: ");
		String content = sc.next();

		try {
			dao.insert(new Article(0, title, content, 0, null, null, 0, 0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("글 작성 완료.");
	}

	// 전체 불러와서 출력
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

	// 리스트로 받아온것 그냥 출력
	public void printAll(ArrayList<Article> list) {
		for (Article a : list) {
			System.out.println(a);
		}
	}

	// 페이지네이션하여 게시글 목록 반환(최신순)
	public void getPagedArticles(int page) {

	}

	// 페이지네이션하여 게시글 목록 출력(최신순) // param으로 list?
	public void printArticle(int page) {
		System.out.println("================= 게시판 =================");
		System.out.println("번호   제목               작성자   작성일  ");
		System.out.println("----------------------------------------");

		List<Article> Articles = ((ArticleDao<Article>) dao).selectByFavorite(1); // user 객체
		List<Article> sortedArticles = reverseList(Articles);
		List<Article> pageArticles = pagedList(sortedArticles, page);
		int totalPageCount = (int) Math.ceil((double) sortedArticles.size() / 5);

		if (pageArticles == null) {
			System.out.println("잘못된 페이지 번호입니다. 전체 페이지 수: " + totalPageCount);
			return;
		}
		for (Article a : pageArticles) {
			System.out.printf(" %-3d | %-15s | %3s | %-1s\n", pageArticles.indexOf(a) + 1, a.getTitle(), a.getWriter(),
					a.getwDate());
		} // 날짜 출력 형식

		System.out.println("----------------------------------------");
		System.out.println(page + " / " + totalPageCount);
	}

	// 페이지에서 게시물 선택해서 디테일 출력
	public void selectArticle(Scanner sc, int page, int sel) {
		try {
			List<Article> Articles = ((ArticleDao<Article>) dao).selectByFavorite(1);
			List<Article> sortedArticles = reverseList(Articles);
			List<Article> pageArticles = pagedList(sortedArticles, page);

			if (pageArticles.size() == 0) {
				System.out.println("글이 없습니다.");
				return;
			}

			Article a = pageArticles.get(sel - 1);
			Boolean flag = true;

			while (flag) {
				System.out.println(a.getNum());
				System.out.println("제목  : " + a.getTitle());
				System.out.println("작성일 : " + a.getwDate());
				System.out.println("작성자 : " + a.getWriter());
				System.out.println("좋아요 수: " + ((ArticleDao<Article>) dao).likeCount(a.getNum())); //
				System.out.println("-------------------------------------------");
				System.out.println(a.getContent());
				System.out.println("-------------------------------------------");

				// 댓글 추가

				System.out.println("1.댓글보기 2.좋아요 3.좋아요취소 4.수정 5.삭제 0.목록");
				System.out.print("> 메뉴: ");
				int cmd = sc.nextInt();
				switch (cmd) {
				case 1:
					System.out.println("준비중");
					break;
				case 2:
					if (((ArticleDao<Article>) dao).isLike(1, a.getNum())) {
						System.out.println("이미 좋아요 하였습니다.");
					} else {
						((ArticleDao<Article>) dao).likeArticle(1, a.getNum());
					}
					break;
				case 3:
					if (((ArticleDao<Article>) dao).isLike(1, a.getNum())) {
						((ArticleDao<Article>) dao).dislikeArticle(1, a.getNum());
					} else {
						System.out.println("아직 좋아요를 하지 않았습니다.");
					}
					break;
				case 4:
					// 자신글만 수정

					System.out.println("=== 게시글 수정 ===");

					System.out.print("> new Title: ");
					String title = sc.next();
					a.setTitle(title);
					// 줄 처리
					System.out.print("> new content: ");
					String content = sc.next();
					a.setContent(content);

					dao.update(a);

					System.out.println("글 수정 완료.");
					break;
				case 5:
					dao.delete(a.getNum());
					return;
				case 0:
					flag = false;
					return;
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 게시물 좋아요. 게시글 객체, 유저 no // dao에서 처리
	private void likeArticle(Article a, int i) {
//		((ArticleDao<Article>) dao).likeArticle(1, 1);
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

	// 글 작성. 회원 관심사에 맞게
	public void addArticle(Scanner sc, int faId) {
		System.out.println("=== 글 작성 ===");

		System.out.print("title: ");
		String title = sc.next();
		System.out.print("content: ");
		String content = sc.next();

		try {
			dao.insert(new Article(0, title, content, 0, null, null, faId, 1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("글 작성 완료.");
	}

	// 검색 시작
	public void searchArticle(Scanner sc) {
		List<Article> articles = null;

//		int pageNum = 1;

		Boolean flag = true;

		while (flag) {
			System.out.println("1.제목으로 검색 2.내용으로 검색 3.제목+내용 0.뒤로");
			System.out.print("> 선택: ");
			int sel = sc.nextInt();
			switch (sel) {
			case 1:
				System.out.print("> 검색할 제목: ");
				String title = sc.next();
				articles = ((ArticleDao<Article>) dao).selectByTitle(title);
				articles = reverseList(articles);
				subRun(sc, articles);
				break;
			case 2:
				System.out.print("> 검색할 내용: ");
				String content = sc.next();
				articles = ((ArticleDao<Article>) dao).selectByContent(content);
				articles = reverseList(articles);
				subRun(sc, articles);
				break;
			case 3:
				System.out.println("준비중");
				break;
			case 0:
				flag = false;
				break;
			}
		}
	}

	// 검색후 게시물 리스트. 안쪽에서 페이지 네이션 해야함.
	public void subRun(Scanner sc, List<Article> list) {
		boolean flag = true;
		int m = 0;
		int pageNum = 1;

		int totalPageCount = (int) Math.ceil((double) list.size() / 5);
		while (flag) {

			System.out.println("================= 검색결과 =================");
			System.out.println("번호   제목               작성자   작성일  ");
			System.out.println("----------------------------------------");

			List<Article> pageArticles = pagedList(list, pageNum);

			if (pageArticles == null) {
				System.out.println("잘못된 페이지 번호입니다. 전체 페이지 수: " + totalPageCount);
				return;
			}
			for (Article a : pageArticles) {
				System.out.printf(" %-3d | %-15s | %3s | %-1s\n", pageArticles.indexOf(a) + 1, a.getTitle(),
						a.getWriter(), a.getwDate());
			} // 날짜 출력 형식

			System.out.println("----------------------------------------");
			System.out.println(pageNum + " / " + totalPageCount);

			// 선택
			System.out.println("1.글선택 2.페이지이동 0.뒤로");
			m = sc.nextInt();
			switch (m) {
			case 1:
				System.out.println("=== 상세보기 ===");
				System.out.print("> 글 번호: ");
				int sel = sc.nextInt();
				sc.nextLine();
				showDetailArticle(sc, list.get((pageNum - 1) * 5 + sel - 1));
				break;
			case 2:
				System.out.print("> 이동할 페이지: ");
				pageNum = sc.nextInt();
				sc.nextLine();
				break;
			case 0:
				return;
			}
		}
	}

	// 게시글의 상세페이지
	public void showDetailArticle(Scanner sc, Article a) {
		Boolean flag = true;

		while (flag) {
			System.out.println(a.getNum());
			System.out.println("제목  : " + a.getTitle());
			System.out.println("작성일 : " + a.getwDate());
			System.out.println("작성자 : " + a.getWriter());
			System.out.println("좋아요 수: " + ((ArticleDao<Article>) dao).likeCount(a.getNum())); //
			System.out.println("-------------------------------------------");
			System.out.println(a.getContent());
			System.out.println("-------------------------------------------");

			// 댓글 추가

			System.out.println("1.댓글보기 2.좋아요 3.좋아요취소 4.수정 5.삭제 0.목록");
			System.out.print("> 메뉴: ");
			int cmd = sc.nextInt();
			switch (cmd) {
			case 1:
				System.out.println("준비중");
				break;
			case 2:
				if (((ArticleDao<Article>) dao).isLike(1, a.getNum())) {
					System.out.println("이미 좋아요 하였습니다.");
				} else {
					((ArticleDao<Article>) dao).likeArticle(1, a.getNum());
				}
				break;
			case 3:
				if (((ArticleDao<Article>) dao).isLike(1, a.getNum())) {
					((ArticleDao<Article>) dao).dislikeArticle(1, a.getNum());
				} else {
					System.out.println("아직 좋아요를 하지 않았습니다.");
				}
				break;
			case 4:
				// 자신글만 수정

				System.out.println("=== 게시글 수정 ===");

				System.out.print("> new Title: ");
				String title = sc.next();
				a.setTitle(title);
				// 줄 처리
				System.out.print("> new content: ");
				String content = sc.next();
				a.setContent(content);

				try {
					dao.update(a);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("글 수정 완료.");
				break;
			case 5:
				try {
					dao.delete(a.getNum());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			case 0:
				flag = false;
				return;
			}

		}
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