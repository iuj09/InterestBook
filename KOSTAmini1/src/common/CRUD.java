package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class CRUD<T> implements AutoCloseable{
    protected DBConnect db;
    protected Connection conn;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected String sql;

    public CRUD() {
        System.out.println("CRUD 클래스 생성!");
    }
    
    /**
     * 삽입: CREATE
     * DB 테이블에 INSERT 작업을 진행하는 메서드.
     * T에는 VO 타입을 설정
     * @param t
     * @throws SQLException
     */
    public abstract void insert (T t) throws SQLException;

    /**
     * 읽기: READ
     * DB 테이블에 SELECT 작업을 진행하는 메서드.
     * 파라미더 args에는 KEY 값을 컬럼으로, 값을 VALUE로 설정
     * 만약 특정 값이 아닌 테이블 전체 값을 구하려면 NULL을 삽입
     * @param args
     * @return (ArrayList<T>)list;
     * @throws SQLException
     */
    public abstract ArrayList<T> select(HashMap<String, String> args) throws SQLException;

    /**
     * 업데이트: UPDATE
     * DB 테이블에 UPDATE 작업을 진행하는 메서드.
     * T에는 VO 타입을 설정
     * @param t
     * @throws SQLException
     */
    public abstract void update(T t) throws SQLException;

    /**
     * 삭제: DELETE
     * DB 테이블에 UPDATE 작업을 진행하는 메서드.
     * T에는 VO타입을 설정
     * @param t
     * @throws SQLException
     */
    public abstract void delete(int no) throws SQLException;
}
