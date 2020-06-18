
public class Crate {
	String username;
	int animalId;
	
	public Crate() {
		
	}
	
	public Crate(String user, int anId) {
		this.username = user;
		this.animalId = anId;
	}
	
	protected void setUsername(String user) {
		this.username = user;
	}
	
	protected void setAnId(int anId) {
		this.animalId = anId;
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	protected int getAnId() {
		return this.animalId;
	}
	
}
