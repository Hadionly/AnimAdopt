public class Review {
	protected int reviewId;			//each review has unique ID, auto-incremented from 300
	protected int animalId;			//foreign key corresponding to an animalId in animals table
	protected int userId;			//user id of the person who posted the review
	protected String postingUser;	//user name of person who posted the review
	protected String contents;		//the contents of the review
	protected String category;		//the category of the review, i.e. "CRAY-CRAY"
	
	//constructors - - - -
	Review(){
	
	}
	
	Review(int rReviewId, int rAnimalId, int rUserId, String rPostingUser, String rContents, String rCategory){
		this.reviewId = rReviewId;
		this.animalId = rAnimalId;
		this.userId = rUserId;
		this.postingUser = rPostingUser;
		this.contents = rContents;
		this.category = rCategory;		
	}
	
	Review(int rAnimalId, int rUserId, String rPostingUser, String rContents, String rCategory){
		this.animalId = rAnimalId;
		this.userId = rUserId;
		this.postingUser = rPostingUser;
		this.contents = rContents;
		this.category = rCategory;		
	}
	
	//setters - - - -
	public void setReviewId(int newReviewId) {
		this.reviewId = newReviewId;
	}
	
	public void setAnimalId(int newAnimalId) {
		this.animalId = newAnimalId;
	}
	
	public void setUserId(int newUserId) {
		this.userId = newUserId;
	}
	
	public void setPostingUser(String newPostingUser) {
		this.postingUser = newPostingUser;
	}
	
	public void setContents(String newContents) {
		this.contents = newContents;
	}
	
	public void setCategory(String newCategory) {
		this.category = newCategory;
	}
	
	//getters - - - -
	public int getReviewId() {
		return this.reviewId;
	}
	
	public int getAnimalId() {
		return this.animalId;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getPostingUser() {
		return this.postingUser;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public String getCategory() {
		return this.category;
	}	
}