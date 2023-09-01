package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.CRUD;
import common.MENU;
import common.Manager;
import common.SERVICE;
import dao.ArticleDao;
import dao.MeetDao;
import dao.MeetReplyDao;
import dao.MemberDao;
import service.ArticleService;
import service.MeetReplyService;
import service.MeetService;
import service.MemberService;
import vo.Article;
import vo.Meet;
import vo.Member;
import vo.MeetReply;

public class Menu<T> {
    private Scanner sc;
    public Manager manager;
    private ArrayList<MENU<?>> list;

    public Menu() {
        sc = new Scanner(System.in);
        list = new ArrayList<>();
        manager = new Manager();
    }

    public void menu() {
        menuList();
        while(true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집 / 2. 멤버 / 3. 게시판 / 0. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            // num - 1 : ArrayList는 0부터 시작하기 때문에 입력값 - 1
            switch(num) {
                case 1:
                    menuRun(num - 1);
                    break;
                case 2:
                    menuRun(num - 1);
                    break;
                case 3:
                	menuRun(num - 1);
                	break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
    
    /**
     * db테이블 및 기능과 관련된 객체 초기화 및 관련 객체를 담는 ArrayList 및 그 객체를 한 곳에서 묶는 Manager 클래스
     * 예시 : list.add(new 클래스Menu(sc, new 클래스Service(sc, new 클래스Dao<클래스>(manager), manager), manager));
     */
    private void menuList() {

        list.add(new MeetMenu(sc, new MeetService(sc, new MeetDao<Meet>(manager), manager), manager));
        list.add(new MeetReplyMenu(sc, new MeetReplyService(sc, new MeetReplyDao<MeetReply>(manager), manager), manager));
        // menuList.add(((MENU<Member>)new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>()), this)));
        // menuList.add(((MENU<Article>)new BoardMenu(sc, new ArticleService(sc, new ArticleDao<Article>()), this)));
        // menuList.add((MENU<Member>)new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>()), this));
        // menuList.add((MENU<MeetReply>)new MeetReplyMenu(sc, new MeetReplyService(sc, new MeetReplyDao<MeetReply>()), this));
        // manager = new Manager<>();
        // manager.setMenu((MENU<T>)new MeetMenu(sc, new MeetService(sc, new MeetDao<Meet>()), this));

        // for() {

        // }

    }

    private void menuRun(int num) {
        list.get(num).menu();
    }
}
