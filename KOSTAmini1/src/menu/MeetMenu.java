package menu;

import java.util.Scanner;

import common.MENU;
import service.MeetService;
import vo.Meet;

public class MeetMenu extends MENU<Meet> {

    public MeetMenu(MeetService service, Scanner sc) {
        super(service, sc);
        this.sc = sc;
        this.service = service;
    }

    @Override
    public void menu() {
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            ((MeetService)service).menu(-1);
            System.out.println("1. 모집 게시글 상세조회 / 2. 모집 게시글 작성 / 3. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    flag = false;
                    break;
            }
        }
    }
}
