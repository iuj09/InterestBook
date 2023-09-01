

import java.util.ArrayList;
import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.MemberLog;
import dao.MeetDao;
import dao.MeetReplyDao;
import dao.MemberDao;
import menu.MeetMenu;
import menu.MeetReplyMenu;
import menu.MemberMenu;
import service.MeetReplyService;
import service.MeetService;
import service.MemberService;
import vo.Meet;
import vo.Member;

public class PraticeMenu {
    private Scanner sc;
    public Manager manager;
    private ArrayList<MENU<?>> list;

    public PraticeMenu() {
        sc = new Scanner(System.in);
        list = new ArrayList<>();
        manager = new Manager();
    }

    public void menu() {
        menuList();
        while (true) {
            System.out.println("\n-----------------------------------------------------");
            System.out.println("1." + (MemberLog.member == null ? "로그인" : "로그아웃") + "   2. " + (MemberLog.member == null ? "모든 모집글" : "나의 모집글"));
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    if (MemberLog.member == null) {
                        ((MemberService)this.manager.getService("MemberService")).login(sc);
                    } else {
                        ((MemberService)this.manager.getService("MemberService")).logout();
                    }
                case 2:
                    menuRun(0);
                    break;
            }
        }
    }

    private void menuList() {
        // list.add(new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>(manager), manager), manager));
        list.add(new MeetMenu(sc, new MeetService(sc, new MeetDao<Meet>(manager), manager), manager));
        // list.add(new MeetReplyMenu(sc, new MeetReplyService(sc, new MeetReplyDao<MeetReply>(manager), manager), manager));
    }

    private void menuRun(int num) {
        list.get(num).menu();
    }
}
