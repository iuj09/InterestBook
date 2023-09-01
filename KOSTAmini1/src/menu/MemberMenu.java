package menu;

import java.util.Scanner;

import common.MENU;
import common.MemberLog;
import common.SERVICE;
import service.MemberService;
import vo.Member;

public class MemberMenu extends MENU<Member> {

    public MemberMenu(Scanner sc, SERVICE<Member> service, Menu<?> menu) {
        super(sc, service, menu);
        this.sc = sc;
        this.service = service;
        this.menu = menu;
    }

    @Override
    public void menu() {
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. " + (MemberLog.member == null ? "회원 가입" : "마이페이지")  + " / 2." + (MemberLog.member == null ? "로그인" : "로그아웃") + " / 3.회원 검색 / 4.게시물 검색 / 5.Meet 검색 / 6." + (MemberLog.member == null ? "" : "회원 탈퇴") + " / 0.뒤로");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                	if(MemberLog.member == null) {
                		((MemberService)service).join(sc);
                	}else {
                		System.out.println("마이페이지");
                		System.out.println("-----------------------------------------------------");
                		System.out.println("1.내 정보 조회 / 2.내 정보 수정 / 3.내 게시물 조회 / 4.모집글 참가 내역 조회 / 5.좋아요 표시한 게시물 조회" + (MemberLog.member.getAdmin().equals("1") ? " / 6.관리자 권한부여 / 7.지역 관리 / 8.관심사 관리": ""));
                		System.out.println("-----------------------------------------------------");
                        System.out.print(": ");
                		((MemberService)service).myPage(sc.nextInt());
                	}
                    break;
                case 2:
                	if(MemberLog.member == null) {
                		((MemberService)service).login(sc);
                	}else {
                		((MemberService)service).logout();
                	}
                	break;
                case 3:
                	System.out.println("회원 검색");
                	System.out.println("-----------------------------------------------------");
            		System.out.println("1.id로 검색 / 2.이름으로 검색 / 3.번호로 검색(관리자용) / 4.전체 검색(관리자용)");
            		System.out.println("-----------------------------------------------------");
                    System.out.print(": ");
            		((MemberService)service).searchMember(sc.nextInt());
                	break;
                case 4:
                	menu.menuRun(2);
                	break;
                case 5:
                	menu.menuRun(0);
                	break;
                case 6:
                	if(MemberLog.member != null) {
                		((MemberService)service).delMember(sc);
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
    
}
