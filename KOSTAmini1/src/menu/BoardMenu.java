package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import service.ArticleService;
import vo.Article;

public class BoardMenu extends MENU<Article> {
	private static int pageNum = 1, sPageNum = 1;
	private ArticleService aService;
	private RepliesMenu rMenu;
	
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
		aService = (ArticleService) service;
		rMenu = ((RepliesMenu)this.manager.getMenu("RepliesMenu"));
	}

	@Override
	public void menu() {
		
		boolean iFlag = true;
		int iCmd = 0;
		String iMsg = "";
		while (iFlag) {
			// main 메뉴
			HashMap<String, Object> iContext = aService.indexArticle(pageNum);
			aService.printIndex(iContext, iMsg, pageNum);

			ArrayList<Article> articles = (ArrayList<Article>) iContext.get("articles");
			int totalPage = (Integer) iContext.get("totalPage");
			iMsg = "";
			
			System.out.println("1-5.글선택 6.이전페이지 7.다음페이지 8.글쓰기 9.검색 0.뒤로"); // 좋아요순 정렬?
			System.out.print("> 메뉴: ");
			iCmd = sc.nextInt();
			switch (iCmd) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				// 글선택
				if (iCmd > articles.size()) {
					iMsg = RED + "잘못된 글 번호입니다.\n" + RESET;
					break;
				}
				Boolean dFlag = true;
				String dMsg = "";
				while (dFlag) {
					Article article = articles.get(iCmd - 1);
					HashMap<String, Object> dContext = aService.detailArticle(article);
					aService.printDetail(article, dMsg);
					
					Boolean islike = (Boolean) (dContext.get("islike"));
					dMsg = "";
					
					System.out.println("1.댓글보기 2.좋아요" + (islike ? " 취소" : "") + " 3.수정 4.삭제 0.목록"); // 작성자만
					System.out.print("> 메뉴: ");
					int cmd = sc.nextInt();
					switch (cmd) {
					case 1:
						// 댓글보기
						rMenu.menu1(article);
						break;
					case 2:
						// 좋아요
						dMsg = GREEN + aService.likeArticle(article) + RESET;
						break;
					case 3:
						// 수정
						System.out.println("=== 글 수정 ===");

						System.out.print("> new title: ");
						sc.nextLine();
						String title = sc.nextLine();
						System.out.print("> new content: "); // 줄 처리
						String content = sc.nextLine();
						boolean eResult = aService.editArticle(article.getNum(), title, content); // user
						if (eResult) {
							iMsg = GREEN + "성공적으로 게시글이 수정되었습니다.\n" + RESET;
						} else {
							iMsg = RED + "글 수정 실패!!\n" + RESET;
						}
						break;
					case 4:
						// 삭제
						boolean dResult = aService.delArticle(article.getNum()); // user
						if (dResult) {
							iMsg = GREEN + "성공적으로 게시글이 삭제되었습니다.\n" + RESET;
						} else {
							iMsg = RED + "게시글 삭제 실패!!\n" + RESET;
						}
						break;
					case 0:
						dFlag = false;
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
				boolean wResult = aService.addArticle(title, content); // user
				if (wResult) {
					iMsg = GREEN + "성공적으로 게시글이 작성되었습니다.\n" + RESET;
				} else {
					iMsg = RED + "글 작성 실패!!\n" + RESET;
				}
				pageNum = 1; // 해당 게시물로 가기?

				break;
			case 9:
				//////// 검색 시작
				int sCmd = 0;
				System.out.println("==================== 게시글 검색 ====================");
				System.out.println("1.제목으로 검색 2.내용으로 검색 3.제목+내용 4.작성자 5.로그인id 0.뒤로");
				System.out.print("> 명령: ");

				sCmd = sc.nextInt();
				if (sCmd < 4) {
					System.out.print("> 검색할 단어(공백으로 여러단어 구분): ");
					// TODO
				} else if (sCmd == 4) {
					// TODO
					System.out.print("> 검색할 작성자: ");
				} else if (sCmd == 5) {
					// TODO
					System.out.print("> 검색할 로그인id: ");
				}
				sc.nextLine();
				String sWords = sc.nextLine();
				int siCmd = 0;
				boolean sflag = true;
				String sMsg = "";
				while (sflag) {
					// articles 추출
					HashMap<String, Object> sContext = aService.searchArticles(sWords, sCmd,
							sPageNum);
					aService.printIndex(sContext, sMsg, sPageNum);
					
					ArrayList<Article> sArticles = (ArrayList<Article>) sContext.get("articles"); // 팔로우 정렬?
					int sTotalPage = (int) sContext.get("totalPage");
					sMsg = "";

					System.out.println("1-5.글선택 6.이전페이지 7.다음페이지 0.뒤로"); // 좋아요순 정렬?
					System.out.print("> 메뉴: ");
					siCmd = sc.nextInt();
					switch (siCmd) {
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
						// 검색 글 선택
						if (siCmd > sArticles.size()) {
							sMsg = RED + "잘못된 글 번호입니다.\n" + RESET;
							break;
						}
						Boolean sdFlag = true;
						String sdMsg = "";
						while (sdFlag) {
							Article article = sArticles.get(siCmd - 1);
							HashMap<String, Object> detailContext = aService.detailArticle(article);
							aService.printDetail(article, sdMsg);
							
							Boolean islike = (Boolean) (detailContext.get("islike"));
							sdMsg = "";
							
							System.out.println("1.댓글보기 2.좋아요" + (islike ? " 취소" : "") + " 3.수정 4.삭제 0.목록"); // 작성자만
							System.out.print("> 메뉴: ");
							int sdCmd = sc.nextInt();
							switch (sdCmd) {
							case 1:
								// 검색 글 댓글 보기
								rMenu.menu1(article);
								break;
							case 2:
								// 검색 글 좋아요
								sdMsg = GREEN + aService.likeArticle(article) + RESET;
								break;
							case 3:
								// 검색 글 수정
								System.out.println("=== 글 수정 ===");

								System.out.print("> new title: ");
								sc.nextLine();
								String stitle = sc.nextLine();
								System.out.print("> new content: "); // 줄 처리
								String scontent = sc.nextLine();
								boolean eResult = aService.editArticle(article.getNum(), stitle,
										scontent); // user
								if (eResult) {
									sMsg = GREEN + "성공적으로 게시글이 수정되었습니다.\n" + RESET;
								} else {
									sMsg = RED + "글 수정 실패!!\n" + RESET;
								}
								break;
							case 4:
								// 검색 글 삭제
								boolean dResult = aService.delArticle(article.getNum()); // user
								if (dResult) {
									sMsg = GREEN + "성공적으로 게시글이 삭제되었습니다.\n" + RESET;
								} else {
									sMsg = RED + "게시글 삭제 실패!!\n" + RESET;
								}
								break;
							case 0:
								sdFlag = false;
								break;
							}

						}
						break;
					case 6:
						// 검색 글 목록 이전
						if (pageNum > 1) {
							pageNum--;
						} else {
							iMsg = RED + ">> 첫 페이지입니다. <<\n" + RESET;
						}
						break;
					case 7:
						// 검색 글 목록 다음
						if (pageNum < sTotalPage) {
							pageNum++;
						} else {
							iMsg = RED + ">> 마지막 페이지입니다. <<\n" + RESET;
						}
						break;
					case 0:
						iFlag = false;
						sPageNum = 1;
						return;
					}
				}
			case 0:
				iFlag = false;
				pageNum = 1;
				return;
			}
		}
	}

	@Override
	public void menu1(Article a) {
		// TODO Auto-generated method stub
		
	}
}
