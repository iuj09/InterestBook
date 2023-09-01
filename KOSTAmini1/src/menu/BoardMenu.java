package menu;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import dao.MemberDao;
import service.ArticleService;
import service.MemberService;
import vo.Article;
import vo.Meet;

public class BoardMenu extends MENU<Article> {
	private static int pageNum = 1;
	private MemberService mService;
	
	public static final String BLACK = "\u001B[30m";
	public static final String WHITE = "\u001B[37m";
	public static final String RED = "\u001B[31m";
	public static final String YELLOW = "\u001B[33m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String RESET = "\u001B[0m";
	
	public BoardMenu(Scanner sc, SERVICE<Article> service, Manager manager) {
		super(sc, service, manager);
		mService = new MemberService(sc, new MemberDao());
	}

	@Override
	public void menu() {
		boolean flag = true;
		int m = 0;
		String iMsg = "";
		while (flag) {
			HashMap<String, Object> indexContext = ((ArticleService) service).printArticle(pageNum);
			int totalPage = (int) indexContext.get("totalPage");
			List<Article> articles = (List<Article>) indexContext.get("articles"); // 팔로우 정렬?
			System.out.println("================= 게시판 =================");
			System.out.println("번호   제목               작성자   작성일  ");
			System.out.println("----------------------------------------");
			if (indexContext.get("articles") == null) {
				System.out.println("게시물이 없습니다.");
			} else {
				for (Article a : articles) {
					System.out.printf(" %-3d | %-15s | %3s | %-1s\n", articles.indexOf(a) + 1, a.getTitle(),
							a.getWriter(), a.getwDate());
				} // 날짜 출력 형식 // 작성자 출력 형식
			}

			System.out.println("----------------------------------------");
			System.out.println(pageNum + " / " + totalPage);
			System.out.printf(iMsg);
			iMsg = "";
			System.out.println("1-5.글선택 6.이전페이지 7.다음페이지 8.글쓰기 9.검색 0.뒤로"); // 좋아요순 정렬?
			m = sc.nextInt();
			switch (m) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				if (m > articles.size()) {
					break;
				}
				Article article = articles.get(m - 1);
				HashMap<String, Object> detailContext = ((ArticleService) service).detailArticle(article);
				Boolean flag1 = true;
				Boolean islike = (Boolean)(detailContext.get("islike"));
				String dMsg = "";
				while (flag1) {
					System.out.println(article.getNum());
					System.out.println("제목  : " + article.getTitle());
					System.out.println("작성일 : " + article.getwDate());
					System.out.println("작성자 : " + article.getWriter());
					System.out.println("좋아요 수: " + detailContext.get("likeCount")); // context
					System.out.println("댓글 수: " + detailContext.get("repliesCount")); // context
					System.out.println("-------------------------------------------");
					System.out.println(article.getContent());
					System.out.println("-------------------------------------------");
					// 댓글 추가

					System.out.printf(dMsg);
					dMsg = "";
					System.out.println("1.댓글보기 2.좋아요" + (islike ? " 취소" : "") + " 3.수정 4.삭제 0.목록"); // 작성자만
					System.out.print("> 메뉴: ");
					int cmd = sc.nextInt();
					switch (cmd) {
					case 1:
						System.out.println("준비중");
						break;
					case 2:
						// 좋아요
						break;
					case 3:
						// 수정
						System.out.println("=== 글 수정 ===");

						System.out.print("> new title: ");
						String title = sc.next();
						System.out.print("> new content: "); // 줄 처리
						String content = sc.next();
						boolean eResult = ((ArticleService) service).editArticle(article.getNum(), title, content); // user
						if (eResult) {
							iMsg = GREEN + "성공적으로 게시글이 수정되었습니다.\n" + RESET;
						} else {
							iMsg = RED + "글 수정 실패!!\n" + RESET;
						}
						break;
					case 4:
						// 삭제
						boolean dResult = ((ArticleService) service).delArticle(article.getNum()); // user
						if (dResult) {
							iMsg = GREEN + "성공적으로 게시글이 삭제되었습니다.\n" + RESET;
						} else {
							iMsg = RED + "글 삭제 실패!!\n" + RESET;
						}
						break;
					case 0:
						flag1 = false;
						break;
					}

				}
				break;
			case 6:
				// 이전
				if (pageNum > 1) {
					pageNum--;
				} else {
					iMsg = RED + ">> 첫 페이지입니다. <<\n" + RESET;
				}
				break;
			case 7:
				// 다음
				if (pageNum < totalPage) {
					pageNum++;
				} else {
					iMsg = RED + ">> 마지막 페이지입니다. <<\n" + RESET;
				}
				break;
			case 8:
				// 글쓰기
				System.out.println("=== 글 작성 ===");

				System.out.print("> title: ");
				String title = sc.next();
				System.out.print("> content: "); // 줄 처리
				String content = sc.next();
				boolean wResult = ((ArticleService) service).addArticle(title, content); // user
				if (wResult) {
					iMsg = GREEN + "성공적으로 게시글이 작성되었습니다.\n" + RESET;
				} else {
					iMsg = RED + "글 작성 실패!!\n" + RESET;
				}
				pageNum = 1; // 해당 게시물로 가기?
				
				break;
			case 9:
				// 검색
				System.out.println("=== 제목/ 내용 검색 ===");
				
//				HashMap<String, Object> sArgs = new HashMap<>();
				
				System.out.print("> 검색할 단어: ");
				sc.nextLine();
				String sWords = sc.nextLine();
				
				HashMap<String, Object> sContext = ((ArticleService) service).searchArticles(sWords);
				
				System.out.println(sContext.get("call"));
				System.out.println("준비중");
			case 0:
				flag = false;
				pageNum = 1;
				return;
			}
		}
	}
}
