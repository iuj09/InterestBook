package menu;

import java.util.Scanner;

import common.MENU;
import service.ArticleService;
import vo.Article;

public class BoardMenu extends MENU<Article> {
	private static int page = 1;

	public BoardMenu(Scanner sc, ArticleService aService, Menu<?> menu) {
		super(sc, aService, menu);
		this.sc = sc;
		this.service = aService;
		this.menu = menu;
	}

	@Override
    public void menu() {
		boolean flag = true;
//		int page = 1; 
		int m = 0;
		while (flag) {
			((ArticleService) service).printArticle(page);
//			aService.listByPage(page);
//			aService.getAll();
			System.out.println("1.글선택 2.페이지이동 3.글쓰기 4.검색 0.뒤로");
			m = sc.nextInt();
			switch (m) {
			case 1:
				System.out.println("=== 상세보기 ===");
				System.out.print("> 글 번호: ");
				int sel = sc.nextInt();
				sc.nextLine();
				((ArticleService) service).selectArticle(sc, sel, m, null, null);
				break;
			case 2:
				System.out.print("> 이동할 페이지: ");
				page = sc.nextInt();
				sc.nextLine();
//				aService.getArticle(sc);
				break;
			case 3:
				((ArticleService) service).addArticle(sc, 1); // user
//				aService.getByWriterArticle(sc);
				break;
			case 4:
				System.out.println("1.제목으로 검색 2.내용으로 검색 3.제목+내용");
//				aService.getByTitleArticle(sc);
				break;
			case 5:
//				aService.getAll();
				break;
			case 6:
//				aService.editArticle(sc);
				break;
			case 7:
//				aService.delArticle(sc);
				break;
			case 8:
				flag = false;
				break;
			case 9:
				flag = false;
				break;
			case 0:
				flag = false;
				page = 1;
				break;
			}
		}
    }
	
//	public void boardRun(Scanner sc) {
//		boolean flag = true;
////		int page = 1; 
//		int m = 0;
//		while (flag) {
//			aService.printArticle(page);
////			aService.listByPage(page);
////			aService.getAll();
//			System.out.println("1.글선택 2.페이지이동 3.글쓰기 4.검색 0.뒤로");
//			m = sc.nextInt();
//			switch (m) {
//			case 1:
//				System.out.println("=== 상세보기 ===");
//				System.out.print("> 글 번호: ");
//				int sel = sc.nextInt();
//				sc.nextLine();
//				aService.selectArticle(sc, sel, m, null, null);
//				break;
//			case 2:
//				System.out.println("> 이동할 페이지: ");
//				page = sc.nextInt();
//				sc.nextLine();
////				aService.getArticle(sc);
//				break;
//			case 3:
//				aService.addArticle(sc, 1); // user
////				aService.getByWriterArticle(sc);
//				break;
//			case 4:
//				System.out.println("1.제목으로 검색 2.내용으로 검색 3.제목+내용");
////				aService.getByTitleArticle(sc);
//				break;
//			case 5:
////				aService.getAll();
//				break;
//			case 6:
////				aService.editArticle(sc);
//				break;
//			case 7:
////				aService.delArticle(sc);
//				break;
//			case 8:
//				flag = false;
//				break;
//			case 9:
//				flag = false;
//				break;
//			case 0:
//				flag = false;
//				page = 1;
//				break;
//			}
//		}
//	}

}
