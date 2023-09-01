package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.MemberLog;
import common.SERVICE;
import service.LocationService;
import vo.Location;

public class LocationMenu extends MENU<Location>{

	public LocationMenu(Scanner sc, SERVICE<Location> service, Manager manager) {
        super(sc, service, manager);
        this.sc = sc;
        this.service = service;
    }
	
	@Override
	public void menu() {
		boolean flag = true;
		while(flag) {
			System.out.println("지역 관리");
    		System.out.println("-----------------------------------------------------");
    		System.out.println("1.지역 추가 / 2.지역 검색(by no) / 3.전체 지역 검색 / 4.지역 수정 / 5.지역 삭제 / 0.뒤로");
    		System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();
    		switch(num) {
    		case 1:
    			((LocationService)service).addLoc(sc);
    			break;
    		case 2:
    			((LocationService)service).searchLocByNum(sc);;
    			break;
    		case 3:
    			((LocationService)service).printList(((LocationService)service).searchAllLoc());
    			break;
    		case 4:
    			((LocationService)service).editLoc(sc);;
    			break;
    		case 5:
    			((LocationService)service).delLoc(sc);;
    			break;
    		case 0:
    			flag = false;
    			break;
    		}
		}
	}
}
