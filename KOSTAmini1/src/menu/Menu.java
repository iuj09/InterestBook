package menu;

import java.util.ArrayList;
import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.MemberLog;
import dao.ArticleDao;
import dao.FavoriteDao;
import dao.LocationDao;
import dao.MeetDao;
import dao.MeetReplyDao;
import dao.MemberDao;
import service.ArticleService;
import service.FavoriteService;
import service.LocationService;
import service.MeetReplyService;
import service.MeetService;
import service.MemberService;
import vo.Article;
import vo.Favorite;
import vo.Location;
import vo.Meet;
import vo.MeetReply;
import vo.Member;

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
    	boolean flag = true;
        menuList();
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. " + (MemberLog.member == null ? "회원 가입" : "마이페이지")  + " / 2." + (MemberLog.member == null ? "로그인" : "로그아웃") + " / 3.회원 검색 / 4.게시물 검색 / 5.Meet 검색 / 6." + (MemberLog.member == null ? "종료" : "회원 탈퇴") + (MemberLog.member != null ? " / 0.뒤로" : ""));
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            // num - 1 : ArrayList는 0부터 시작하기 때문에 입력값 - 1
            switch(num) {
            case 1:
            	if(MemberLog.member == null) {
            		((MemberService)this.manager.getService("MemberService")).join(sc);
            	}else {
            		((MemberMenu)this.manager.getMenu("MemberMenu")).myPage();
            	}
                break;
            case 2:
            	if(MemberLog.member == null) {
            		((MemberService)this.manager.getService("MemberService")).login(sc);
            		
            	}else {
            		((MemberService)this.manager.getService("MemberService")).logout();
            	}
            	break;
            case 3:
        		((MemberMenu)this.manager.getMenu("MemberMenu")).searchMember();
            	break;
            case 4:
            	((BoardMenu)this.manager.getMenu("BoardMenu")).menu();
            	break;
            case 5:
//            	menu.menuRun(0);
            	break;
            case 6:
            	if(MemberLog.member != null) {
            		((MemberService)this.manager.getService("MemberService")).delMember(sc);
            	}else {
            		System.exit(0);
            	}
            	break;
            case 0:
            	if(MemberLog.member != null) {
            		flag = false;
            		break;
            	}
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
        list.add(new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>(manager), manager), manager));
        list.add(new BoardMenu(sc, new ArticleService(sc, new ArticleDao<Article>(manager), manager), manager));
        list.add(new LocationMenu(sc, new LocationService(sc, new LocationDao<Location>(manager), manager), manager));
        list.add(new FavoriteMenu(sc, new FavoriteService(sc, new FavoriteDao<Favorite>(manager), manager), manager));
        
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
