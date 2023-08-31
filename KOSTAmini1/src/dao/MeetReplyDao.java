package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import common.DBConnect;
import vo.MeetReply;

public class MeetReplyDao<T extends MeetReply> extends CRUD<MeetReply> {

    public MeetReplyDao() {
        db = DBConnect.getInstance();
    }

    @Override
    public void insert(MeetReply t) throws SQLException {
    
    }

    @Override
    public ArrayList<MeetReply> select(HashMap<String, String> args) throws SQLException {
        ArrayList<MeetReply> list = new ArrayList<>();

        conn = db.conn();

        if(args == null) {
            sql = "SELECT * FROM MEETS_REPLIES";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new MeetReply(rs.getInt(1), rs.getString(2), 
                                        rs.getDate(3), rs.getDate(4), 
                                        rs.getInt(5), rs.getInt(6), 
                                        rs.getInt(7)));
            }
        }

        return list;
    }

    @Override
    public void update(MeetReply t) throws SQLException {
    
    }

    @Override
    public void delete(int no) throws SQLException {

    }

    @Override
    public void close() throws Exception {
        if(rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
}
