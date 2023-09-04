package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import common.Manager;
import vo.Favorite;

public class FavoriteDao<T extends Favorite> extends CRUD<Favorite> {
	
	public FavoriteDao(Manager manager) {
		super(manager);
		db = DBConnect.getInstance();
	}
	
	public void insert(Favorite f) throws SQLException{
		
		conn = db.conn();
		
		sql = "insert into favorites values(?, ?, 0)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, f.getNo());
		ps.setString(2, f.getName());
		
		ps.executeUpdate();
	}
	
	public ArrayList<Favorite> select(HashMap<String, String> args) throws SQLException{
		ArrayList<Favorite> list = new ArrayList<>();
		
		conn = db.conn();
		
		if(args == null) {
			sql = "select f.no, f.name, count(*) heart from favorites f, members m where f.no = m.favorites_no group by f.no, f.name order by f.no";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Favorite(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
		return list;
		}
		
		sql = "select f.no, f.name, count(*) heart from favorites  f, members m where f.no = m.favorites_no group by f.no, f.name having ";
		
		for(Entry<String, String> entry : args.entrySet()) {
			if(entry.getKey().equals("f.no")) {
				Integer.parseInt(entry.getValue());
			}
				sql += entry.getKey() + " = \'" + entry.getValue() + "\' order by f.no";
		}
		
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		while(rs.next()) {
			list.add(new Favorite(rs.getInt(1), rs.getString(2), rs.getInt(3)));
		}
		return list;
	}
	
	public void update(Favorite f) throws SQLException{
		
		conn = db.conn();
		
		sql = "update favorites set name = ? where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, f.getName());
		ps.setInt(2, f.getNo());
		
		ps.executeUpdate();
	}
	
	public void delete(int no) throws SQLException{
		
		conn = db.conn();
		
		sql = "delete from favorites where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, no);
		
		ps.executeUpdate();
	}
	
	public String getName(int cId) {
		String name = "";
		conn = db.conn();
		sql = "SELECT * FROM favorites WHERE no = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cId);
			rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public void close() throws SQLException {
        if (rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
	

}
