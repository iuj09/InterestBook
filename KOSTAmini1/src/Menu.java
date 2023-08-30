import java.util.Scanner;

import dao.MeetDao;
import service.MeetService;

public class Menu {
    private Scanner sc;

    public Menu() {
        sc = new Scanner(System.in);
    }

    public void menu() {
        
        while(true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집 / 2. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    meet();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void meet() {
        MeetService meetService = new MeetService(sc, new MeetDao<>());
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            meetService.menu(-1);
            System.out.println("1. 모집 게시글 상세조회 / 2. 모집 게시글 작성 / 5. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    infoMeet();
                    break;
                case 2:
                    meetService.menu(num);
                    break;
                case 10:
                    flag = false;
                    break;
            }
        }
    }

    public void infoMeet() {
        System.out.println("1. 참가 / 2. 참가 취소 / 3. 댓글 작성");
        System.out.println("-----------------------------------------------------");
    }
}
