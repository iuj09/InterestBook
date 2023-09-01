package menu;

import java.util.Scanner;
import common.MENU;
import common.Manager;
import vo.Replies;
import service.RepliesService;

public class RepliesMenu extends MENU<Replies> {

	public RepliesMenu(Scanner sc, RepliesService service, Manager manager) {
		super(sc, service, manager);
		this.sc = sc;
        this.service = service;
	}

	@Override
	public void menu() {
		boolean flag = true;
		while (flag) {
			System.out.println("-----------------------------------------------------");
			System.out.println("1.게시물 좋아요 / 2.댓글보기 / 3.댓글작성 / 4.이전");
			System.out.println("-----------------------------------------------------");
			System.out.print(": ");
			int num = sc.nextInt();

			switch (num) {
			case 1:
//				this.manager.getDao("ArticleDao").likeCount();
				break;
			case 2:
				if (((RepliesService) service).hasComment()) {
					menu2();
				}
				break;
			case 3:
				((RepliesService) service).addReplies(sc);
				break;
			case 4:
				flag = false;
				return;
			}
		}
	}

	public void menu2() {
		boolean flag = true;
		while (flag) {
			((RepliesService) service).printAll();
			System.out.println("-----------------------------------------------------");
			System.out.println("1.댓글 좋아요 / 2.댓글수정 / 3.댓글삭제 / 4.이전");
			System.out.println("-----------------------------------------------------");
			System.out.print(": ");
			int num = sc.nextInt();

			switch (num) {
			case 1:
				((RepliesService) service).Like();
				break;
			case 2:
				((RepliesService) service).updateReplies(sc);
				break;
			case 3:
				((RepliesService) service).delReplies(sc);
				break;
			case 4:
				flag = false;
				return;
			}
		}
	}

}
