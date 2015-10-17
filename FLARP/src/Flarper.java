public class Flarper {

	private int health;
	private boolean alive;
	private int sword;
	private int playerID;
	
	public Flarper(int playerID) {
		health = 100;
		alive = true;
		this.playerID = playerID;
	}
	
	public void pairSword(int swordID) {
		sword = swordID;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		checkDeath();
	}
	
	private void checkDeath() {
		if(health <= 0) {
			alive = false;
		}
	}
}
