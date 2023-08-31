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
    private ArrayList<CRUD<T>> daoList;
    private ArrayList<SERVICE<T>> serviceList;
    public ArrayList<MENU<?>> menuList;


    public Menu() {
        sc = new Scanner(System.in);
        menuList = new ArrayList<>();
        daoList = new ArrayList<>();
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
     * 자신의 메뉴 클래스를 생성하는 메서드
     * 메뉴 객체 만드는 방법 : list.add(((MENU<VO>)new 이름Menu(sc, new 이름Service(sc, new 이름Dao<VO>()), this));
     */
    @SuppressWarnings("rawtypes")
    private void menuList() {
        // list.add((MENU<Meet>)new MeetMenu(sc, new MeetService(sc, new MeetDao<Meet>()), this));
        menuList.add(((MENU<Member>)new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>()), this)));
        menuList.add(((MENU<Article>)new BoardMenu(sc, new ArticleService(sc, new ArticleDao<Article>()), this)));
        menuList.add((MENU<Member>)new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>()), this));
        menuList.add((MENU<MeetReply>)new MeetReplyMenu(sc, new MeetReplyService(sc, new MeetReplyDao<MeetReply>()), this));

        
        daoList.add((CRUD<T>)new MeetDao<Meet>());
        serviceList.add((SERVICE<T>)new MeetService(sc, (CRUD<Meet>)daoList.get(0)));
        menuList.add((MENU<T>)new MeetMenu(sc, (SERVICE<Meet>)serviceList.get(0), this));

        Manager<?> manager = new Manager<>(daoList, serviceList, menuList);
    }

    public void menuRun(int num) {
        list.get(num).menu();
    }
}
