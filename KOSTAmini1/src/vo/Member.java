package vo;

import java.sql.Date;

public class Member {
	private int no;
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	private int locationNo;
	private int favoriteNo;
	private String admin;
	
	public Member() {}
	
	public Member(int no, String id, String pwd, String name, String email,
			Date joinDate, int locationNo, int favoriteNo, String admin) {
		this.no = no;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.joinDate = joinDate;
		this.locationNo = locationNo;
		this.favoriteNo = favoriteNo;
		this.admin = admin;
	}
	
	public Member(String id, String name, int locationNo, int favoriteNo) {
		this.id = id;
		this.name = name;
		this.locationNo = locationNo;
		this.favoriteNo = favoriteNo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public int getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(int locationNo) {
		this.locationNo = locationNo;
	}

	public int getFavoriteNo() {
		return favoriteNo;
	}

	public void setFavoriteNo(int favoriteNo) {
		this.favoriteNo = favoriteNo;
	}
	
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Member [no=" + no + ", id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email +  ", joinDate=" + joinDate + ", locationNo=" + locationNo
				+ ", favoriteNo=" + favoriteNo + ", admin=" + admin + "]";
	}
}
