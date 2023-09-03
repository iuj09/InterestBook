package common;

import java.util.ArrayList;

/*
 * MENU, SERVICE, DAO를 통합 관리하는 최상위 클래스
 */
@SuppressWarnings("rawtypes")
public class Manager {
    private ArrayList<CRUD> dao;
    private ArrayList<SERVICE> service;
    private ArrayList<MENU> menu;

    public Manager() {
        dao = new ArrayList<>();
        service = new ArrayList<>();
        menu = new ArrayList<>();
    }

    public void setDao(CRUD dao) {
        this.dao.add(dao);
    }

    public void setService(SERVICE service) {
        this.service.add(service);
    }

    public void setMenu(MENU menu) {
        this.menu.add(menu);
    }

    /**
     * 파라미터로 넣은 CRUD클래스 이름의 객체를 반환
     * 반환값이 NULL이기 때문에 주의 NullpointException 주의
     * @param name : 해당 클래스 이름 지정(예: MeetDao)
     * @return
     */
    public CRUD getDao(String className) {
        for(CRUD crud : dao) {
            if(crud.getClass().getSimpleName().equals(className)) {
                return crud;
            }
        }
        return null;
    }

    /**
     * 파라미터로 넣은 SERVICE클래스 이름의 객체를 반환
     * 반환값이 NULL이기 때문에 주의 NullpointException 주의
     * @param name : 해당 클래스 이름 지정(예: MeetService)
     * @return
     */
    public SERVICE getService(String className) {
        for(SERVICE se : service) {
            if(se.getClass().getSimpleName().equals(className)) {
                return se;
            }
        }
        return null;
    }

    /**
     * 파라미터로 넣은 MENU클래스 이름의 객체를 반환
     * 반환값이 NULL이기 때문에 주의 NullpointException 주의
     * @param name : 해당 클래스 이름 지정(예: MeetMenu)
     * @return
     */
    public MENU getMenu(String className) {
        for(MENU mu : menu) {
            if(mu.getClass().getSimpleName().equals(className)) {
                return mu;
            }
        }
        return null;
    }

    public ArrayList<CRUD> getAllDaoList() {
        return dao;
    }
}
