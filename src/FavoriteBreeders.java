public class FavoriteBreeders {
	protected String user;
	protected String favBreeder;
	
	public FavoriteBreeders() {
		
	}
	
	public FavoriteBreeders(String username, String fav) {
		this.user = username;
		this.favBreeder = fav;
	}
	
	public void setUser(String newUser) {
		this.user = newUser;
	}
	
	public void setFavBreeder(String newFavBreeder) {
		this.favBreeder = newFavBreeder;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getFavBreeder() {
		return this.favBreeder;
	}
}
