package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.MENU;
import common.VO;
import dao.MeetDao;
import service.MeetService;
import vo.Meet;

public class Menu<T extends VO> {
    private Scanner sc;
    private List<MENU<T>> list;

    public Menu() {
        sc = new Scanner(System.in);
        list = new ArrayList<>(){
            {
                add(null); add(null);
            }
        };
    }

    public void menu() {
        while(true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 모집 / 2. 종료");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            switch(num) {
                case 1:
                    objCheck(num);
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void objCheck(int num) {
        if(num == 1 && list.get(0) == null) list.add(1, (MENU<T>)new MeetMenu(new MeetService(sc, new MeetDao<Meet>()), sc));
        else list.get(num).menu();
    }
}
