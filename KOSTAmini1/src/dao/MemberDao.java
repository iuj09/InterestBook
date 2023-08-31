package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import common.CRUD;
import common.DBConnect;
import vo.Member;

public class MemberDao<T extends Member> extends CRUD<Member> {

	
	public  MemberDao() {
		db = DBConnect.getInstance();
	}
	
	//회원 가입
	public void insert(Member m) throws SQLException {
		conn = db.conn();
		
		sql = "insert into members values(?, ?, ?, ?, ?, sysdate, ?, ?, ?)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, m.getNo());
		ps.setString(2, m.getId());
		ps.setString(3, m.getPwd());
		ps.setString(4, m.getName());
		ps.setString(5, m.getEmail());
		ps.setInt(6, m.getLocationNo());
		ps.setInt(7, m.getFavoriteNo());
		ps.setInt(8, m.getAdmin());
		
		ps.executeUpdate();
	}
	
	//admin 권한 부여
	public void updateAdm(Member m) throws SQLException{
		conn = db.conn();
		
		sql = "update members set admin = ? where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, m.getAdmin());
		ps.setInt(2, m.getNo());
		
		ps.executeUpdate();
	}
	
	
	/**
	 * 마이페이지
	 * 	- 내 정보 조회 : 아래 select method에서 로그인 된 id를 통해 내 정보 확인
	 *  - 내 정보 수정
	 *  - meet 참가 확인
	 *  - 좋아요 표시한 게시물 확인
	 */
	
	//내 정보 수정
	public void update(Member m) throws SQLException{
		conn = db.conn();
		
		sql = "update members set pwd = ?, name = ? where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, m.getPwd());
		ps.setString(2, m.getName());
		ps.setInt(3, m.getNo());
		
		ps.executeUpdate();
	}
	
	//select id, name, locNo, favNo where ?
	public ArrayList<Member> selectIdName(HashMap<String, String> args) throws SQLException{
		ArrayList<Member> list = new ArrayList<>();
		
		conn = db.conn();
		
		sql = "select id, name, location_no, favorite_no from members where ";
		
		int cnt = args.size() -1;
		for(Entry<String, String> entry : args.entrySet()) {
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
			list.add(new Member(rs.getString(1), rs.getString(2),
					rs.getInt(3), rs.getInt(4)));
		}
		return list;
	}
	
	//select * from members where ?
	public ArrayList<Member> select(HashMap<String, String> args) throws SQLException{
		ArrayList<Member> list = new ArrayList<>();
		
		conn = db.conn();
		
		if(args == null) {
			sql = "select * from members";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
			}
		return list;
		}
		
		sql = "select * from members where ";
		
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
			list.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
		}
		return list;
	}
	
	
	//회원 탈퇴
	public void delete(int no) throws SQLException {
		conn = db.conn();
		
		sql = "delete from members where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, no);
		
		ps.executeUpdate();
	}
	
	public void delete(Member m) throws SQLException{
		
	}
	
	public void close() throws SQLException {
        if (rs != null)
            rs.close();
        ps.close();
        conn.close();
    }
	
}
