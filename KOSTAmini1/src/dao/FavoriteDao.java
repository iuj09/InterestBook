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
		
		sql = "insert into favorites values(?, ?)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, f.getNo());
		ps.setString(2, f.getName());
		
		ps.executeUpdate();
	}
	
	public ArrayList<Favorite> select(HashMap<String, String> args) throws SQLException{
		ArrayList<Favorite> list = new ArrayList<>();
		
		conn = db.conn();
		
		if(args == null) {
			sql = "select * from favorites";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Favorite(rs.getInt(1), rs.getString(2)));
			}
		return list;
		}
		
		sql = "select * from favorites where ";
		
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
			list.add(new Favorite(rs.getInt(1), rs.getString(2)));
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
	
	public void close() throws SQLException {
        if (rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
	

}
