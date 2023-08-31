package menu;

import java.util.Scanner;

import common.MENU;
import common.SERVICE;
import service.MeetReplyService;
import vo.MeetReply;

public class MeetReplyMenu extends MENU<MeetReply>{

    public MeetReplyMenu(Scanner sc, SERVICE<MeetReply> service, Menu<?> menu) {
        super(sc, service, menu);
        this.sc = sc;
        this.service = service;
        this.menu = menu;
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
}
