package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.CRUD;
import vo.Meet;

public class MeetDao<T extends Meet> extends CRUD<Meet> {
    @Override
    public void insert(Meet meet) throws SQLException {
        conn = db.conn();

        sql = "INSERT INTO MEETS VALUES (10, ?, ?, ?, SYSDATE, SYSDATE, \'2023/10/01'\', 5, 3)";

        ps = conn.prepareStatement(sql);

        ps.setInt(1, meet.getRecurit());
        ps.setString(2, meet.getTitle());
        ps.setString(3, meet.getContent());
    }

    @Override
    public ArrayList<Meet> select(HashMap<String, String> args) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'select'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
}
