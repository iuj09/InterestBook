package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import vo.Favorite;

public class FavoriteMenu extends MENU<Favorite>{
	
	public FavoriteMenu(Scanner sc, SERVICE<Favorite> service, Manager manager) {
        super(sc, service, manager);
        this.sc = sc;
        this.service = service;
    }
	
	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}
}
