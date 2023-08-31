package vo;

import java.sql.Date;

public class Replies {
	private int no; // 댓글 번호
	private String content; // 댓글 내용
	private Date w_date; // 작성일
	private Date e_date; // 수정일
	private int heart; // 좋아요
	private int article_no;// 게시판 고유번호
	private int member_no; // 멤버 고유번호

	public Replies() {

	}

	public Replies(int no, String content, Date w_date, Date e_date, int heart, int article_no, int member_no) {
		this.no = no;
		this.content = content;
		this.w_date = w_date;
		this.e_date = e_date;
		this.heart = heart;
		this.article_no = article_no;
		this.member_no = member_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getW_date() {
		return w_date;
	}

	public void setW_date(Date w_date) {
		this.w_date = w_date;
	}

	public Date getE_date() {
		return e_date;
	}

	public void setE_date(Date e_date) {
		this.e_date = e_date;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getArticle_no() {
		return article_no;
	}

	public void setArticle_no(int board_no) {
		this.article_no = board_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	@Override
	public String toString() {
		return "작성자:" + member_no + " 게시판 번호:" + article_no + " \n [" + no + "] " + content + " 작성일:" + w_date + " 수정일:"
				+ e_date + " 좋아요:" + heart;
	}

}
