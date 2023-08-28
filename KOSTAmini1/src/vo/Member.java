package vo;

import java.sql.Date;

public class Member {
	private int num;
	private String id;
	private String pwd;
	private String name;
	private Date joinDate;
	private int locationNum;
	private int favoriteNum;
	
	public Member() {}
	
	public Member(int num, String id, String pwd, String name, Date joinDate, int locationNum, int favoriteNum) {
		this.num = num;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.joinDate = joinDate;
		this.locationNum = locationNum;
		this.favoriteNum = favoriteNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public int getLocationNum() {
		return locationNum;
	}

	public void setLocationNum(int locationNum) {
		this.locationNum = locationNum;
	}

	public int getFavoriteNum() {
		return favoriteNum;
	}

	public void setFavoriteNum(int favoriteNum) {
		this.favoriteNum = favoriteNum;
	}


	@Override
	public String toString() {
		return "Member [num=" + num + ", id=" + id + ", pwd=" + pwd + ", name=" + name + ", joinDate=" + joinDate + ", locationNum=" + locationNum
				+ ", favoriteNum=" + favoriteNum + "]";
	}
	
	
}
