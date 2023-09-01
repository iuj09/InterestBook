package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.Meet;
import vo.MeetJoin;

public class MeetDao<T extends Meet> extends CRUD<Meet> {
    public MeetDao(Manager manager) {
        super(manager);
        db = DBConnect.getInstance();
    }

    @Override
    public void insert(Meet meet) throws SQLException {
        conn = db.conn();

        sql = "INSERT INTO MEETS(NO, RECURIT, TITLE, CONTENT, DEADLINE, LOCATIONS_NO, MEMBERS_NO) " + 
                "VALUES (MEETS_NO_SEQUENCE.NEXTVAL, ?, ?, ?, TO_DATE(SYSDATE, \'yyyy/mm/dd hh24:mi:ss\'), ?, ?)";
        
        System.out.println(sql);

        ps = conn.prepareStatement(sql);

        ps.setInt(1, meet.getRecurit());
        ps.setString(2, meet.getTitle());
        ps.setString(3, meet.getContent());
        ps.setInt(4, meet.getLocationNo());
        ps.setInt(5, meet.getMemberNo());

        ps.executeUpdate();
    }

    @Override
    public ArrayList<Meet> select(HashMap<String, String> args) throws SQLException {
        ArrayList<Meet> list = new ArrayList<>();

        conn = db.conn();
        
        sql = "SELECT * FROM MEETS";
        if(args == null) {
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new Meet(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getDate(6),
                        rs.getDate(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }
        } else {
            sql += " WHERE ";
            int cnt = args.size() -1;
            for(Entry<String, String> entry : args.entrySet()) {
                if (cnt > 0) {
                    sql += entry.getKey() + " = \'" + entry.getValue() + "\' AND";
                } else {
                    sql += entry.getKey() + " = \'" + entry.getValue() + "\' ";
                    cnt--;
                }
		    }

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new Meet(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getDate(6),
                        rs.getDate(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }
        }

        return list;
    }

    @Override
    public void update(Meet meet) throws SQLException {
        conn = db.conn();

        sql = "UPDATE MEETS SET ";

        if(meet.getTitle() != null && !meet.getTitle().equals("")) {
            sql += "TITLE = \'" + meet.getTitle() + "\' " + "WHERE NO = \'" + meet.getNo() + "\'";
        } else if(meet.getContent() != null && !meet.getContent().equals("")) {
            sql += "CONTENT = \'" + meet.getContent() + "\' " + "WHERE NO = \'" + meet.getNo() + "\'";
        } else if(meet.getRecurit() != 0) {
            sql += "RECURIT = \'" + meet.getRecurit() + "\' " + "WHERE NO = \'" + meet.getNo() + "\'";
        } else {
            sql += "E_DATE = SYSDATE" + "WHERE NO = \'" + meet.getNo() + "\'";
        }

        System.out.println(sql);

        ps = conn.prepareStatement(sql);

        ps.executeUpdate();
    }

    public ArrayList<MeetJoin> join(int memberNo) throws SQLException {
        ArrayList<MeetJoin> list = new ArrayList<>();

        conn = db.conn();

        sql = "SELECT * FROM MEMBER_MEETS_JOIN WHERE MEMBERS_NO = ?";

        ps = conn.prepareStatement(sql);

        ps.setInt(1, memberNo);

        rs = ps.executeQuery();

        while(rs.next()) {
            list.add(new MeetJoin(rs.getInt(1), rs.getInt(2)));
        }

        return list;
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
