package service;

import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import vo.MeetRecurit;

public class MeetRecuritService extends SERVICE<MeetRecurit> {

    public MeetRecuritService(Scanner sc, CRUD<MeetRecurit> dao, Manager manager) {
        super(sc, dao, manager);
    }
    
}
