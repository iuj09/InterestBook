package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import vo.Location;

public class LocationMenu extends MENU<Location>{

	public LocationMenu(Scanner sc, SERVICE<Location> service, Manager manager) {
        super(sc, service, manager);
        this.sc = sc;
        this.service = service;
    }
	
	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}
}
