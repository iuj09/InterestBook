package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.Location;

public class LocationDao<T extends Location> extends CRUD<Location> {
	
	public LocationDao(Manager manager) {
		super(manager);
		db = DBConnect.getInstance();
	}
	
	
	public void insert(Location l) throws SQLException{
		conn = db.conn();
		
		sql = "insert into locations values(?, ?)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, l.getNo());
		ps.setNString(2, l.getName());
		
		ps.executeUpdate();
	}
	
	public ArrayList<Location> select(HashMap<String, String> args) throws SQLException{
		ArrayList<Location> list = new ArrayList<>();
		
		conn = db.conn();
		
		if(args == null) {
			sql = "select * from locations";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Location(rs.getInt(1), rs.getString(2)));
			}
		return list;
		}
		
		sql = "select * from locations where ";
		
		int cnt = args.size() -1;
		for(Entry<String, String> entry : args.entrySet()) {
			if(entry.getKey().equals("no")) {
				Integer.parseInt(entry.getValue());
			}
			if (cnt > 0) {
				sql += entry.getKey() + " = \'" + entry.getValue() + "\' and";
			} else {
				sql += entry.getKey() + " = \'" + entry.getValue() + "\' ";
				cnt--;
			}
		}
		
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		while(rs.next()) {
			list.add(new Location(rs.getInt(1), rs.getString(2)));
		}
		return list;
	}
	
	public void update(Location l) throws SQLException{
		
		conn = db.conn();
		
		sql = "update locations set name = ? where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, l.getName());
		ps.setInt(2, l.getNo());
		
		ps.executeUpdate();
	}
	
	public void delete(int no) throws SQLException{
		
		conn = db.conn();
		
		sql = "delete from locations where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, no);
		
		ps.executeUpdate();
	}
	
	public void close() throws SQLException {
        if (rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
}
