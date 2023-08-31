package common;

import java.util.Scanner;

import menu.Menu;

/**
 * 각 테이블에 맞는 기능(= 메뉴)를 관리하는 클래스
 */
public abstract class MENU<T> {
    protected Scanner sc;
    protected SERVICE<T> service;
    protected Menu<?> menu;
    
    /**
     * SERVICE<T> : T는 CRUD<T>의 VO를 담당
     * Menu<?> : 객체로 생성된 Menu 클래스, 여러 객체가 생성되면 혼잡 및 충돌이 일어날 수 있어 하나의 객체로 생성 및 관리
     * @param sc
     * @param service
     * @param menu
     */
    public MENU(Scanner sc, SERVICE<T> service, Menu<?> menu) {
        this.sc = sc;
        this.service = service;
        this.menu = menu;
        System.out.println(service.toString());
    }

    public abstract void menu();
}
