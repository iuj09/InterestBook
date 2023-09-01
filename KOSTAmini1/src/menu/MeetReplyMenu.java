package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import service.MeetReplyService;
import vo.Article;
import vo.MeetReply;

public class MeetReplyMenu extends MENU<MeetReply>{

    public MeetReplyMenu(Scanner sc, SERVICE<MeetReply> service,  Manager manager) {
        super(sc, service, manager);
    }

    @Override
    public void menu() {
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            ((MeetReplyService)service).menu(1, 0);
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 댓글 조회 / 2. 이전으로");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    ((MeetReplyService)service).menu(1, 0);
                    break;
                case 2:
                    flag = false;
                    break;
            }
        }
    }

	@Override
	public void menu1(Article a) {
		// TODO Auto-generated method stub
		
	}
}
