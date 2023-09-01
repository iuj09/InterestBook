package common;

import java.util.Scanner;

/**
 * DAO에 들어갈 타입을 결정하기 위해 T의 값은 VO의 값을 결정
 */
public abstract class SERVICE<T> {
    protected CRUD<T> dao;
    protected Scanner sc;
    protected Manager manager;

    public SERVICE(Scanner sc, CRUD<T> dao, Manager manager) {
        this.sc = sc;
        this.dao = dao;
        this.manager = manager;

        manager.setService(this);
    }
}
