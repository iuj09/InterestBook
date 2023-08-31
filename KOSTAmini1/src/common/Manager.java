package common;

import java.util.ArrayList;

public class Manager<T> {
    private ArrayList<CRUD<T>> daoList;
    private ArrayList<SERVICE<T>> serviceList;
    private ArrayList<MENU<T>> menuList;

    public Manager(ArrayList<CRUD<T>> daoList, ArrayList<SERVICE<T>> serviceList, ArrayList<MENU<T>> menuList) {
        this.daoList = daoList;
        this.serviceList = serviceList;
        this.menuList = menuList;
    }

    public CRUD<T> getDao(int num) {
        return daoList.get(num);
    }

    public SERVICE<T> getService(int num) {
        return serviceList.get(num);
    }

    public MENU<T> getMenu(int num) {
        return menuList.get(num);
    }
}
