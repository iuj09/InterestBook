package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.MENU;
import dao.MeetDao;
import dao.MemberDao;
import service.MeetService;
import service.MemberService;
import vo.Meet;
import vo.Member;

public class Menu<T> {
    private Scanner sc;
    public List<MENU<?>> list;

    public Menu() {
        sc = new Scanner(System.in);
        list = new ArrayList<>();
    }

    public void menu() {
        menuList();
        while(true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집 / 2. 멤버 / 3. 게시판 / 3. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            // num - 1 : ArrayList는 0부터 시작하기 때문에 입력값 - 1
            switch(num) {
                case 1:
                    menuRun(num - 1);
                    break;
                case 2:
                    menuRun(num - 1);
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }
    
    /**
     * 자신의 메뉴 클래스를 생성하는 메서드
     * 메뉴 객체 만드는 방법 : list.add(((MENU<VO>)new 이름Menu(sc, new 이름Service(sc, new 이름Dao<VO>()), this));
     */
    public void menuList() {
        list.add((MENU<Meet>)new MeetMenu(sc, new MeetService(sc, new MeetDao<Meet>()), this));
        list.add(((MENU<Member>)new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>()), this)));
    }

    public void menuRun(int num) {
        list.get(num).menu();
    }
}
