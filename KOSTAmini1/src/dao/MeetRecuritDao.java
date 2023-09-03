package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.MeetRecurit;

public class MeetRecuritDao<T extends MeetRecurit> extends CRUD<MeetRecurit> {
    public MeetRecuritDao(Manager manager) {
        super(manager);
        db = DBConnect.getInstance();
    }

    @Override
    public void insert(MeetRecurit meetRecurit) throws SQLException {
        conn = db.conn();

        sql = "INSERT INTO MEETS_RECURIT_JOIN VALUES(?, ?, ?)";
        System.out.println("통과2");
        ps = conn.prepareStatement(sql);
        ps.setInt(1, meetRecurit.getMeetNo());
        ps.setInt(2, meetRecurit.getMemberNo());
        ps.setInt(3, meetRecurit.getExc());

        ps.executeUpdate();
    }

    @Override
    public ArrayList<MeetRecurit> select(HashMap<String, String> args) throws SQLException {
        ArrayList<MeetRecurit> list = new ArrayList<>();

        sql = "SELECT * FROM MEETS_RECURIT_JOIN";

        if(args.isEmpty()) {
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new MeetRecurit(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
        } else {
            sql += " WHERE ";
            int cnt = args.size() -1;
            for(Entry<String, String> entry : args.entrySet()) {
                if (cnt > 0) {
                    sql += entry.getKey() + " = \'" + entry.getValue() + "\' AND ";
                } else {
                    sql += entry.getKey() + " = \'" + entry.getValue() + "\' ";
                    
                }
                cnt--;
		    }

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new MeetRecurit(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
        }

        return list;
    }

    @Override
    public void update(MeetRecurit meetRecurit) throws SQLException {
        conn = db.conn();

        sql = "UPDATE MEETS_RECURIT_JOIN SET EXC = ? WHERE MEETS_NO = ? AND MEMBERS_NO = ?";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, meetRecurit.getExc());
        ps.setInt(2, meetRecurit.getMeetNo());
        ps.setInt(3, meetRecurit.getMemberNo());

        ps.executeUpdate();
    }

    @Override
    public void delete(int no) throws SQLException {
        conn = db.conn();

        sql = "DELETE FROM MEETS_RECURIT_JOIN WHERE MEETS_NO = ?";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, no);

        ps.executeUpdate();
    }
    
    @Override
    public void close() throws Exception {
        if(rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
}
