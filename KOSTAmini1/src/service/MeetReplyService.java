package service;

import java.sql.SQLException;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import dao.MeetReplyDao;
import vo.MeetReply;

public class MeetReplyService extends SERVICE<MeetReply> {

    public MeetReplyService(Scanner sc, CRUD<MeetReply> dao, Manager manager) {
        super(sc, dao, manager);
    }
    
    public void menu(int num, int no) {
        try (MeetReplyDao<MeetReply> meetDao = (MeetReplyDao<MeetReply>)this.dao;){
            switch(num) {
                case 1:
                    list(meetDao);
                    break;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void list(MeetReplyDao<MeetReply> meetDao) throws SQLException {
        for(MeetReply meetReply : dao.select(null)) {
            System.out.println(meetReply);
        }
    }
}
