package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.MemberLog;
import common.SERVICE;
import service.MeetService;
import vo.Article;
import vo.Meet;

public class MeetMenu extends MENU<Meet> {
    private MeetService meetService;
    private Manager meetMenuManager;

    public MeetMenu(Scanner sc, SERVICE<Meet> service, Manager manager) {
        super(sc, service, manager);
        this.sc = sc;
        meetService = (MeetService)this.service;
        meetMenuManager = manager;   
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
        String memeberName = (MemberLog.member != null) ? MemberLog.member.getId() : null;
        int memberNo = (MemberLog.member != null) ? MemberLog.member.getNo() : -1;
        boolean flag = true;
        while(flag) {
            System.out.println("\n-----------------------------------------------------");
            System.out.println("1. " + (memeberName == null ? "모집글 리스트" : memeberName + "님의 모집글 리스트") + " / 2. 모집글 상세 조회 / 3. 모집글 작성 / 4. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    // list: 모집글 리스트 / 로그인 : 회원 모집 리스트, 비로그인 : 전체 모집 리스트
                    meetService.menu(1, memberNo, 0);
                    break;
                case 2:
                    // 모집글 자세히 확인
                    meetInfo(memberNo);
                    break;
                case 3:
                    // write: 모집글 작성, 로그인 상태에서만 가능
                    meetService. menu(2, memberNo, 0);
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }
    }

    private void meetInfo(int memberNo) {
        System.out.println("-----------------------------------------------------");
        System.out.print("모집글 번호: ");
        int no = sc.nextInt();
        Meet meet = meetService.menu(-1, memberNo, no).get(0);
        System.out.println(meet);

        if(meet == null) return;
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집글 수정 / 2. 참가 / 3. 참가 취소 / 4. 해당 모집글 댓글 조회 / 5. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    meetService.menu(3, memberNo, no);
                    break;
                case 2:
                    meetService.menu(5, memberNo, no);
                    break;
                case 3:
                    break;
                case 4:
                    meetMenuManager.getMenu("MeetReplyMenu").menu();
                    break;
                case 5:
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
