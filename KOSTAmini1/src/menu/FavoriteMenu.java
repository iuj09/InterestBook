package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import service.FavoriteService;
import service.LocationService;
import vo.Article;
import vo.Favorite;

public class FavoriteMenu extends MENU<Favorite>{
	
	public FavoriteMenu(Scanner sc, SERVICE<Favorite> service, Manager manager) {
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
    		System.out.println("1.관심사 추가 / 2.관심사 검색(by no) / 3.전체 관심사 검색 / 4.관심사 수정 / 5.관심사 삭제 / 0.뒤로");
    		System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();
    		switch(num) {
    		case 1:
    			((FavoriteService)service).addFav(sc);
    			break;
    		case 2:
    			((FavoriteService)service).searchFavByNum(sc);
    			break;
    		case 3:
    			((FavoriteService)service).printList(((FavoriteService)service).searchAllFav());
    			break;
    		case 4:
    			((FavoriteService)service).editFav(sc);;
    			break;
    		case 5:
    			((FavoriteService)service).delFav(sc);;
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
