package service;

import java.sql.SQLException;
import java.util.Scanner;

import common.CRUD;
import common.SERVICE;
import dao.MeetDao;
import vo.Meet;

public class MeetService extends SERVICE<Meet> {

    public MeetService(Scanner sc, CRUD<Meet> dao) {
        super(sc, dao);
        this.dao = dao;
    }

    public void menu(int num) {
        try (MeetDao<Meet> meetDao = (MeetDao<Meet>)this.dao;){
            switch(num) {
                case -1:
                    list(meetDao);
                    break;
                case 1:
                    
                    break;
                case 2:
                    write(meetDao);
                    break;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void list(MeetDao<Meet> dao) throws SQLException {
        System.out.println("모집글 출력");
        System.out.println("-----------------------------------------------------");
        for(Meet meet : dao.select(null)) {
            System.out.println(meet.toString());
        }
        System.out.println("-----------------------------------------------------");
    }

    public void write(MeetDao<Meet> dao) throws SQLException {
        System.out.println("모집글 작성");

        Meet meet = new Meet();

        System.out.print("인원 수 입력 : ");
        int recurit = sc.nextInt();
        meet.setRecurit(recurit);

        System.out.print("제목 :");
        String title = sc.next();
        meet.setTitle(title);

        System.out.print("내용 :");
        String content = sc.next();
        meet.setContent(content);

        System.out.print("지역번호 입력 : ");
        int locationNo = sc.nextInt();
        meet.setLocationNo(locationNo);

        dao.insert(meet);
    }
}
