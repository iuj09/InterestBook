package vo;

public class Favorite {
	private int no;
	private String name;
	private int heart;
	
	public Favorite() {}
	
	public Favorite(int no, String name, int heart) {
		this.no = no;
		this.name = name;
		this.heart = heart;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Favorite [no=" + no + ", name=" + name + ", heart=" + heart + "]";
	}
	
	
}
