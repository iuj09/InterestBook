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
            System.out.println("1. 모집글 출력 / 2. 모집글 조회 / 3. 모집글 작성 / 3. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    ((MeetService)service).menu(1, 0);
                    break;
                case 2:
                    // 모집글 선택
                    System.out.print("모집글 번호: ");
                    int no = sc.nextInt();
                    meetInfo(no);
                    break;
                case 3:
                    ((MeetService)service).menu(2, 0);
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }
    }

    /**
     * no : 게시글 번호
     */
    public void meetInfo(int no) {
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            ((MeetService)service).menu(-1, no);
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집글 수정 / 2. 참가 / 3. 참가 취소 / 4. 해당 모집글 댓글 조회 / 5. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    ((MeetService)service).menu(3, no);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    menu.menuRun(2);
                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }
}
