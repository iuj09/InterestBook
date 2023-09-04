package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import dao.MeetDao;
import dao.MeetRecuritDao;
import vo.Meet;
import vo.MeetRecurit;

@SuppressWarnings("unchecked")
public class MeetService extends SERVICE<Meet> {
    private MeetRecuritDao<MeetRecurit> meetRecuritDao;

    public MeetService(Scanner sc, CRUD<Meet> dao, Manager manager) {
        super(sc, dao, manager);
        meetRecuritDao = (MeetRecuritDao<MeetRecurit>)manager.getDao("MeetRecuritDao");
    }

    /**
     * 메뉴에 관한 상세한 동작을 하는 메서드
     * @param num       : 실행 번호
     * @param memberNo  : 멤버 번호
     * @param no        : 모집 번호
     * @return
     */
    public ArrayList<Meet> menu(int num, int memberNo, int no) {
        ArrayList<Meet> list = new ArrayList<>();
        try (MeetDao<Meet> meetDao = (MeetDao<Meet>)this.dao;){
            switch(num) {
                case 1:
                    // 모집글 리스트 / 로그인시: 회원이 작성한 모집글 리스트, 비로그인: 전체 모집 글 리스트
                    list(meetDao, memberNo);
                    break;
                case 2:
                    // 모집글 작성 / 로그인 상태에서만 가능
                    write(meetDao, memberNo, no);
                    break;
                case 3:
                    // 모집 글 수정 / 로그인시에 
                    edit(meetDao, infoMeet(meetDao, memberNo, no));
                    break;
                case 4:
                    // 건드리지 말자
                    check(meetDao, memberNo);
                    break;
                case 5:
                    meetJoin(meetDao, memberNo, no);
                    break;
                case 7:
                    // writeReply(meetDao, infoMeet(meetDao, no));
                    break;
                case -1:
                    list.add(infoMeet(meetDao, memberNo, no));
                    break;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
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

    /*
     * 1. 모집글 리스트
     */
    private ArrayList<Meet> list(MeetDao<Meet> dao, int memberNo) throws SQLException {
        HashMap<String, String> args = new HashMap<>();
        if(memberNo != -1) {
            args.put("MEMBERS_NO", String.valueOf(memberNo));
        }        
        
        ArrayList<Meet> list = new ArrayList<>();
        list = dao.select(args);
        if(!list.isEmpty())
            for(Meet meet : list)
                System.out.println(meet);
      
        args.clear();

        return list;
    }

    private void write(MeetDao<Meet> dao, int memberNo, int no) throws SQLException {
        if(memberNo == -1) {
            System.out.println("로그인한 회원만 모집글 작성 가능.\n");
            return;
        }

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

        meet.setMemberNo(memberNo);

        dao.insert(meet);
        System.out.println("INSERT 통과!");
        meetRecuritDao.insert(new MeetRecurit(no, memberNo, 1));
    }

    private void edit(MeetDao<Meet> dao, Meet original) throws SQLException {
        boolean flag = true;
        Meet fresh = original;
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
                        finish[0] = true;
                    }
                    break;
                case 2:
                    if(!finish[1]) {
                        System.out.println("원 내용: " + original.getContent());
                        System.out.print("새롭게 바꿀 내용: ");
                        String content = sc.next();
                        fresh.setContent(content);
                        finish[1] = true;
                    }
                    break;
                case 3:
                    if(!finish[2]) {
                        System.out.println("원 인원: " + original.getRecurit());
                        System.out.print("새롭게 모집 인원 수: ");
                        int recurit = sc.nextInt();
                        fresh.setRecurit(recurit);
                        finish[2] = true;
                    }
                    break;
                case 4:
                    dao.update(fresh);
                    flag = false;
                    break;
            }
        }
    }

    private Meet infoMeet(MeetDao<Meet> dao, int memberNo, int no) throws SQLException {
        Meet meet = new Meet();
        if(memberNo != -1) {
            HashMap<String, String> map = new HashMap<>();
            map.put("MEMBERS_NO", String.valueOf(memberNo));
            map.put("NO", String.valueOf(no));

            if(dao.select(map).isEmpty()) {
                System.out.println("번호를 잘못 입력하셨습니다. 다시 입력하십시오.");
                return meet;
            }
        }
        return meet;
    }

    private void meetJoin(MeetDao<Meet> meetDao, int memberNo, int no) throws SQLException {
        if(memberNo > 0 && no > 0) {
            meetRecuritDao.insert(new MeetRecurit(no, memberNo, 1));
            meetDao.join(no, memberNo);
        } else {
            System.out.println("로그인 후 실행가능합니다.");
        }
    }
}
