public class Flarper {

	private int health;
	private boolean alive;
	private int playerID;
	private Sword sword;
	
	public Flarper(int playerID) {
		health = 100;
		setAlive(true);
		this.playerID = playerID;
	}
	
	public void pairSword(Sword sword) {
		this.setSword(sword);
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		checkDeath();
	}
	
	private void checkDeath() {
		if(health <= 0) {
			setAlive(false);
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Sword getSword() {
		return sword;
	}

	public void setSword(Sword sword) {
		this.sword = sword;
	}
}
