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

    public CRUD() {}
    
    /**
     * 삽입: CREATE
     * @param t
     * @throws SQLException
     */
    public abstract void insert (T t) throws SQLException;

    /**
     * 읽기: READ
     * @param args
     * @return (ArrayList<T>)list;
     * @throws SQLException
     */
    public abstract ArrayList<T> select(HashMap<String, String> args) throws SQLException;

    /**
     * 업데이트: UPDATE
     * @param t
     * @throws SQLException
     */
    public abstract void update(T t) throws SQLException;

    // 삭제: DELETE
    /**
     * 삭제: DELETE
     * @param t
     * @throws SQLException
     */
    public abstract void delete(T t) throws SQLException;

    /**
     * 테이블 전체 데이터 선택
     * @return (ArrayList<T>)list;
     * @throws SQLException
     */
    public abstract ArrayList<T> selectAll() throws SQLException;
}
