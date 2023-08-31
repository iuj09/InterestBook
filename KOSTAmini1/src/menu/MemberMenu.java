package menu;

import java.util.Scanner;

import common.MENU;
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
            System.out.println("1. 회원 출력 / 2. 이전");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    ((MemberService)service).searchAll();
                    break;
                case 2:
                    flag =false;
                    break;
            }
        }
    }
    
}
