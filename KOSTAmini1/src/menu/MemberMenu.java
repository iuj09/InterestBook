package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.MemberLog;
import common.SERVICE;
import dao.MemberDao;
import service.MemberService;
import vo.Article;
import vo.Member;

public class MemberMenu extends MENU<Member> {

    public MemberMenu(Scanner sc, SERVICE<Member> service, Manager manager) {
        super(sc, service, manager);
        this.sc = sc;
        this.service = service;
    }

    @Override
    public void menu() {
       
    }
    
    public void myPage()	{
    	boolean flag = true;
    	while(flag) {
    		System.out.println("마이페이지");
    		System.out.println("-----------------------------------------------------");
    		System.out.println("1.내 정보 조회 / 2.내 정보 수정 / 3.내 게시물 조회 / 4.모집글 참가 내역 조회 / 5.좋아요 표시한 게시물 조회" + (MemberLog.member.getAdmin().equals("1") ? " / 6.관리자 권한부여 / 7.지역 관리 / 8.관심사 관리": "") + " / 0.뒤로");
    		System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();
    		switch(num) {
    		case 1:
    			((MemberService)service).myInfo(sc);
    			break;
    		case 2:
    			((MemberService)service).editInfo(sc);
    			break;
    		case 3:
    			((MemberService)service).myArticle(sc);
    			break;
    		case 4:
    			((MemberService)service).checkMeet(sc);
    			break;
    		case 5:
    			
    			break;
    		case 6:
    			if(MemberLog.member.getAdmin().equals("1")) {
    				((MemberService)service).editAdm(sc);
    			}
    			break;
    		case 7:
    			if(MemberLog.member.getAdmin().equals("1")) {
    				((LocationMenu)this.manager.getMenu("LocationMenu")).menu();
    			}
    			break;
    		case 8:
    			if(MemberLog.member.getAdmin().equals("1")) {
    				((FavoriteMenu)this.manager.getMenu("FavoriteMenu")).menu();
    			}
    		case 0:
    			flag = false;
    			break;
    		}
    	}
	}
    
    public void searchMember() {
    	boolean flag = true;
    	while(flag) {
    		System.out.println("회원 검색");
        	System.out.println("-----------------------------------------------------");
    		System.out.println("1.id로 검색 / 2.이름으로 검색" + (MemberLog.member.getAdmin().equals("1") ? "/ 3.번호로 검색(관리자용) / 4.전체 검색(관리자용)" : "") + " / 0.뒤로");
    		System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();
    		switch(num) {
    		case 1:
    			((MemberService)service).searchById(sc);
    			break;
    		case 2:
    			((MemberService)service).searchByName(sc);
    			break;
    		case 3:
    			((MemberService)service).searchByNum(sc);
    			break;
    		case 4:
    			((MemberService)service).searchAll();
    			break;
    		case 0:
    			flag = false;
    			break;
    		}
    	}
	}

	@Override
	public void menu1(Article a) {
		// TODO Auto-generated method stub
		
	}
    
}
