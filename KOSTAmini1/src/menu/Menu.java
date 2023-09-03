package menu;

import java.util.ArrayList;
import java.util.Scanner;

import common.CRUD;
import common.MENU;
import common.Manager;
import common.MemberLog;
import common.SERVICE;
import dao.ArticleDao;
import dao.FavoriteDao;
import dao.LocationDao;
import dao.MeetDao;
import dao.MeetReplyDao;
import dao.MemberDao;
import dao.MeetRecuritDao;
import dao.RepliesDao;
import service.ArticleService;
import service.FavoriteService;
import service.LocationService;
import service.MeetRecuritService;
import service.MeetReplyService;
import service.MeetService;
import service.MemberService;
import service.RepliesService;
import vo.Article;
import vo.Favorite;
import vo.Location;
import vo.Meet;
import vo.MeetRecurit;
import vo.MeetReply;
import vo.Member;
import vo.Replies;

public class Menu<T> {
    private Scanner sc;
    public Manager manager;
    private ArrayList<CRUD<?>> daoList;
    private ArrayList<SERVICE<?>> serviceList;
    private ArrayList<MENU<?>> menuList;

    public Menu() {
        sc = new Scanner(System.in);
        manager = new Manager();
        daoList = new ArrayList<>();
        serviceList = new ArrayList<>();
        menuList = new ArrayList<>();
    }

    public void menu() {
        list();
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. " + (MemberLog.member == null ? "회원 가입" : "마이페이지")  + " / 2." + (MemberLog.member == null ? "로그인" : "로그아웃") + " / 3.회원 검색 / 4.게시물 조회 / 5.Meet 조회 / 6." + (MemberLog.member == null ? "종료" : "회원 탈퇴") + (MemberLog.member != null ? " / 0.종료" : ""));
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

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
           	    menuRun(0);
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
    private void list() {
        // 0. 모집글
    	menuList.add(new MeetMenu(sc, new MeetService(sc, new MeetDao<>(manager), manager), manager));
        // 1. 모집글 댓글
        menuList.add(new MeetReplyMenu(sc, new MeetReplyService(sc, new MeetReplyDao<MeetReply>(manager), manager), manager));
        // 2. 멤버관리
        menuList.add(new MemberMenu(sc, new MemberService(sc, new MemberDao<Member>(manager), manager), manager));
        // 3. 지역
        menuList.add(new LocationMenu(sc, new LocationService(sc, new LocationDao<Location>(manager), manager), manager));
        // 4. 관심사
        menuList.add(new FavoriteMenu(sc, new FavoriteService(sc, new FavoriteDao<Favorite>(manager), manager), manager));
        // 5. 게시판
        menuList.add(new BoardMenu(sc, new ArticleService(sc, new ArticleDao<Article>(manager), manager), manager));
        // 6. 게시판 댓글
        menuList.add(new RepliesMenu(sc, new RepliesService(sc, new RepliesDao<Replies>(manager), manager), manager));
        // 7. 모집글에 참석한 인원에 대한 DAO
        menuList.add(new MeetRecuritMenu(sc, new MeetRecuritService(sc, new MeetRecuritDao<MeetRecurit>(manager), manager), manager));
    }

    private void menuRun(int num) {
        menuList.get(num).menu();
    }
}
