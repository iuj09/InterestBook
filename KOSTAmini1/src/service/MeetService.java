package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import dao.MeetDao;
import vo.Meet;
import vo.MeetJoin;

public class MeetService extends SERVICE<Meet> {

    public MeetService(Scanner sc, CRUD<Meet> dao, Manager manager) {
        super(sc, dao, manager);
        // this.dao = dao;
    }

    // 메뉴에 관한 상세한 동작을 하는 메서드
    public void menu(int num, int no) {
        try (MeetDao<Meet> meetDao = (MeetDao<Meet>)this.dao;){
            switch(num) {
                case 1:
                    list(meetDao, no);
                    break;
                case 2:
                    write(meetDao);
                    break;
                case 3:
                    edit(meetDao, infoMeet(meetDao, no));
                    break;
                case 4:
                    check(meetDao, no);
                    break;
                case 7:
                    // writeReply(meetDao, infoMeet(meetDao, no));
                    break;
                case -1:
                    infoMeet(meetDao, no);
                    break;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void check(MeetDao<Meet> meetDao, int memberNo) throws SQLException {
        HashMap<String, String> args = new HashMap<>();
        if(memberNo != 0) {
            args.put("MEMBERS_NO", String.valueOf(memberNo));
        }        
        
        System.out.println("-----------------------------------------------------");
        for(Meet meet : dao.select(args)) {
            System.out.println(meet.toString());
        }
        System.out.println("-----------------------------------------------------");

        args.clear();
    }

    private void list(MeetDao<Meet> dao, int memberNo) throws SQLException {
        System.out.println("모집글 출력");
        
        HashMap<String, String> args = new HashMap<>();
        if(memberNo != 0) {
            args.put("MEMBERS_NO", String.valueOf(memberNo));
        }        
        
        System.out.println("-----------------------------------------------------");
        for(Meet meet : dao.select(args)) {
            System.out.println(meet.toString());
        }
        System.out.println("-----------------------------------------------------");

        args.clear();
    }

    private void write(MeetDao<Meet> dao) throws SQLException {
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

    private void edit(MeetDao<Meet> dao, Meet original) throws SQLException {
        boolean flag = true;
        Meet fresh = new Meet();
        fresh.setNo(original.getNo());
        boolean[] finish = new boolean[]{false, false, false};
        while(flag) {
            System.out.println("-----------------------------------------------------");
            System.out.println("1. 제목 수정 " + (finish[0] ? "[완료]" : "") + " / 2. 내용 수정 " + (finish[1] ? "[완료]" : "") + " / 3. 모집 인원 수정 " + (finish[0] ? "[완료]" : "") + " / 4. 끝");
            System.out.println("-----------------------------------------------------");
            System.out.print(": ");
            int num = sc.nextInt();

            System.out.println();
            switch(num) {
                case 1:
                    if(!finish[0]) {
                        System.out.println("원 제목: " + original.getTitle());
                        System.out.print("새롭게 바꿀 제목: ");
                        String title = sc.next();
                        fresh.setTitle(title);
                        dao.update(fresh);
                        fresh.setTitle("");
                        finish[0] = true;
                    } else { System.out.println("제목 수정 완료");}
                    break;
                case 2:
                    if(!finish[1]) {
                        System.out.println("원 내용: " + original.getContent());
                        System.out.print("새롭게 바꿀 내용: ");
                        String content = sc.next();
                        fresh.setContent(content);
                        dao.update(fresh);
                        fresh.setContent(null);
                        finish[1] = true;
                    } else { System.out.println("내용 수정 완료");}
                    break;
                case 3:
                    if(!finish[2]) {
                        System.out.println("원 인원: " + original.getRecurit());
                        System.out.print("새롭게 모집 인원 수: ");
                        int recurit = sc.nextInt();
                        fresh.setRecurit(recurit);
                        dao.update(fresh);
                        fresh.setRecurit(0);
                        finish[2] = true;
                    } else { System.out.println("모집 인원 수정 완료");}
                    break;
                case 4:
                    dao.update(fresh);
                    flag = false;
                    break;
            }
        }
    }

    private Meet infoMeet(MeetDao<Meet> dao, int no) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("NO", String.valueOf(no));
        return (Meet)dao.select(map).get(0);
    }

    public void meetJoin(MeetDao<Meet> meetDao, int memberNo) throws SQLException {
        ArrayList<MeetJoin> list = meetDao.join(memberNo);
        if(list.isEmpty()) return;
        
        System.out.println("통과");
        for(MeetJoin obj : list) {
            System.out.println(obj);
        }
    }
}
