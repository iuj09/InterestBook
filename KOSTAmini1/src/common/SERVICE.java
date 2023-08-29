package common;

import java.util.Properties;
import java.util.Scanner;

/**
 * DAO에 들어갈 타입을 결정하기 위해 T의 값은 VO의 값을 결정
 */
public abstract class SERVICE<T> {
    protected CRUD<T> dao;
    protected Scanner sc;
    protected Properties prop;

    public SERVICE(Scanner sc, CRUD<T> dao) {
        System.out.println("SERVICE 클래스 생성!");
        this.sc = sc;
        this.prop = new Properties();
        this.dao = dao;
    }
}
