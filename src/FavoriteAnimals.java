public class FavoriteAnimals {
	protected String user;
	protected int favAnimalId;
	
	public FavoriteAnimals() {
		
	}
	
	public FavoriteAnimals(String username, int anId) {
		this.user = username;
		this.favAnimalId = anId;
	}
	
	public void setUser(String newUser) {
		this.user = newUser;
	}
	
	public void setAnimalId(int newAnId) {
		this.favAnimalId = newAnId;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public int getAnimalId() {
		return this.favAnimalId;
	}
	
}
