package menu;

import java.util.Scanner;

import common.MENU;
import common.Manager;
import common.SERVICE;
import vo.Article;
import vo.MeetRecurit;

public class MeetRecuritMenu extends MENU<MeetRecurit> {

    public MeetRecuritMenu(Scanner sc, SERVICE<MeetRecurit> service, Manager manager) {
        super(sc, service, manager);
    }

    @Override
    public void menu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'menu'");
    }

    @Override
    public void menu1(Article a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'menu1'");
    }
    
}
