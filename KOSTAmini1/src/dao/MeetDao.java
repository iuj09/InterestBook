package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import common.DBConnect;
import vo.Meet;

public class MeetDao<T extends Meet> extends CRUD<Meet> {
    public MeetDao() {
        System.out.println("MeetDao 클래스 생성!");
        db = DBConnect.getInstance();
    }

    @Override
    public void insert(Meet meet) throws SQLException {
        conn = db.conn();

        sql = "INSERT INTO MEETS(NO, RECURIT, TITLE, CONTENT, DEADLINE, LOCATION_NO, MEMBER_NO) " + 
                "VALUES (MEETS_NO_SEQUENCE.NEXTVAL, ?, ?, ?, TO_DATE(SYSDATE, \'yyyy/mm/dd hh24:mi:ss\'), ?, 3)";

        ps = conn.prepareStatement(sql);

        ps.setInt(1, meet.getRecurit());
        ps.setString(2, meet.getTitle());
        ps.setString(3, meet.getContent());
        ps.setInt(4, meet.getLocationNo());

        ps.executeUpdate();
    }

    @Override
    public ArrayList<Meet> select(HashMap<String, String> args) throws SQLException {
        ArrayList<Meet> list = new ArrayList<>();

        conn = db.conn();

        if(args == null) {
            sql = "SELECT * FROM MEETS";

            if(conn == null) {
                System.out.println("conn이 널이다");
            }

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new Meet(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getDate(6),
                        rs.getDate(7), rs.getInt(8), rs.getInt(9)));
            }

            return list;
        }

        return null;
    }

    @Override
    public void update(Meet t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
    @Override
    public void delete(int no) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void close() throws Exception {
        if(rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
}
