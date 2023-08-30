package common;

import java.util.Scanner;

public abstract class MENU<T> {
    protected SERVICE<T> service;
    protected Scanner sc;
    
    public MENU(SERVICE<T> service, Scanner sc) {
        this.service = service;
        this.sc = sc;
    }

    public abstract void menu();
}
