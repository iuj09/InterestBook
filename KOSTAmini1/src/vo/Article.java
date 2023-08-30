package vo;

import java.sql.Date;

public class Article {
	private int num;
	private String title;
	private String content;
	private int heart;
	private Date wDate;
	private Date eDate;
	private int writer;
	private int category;

	public Article() {
	}

	public Article(int num, String title, String content, int heart, Date wDate, Date eDate, int writer, int category) {
		this.num = num;
		this.title = title;
		this.content = content;
		this.heart = heart;
		this.wDate = wDate;
		this.eDate = eDate;
		this.writer = writer;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Article [num=" + num + ", title=" + title + ", content=" + content + ", wDate=" + wDate + ", eDate=" + eDate
				+ ", writer=" + writer + ", category=" + category + "]";
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public Date getwDate() {
		return wDate;
	}

	public void setwDate(Date wDate) {
		this.wDate = wDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	public int getWriter() {
		return writer;
	}

	public void setWriter(int writer) {
		this.writer = writer;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}