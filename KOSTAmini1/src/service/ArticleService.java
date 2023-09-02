package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import dao.ArticleDao;
import dao.MemberDao;
import vo.Article;
import vo.Member;

public class ArticleService extends SERVICE<Article> {
	private MemberService mService;
	private final int perPage = 5;

	public ArticleService(Scanner sc, CRUD<Article> dao, Manager manager) {
		super(sc, dao, manager);
		mService = new MemberService(sc, new MemberDao(manager), manager);

	}

	// add.
	public boolean addArticle(String title, String content) {
		Member user = ((MemberService) mService).nowMember();

		try {
			dao.insert(new Article(0, title, content, 0, null, null, user.getNo(), user.getFavoriteNo()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

//		System.out.println("글 작성 완료.");
		return true;
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

	// edit
	public boolean editArticle(int num, String title, String content) {
		Member user = ((MemberService) mService).nowMember();

		try {
			dao.update(new Article(num, title, content, 0, null, null, user.getNo(), user.getFavoriteNo()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// del
	public boolean delArticle(int num) {
		try {
			dao.delete(num);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// index
	public HashMap<String, Object> indexArticle(int page) {
		HashMap<String, Object> context = new HashMap<>();
		Member user = ((MemberService) mService).nowMember();
		HashMap<String, Object> args = null;
		if (user != null) {
			args = new HashMap<>();
			args.put("FAVORITES_NO", user.getFavoriteNo());
		}

		ArrayList<Article> articles = ((ArticleDao<Article>) dao).select1(args);
		List<Article> sortedArticles = reverseList(articles);
		List<Article> pageArticles = pagedList(sortedArticles, page);
		int totalPageCount = (int) Math.ceil((double) sortedArticles.size() / 5);

		context.put("articles", pageArticles);
		context.put("totalPage", totalPageCount);
		return context;
	}

	// detail. article 객체를 받음
	public HashMap<String, Object> detailArticle(Article a) {
		HashMap<String, Object> context = new HashMap<>();
		Member user = ((MemberService) mService).nowMember();

		int likeCount = ((ArticleDao<Article>) dao).likeCount(a.getNum());
		int repliesCount = ((ArticleDao<Article>) dao).repliesCount(a.getNum());
		boolean islike = ((ArticleDao<Article>) dao).isLike(a.getNum(), a.getNum());

		context.put("likeCount", likeCount);
		context.put("repliesCount", repliesCount);
		context.put("islike", islike);

		return context;
	}

	// search
	public HashMap<String, Object> searchArticles(String s, int cmd, int pageNum) {
		HashMap<String, Object> context = new HashMap<>();
		Member user = ((MemberService) mService).nowMember();
		HashMap<String, Object> args = new HashMap<>();
		;

		ArrayList<Article> articles = new ArrayList<>();

		String[] words = s.split(" ");
		if (cmd < 4) {
			if (cmd == 1 || cmd == 3) {
				for (String w : words) {
					args.put("title", w);
				}
			}
			if (cmd == 2 || cmd == 3) {
				for (String w : words) {
					args.put("content", w);
				}
			}
			articles = ((ArticleDao<Article>) dao).select1(args);
		} else if (cmd == 4) {
			words[0] = ""; // 서브 쿼리 필요
		} else if (cmd == 5) {
			words[0] = ""; // 서브 쿼리 필요
		}

		List<Article> sortedArticles = reverseList(articles);
		List<Article> pageArticles = pagedList(sortedArticles, pageNum);
		int totalPageCount = (int) Math.ceil((double) sortedArticles.size() / 5);

		context.put("articles", pageArticles);
		context.put("totalPage", totalPageCount);
		return context;
	}

	// searchByMId
	public ArrayList<Article> searchByMemberNo(int num) {
		ArrayList<Article> articles;
		HashMap<String, Object> args = new HashMap<>() {
			{
				put("MEMBERS_NO", num);
			}
		};
		articles = ((ArticleDao<Article>) dao).select1(args);

		return articles;
	}

	// 좋아요 토글
	public String likeArticle(Article a) {
		Member user = ((MemberService) mService).nowMember();
		String msg;
		if (((ArticleDao<Article>) dao).isLike(user.getNo(), a.getNum())) {
			((ArticleDao<Article>) dao).dislikeArticle(user.getNo(), a.getNum());
			msg = a.getNum() + " 번 게시글을 좋아요 취소했습니다.\n";
		} else {
			((ArticleDao<Article>) dao).likeArticle(user.getNo(), a.getNum());
			msg = a.getNum() + " 번 게시글을 좋아요 했습니다.\n";
		}
		return msg;
	}

	// 좋아요한 게시물 반환
	public ArrayList<Article> likedArticles(int num) {
		ArrayList<Article> list = new ArrayList<>();

		list = ((ArticleDao<Article>) dao).getLikedArticles(num);

		return list;
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
