package menu;

import java.util.Scanner;

import common.MENU;
import common.SERVICE;
import service.MeetService;
import vo.Meet;

public class MeetMenu extends MENU<Meet> {

    public MeetMenu(Scanner sc, SERVICE<Meet> service, Menu<?> menu) {
        super(sc, service, menu);
        this.sc = sc;
        this.service = service;
        this.menu = menu;
    }

    /**
     * Service를 사용하려면 ((이름Service)service) 로 사용
     * 여러 Service를 담아서 사용하기 위해 최상단 클래스 SERVICE 클래스 이용
     * 
     * 다른 클래스 MENU를 사용하려면 case3 처럼 Menu 객체의 list.get(?).menu();를 이용
     * ? : Menu클래스의 menuList 참고
     */
    @Override
    public void menu() {
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집글 출력 / 2. 모집글 작성 / 3. 게시판 / 4. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    ((MeetService)service).menu(-1);
                    break;
                case 2:
                    ((MeetService)service).menu(2);
                    break;
                case 3:
                    menu.list.get(2).menu();
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }
    }
}
