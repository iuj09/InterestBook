package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import service.MemberService;
import service.RepliesService;
import vo.Article;
import vo.Member;
import vo.Replies;

public class RepliesMenu extends MENU<Replies> {

	public RepliesMenu(Scanner sc, RepliesService service, Manager manager) {
		super(sc, service, manager);
		this.sc = sc;
		this.service = service;
	}

	@Override
	public void menu1(Article a) {
		Member user = ((MemberService) this.manager.getService("MemberService")).nowMember();
		boolean flag = true;
		while (flag) {
			((RepliesService) service).printAll(a);
			System.out.println("-----------------------------------------------------");
			System.out.println("1.댓글작성 / 2.댓글수정 / 3.댓글삭제 / 4.댓글 좋아요 / 5.이전");
			System.out.println("-----------------------------------------------------");
			System.out.print(": ");
			int num = sc.nextInt();

			switch (num) {
			case 1:
				((RepliesService) service).addReplies(sc, a, user);
				break;
			case 2:
				((RepliesService) service).updateReplies(sc, user, a);
				break;
			case 3:
				((RepliesService) service).delReplies(sc, user, a);
				break;
			case 4:
				((RepliesService) service).Like(a, user);
				break;
			case 5:
				flag = false;
				return;
			}
		}
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub

	}
}
