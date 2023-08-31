package vo;

public class Favorite {
	private int no;
	private String name;
	
	public Favorite() {}
	
	public Favorite(int no, String name) {
		this.no = no;
		this.name = name;
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
		return "Favorite [no=" + no + ", name=" + name + "]";
	}
	
	
}
