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
		
		sql = "insert into members values(?, ?, ?, ?, ?, sysdate, ?, ?)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, m.getNo());
		ps.setString(2, m.getId());
		ps.setString(3, m.getPwd());
		ps.setString(4, m.getName());
		ps.setString(5, m.getEmail());
		ps.setInt(6, m.getLocationNo());
		ps.setInt(7, m.getFavoriteNo());
		
		ps.executeUpdate();
	}
	
	
	/**
	 * 마이페이지
	 * 	- 내 정보 조회
	 *  - 내 정보 수정
	 *  - meet 참가 확인
	 *  - 좋아요 표시한 게시물 확인
	 */
	//내 정보 조회
	public Member selectMyInfo(int num) throws SQLException{
		Member m = null;
		
		conn = db.conn();
		
		sql = "select * from members where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, num);
		
		rs = ps.executeQuery();
		if(rs.next()) {
			m = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8));
		}
		return m;
	}
	
	
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
	
	
	//회원 검색(by no(관리자용))
	public Member selectByNum(int no) throws SQLException{
		Member m = null;
		
		conn = db.conn();
		
		sql = "select * from members where no = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, no);
		
		rs = ps.executeQuery();
		if(rs.next()) {
			m = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8));
		}
		return m;
	}
	
	//회원 검색(select id, name, locNo, favNo where id)
	public String selectById(String id) throws SQLException{
		String basicInfo = null;
		
		conn = db.conn();
		
		sql = "select id, name, location_no, favorite_no from members where id = ?";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, id);
		
		rs = ps.executeQuery();
		if(rs.next()) {
			basicInfo = "Member [id=" + rs.getString(1) + ", name=" + rs.getString(2) +
					", locationNo=" + rs.getInt(3) + ", favoriteNo=" + rs.getInt(4) + "]";
		}
		return basicInfo;
	}
	
	//회원 검색(select id, name, locNo, favNo where name)
		@SuppressWarnings("null")
		public ArrayList<String> selectByName(String name) throws SQLException{
			ArrayList<String> list = new ArrayList<>();
			
			conn = db.conn();
			
			sql = "select id, name, location_no, favorite_no from members where name = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new String("Member [id=" + rs.getString(1) + ", name=" + rs.getString(2) +
						", locationNo=" + rs.getInt(3) + ", favoriteNo=" + rs.getInt(4) + "]"));
			}
			return list;
		}
	
	//select * from members where
	public ArrayList<Member> select(HashMap<String, String> args) throws SQLException{
		ArrayList<Member> list = new ArrayList<>();
		
		conn = db.conn();
		
		if(args == null) {
			sql = "select * from members";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8)));
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
					rs.getString(5),  rs.getDate(6), rs.getInt(7), rs.getInt(8)));
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
