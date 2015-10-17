
public class Sword {
	private boolean active;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Sword(int id) {
		active = true;
		this.id = id;
	}
}
