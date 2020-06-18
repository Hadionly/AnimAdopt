public class User {
		protected int userId;			//each user has unique id, auto incremented from 1
		protected String username;
		protected String password;
		protected String firstname;
		protected String lastname;
		protected String email;
		protected int numReviews;		//number of reviews posted by a user
		protected int numPostings;		//number of animals posted by a user
		
		
		//constructors - - - - - - - - - 	
		public User() {
				
		}
		
		public User(int uId, String uUsername, String uPass, String uFirst, String uLast, String uEmail,
					int uNumReviews, int uNumPostings) {
			this.userId = uId;
			this.username = uUsername;
			this.password = uPass;
			this.firstname = uFirst;
			this.lastname = uLast;
			this.email = uEmail;
			this.numReviews = uNumReviews;
			this.numPostings = uNumPostings;
		}

		public User(String uUsername, String uPass, String uFirst, String uLast, String uEmail,
				int uNumReviews, int uNumPostings) {
		this.username = uUsername;
		this.password = uPass;
		this.firstname = uFirst;
		this.lastname = uLast;
		this.email = uEmail;
		this.numReviews = uNumReviews;
		this.numPostings = uNumPostings;
	}
		
		//setters - - - - - - - - - 
		public void setId(int newId) {
			this.userId = newId;
		}
		
		public void setUsername(String newUsername) {
			this.username = newUsername;
		}
		
		public void setPass(String newPass) {
			this.password = newPass;
		}
		
		public void setFirst(String newFirst) {
			this.firstname = newFirst;
		}
		
		public void setLast(String newLast) {
			this.lastname = newLast;
		}
		
		public void setEmail(String newEmail) {
			this.email = newEmail;
		}
		
		public void setNumReviews(int newNumReviews) {
			this.numReviews = newNumReviews;
		}
		
		public void setNumPostings(int newNumPostings) {
			this.numPostings = newNumPostings;
		}
		
		//getters - - - - - - - - - 
		public int getUserId() {
			return this.userId;	
		}
		
		public String getUsername() {
			return this.username;
		}
		
		public String getPass() {
			return this.password;
		}
		
		public String getFirst() {
			return this.firstname;
		}
		
		public String getLast() {
			return this.lastname;
		}
		
		public String getEmail() {
			return this.email;
		}	
		
		public int getNumReviews() {
			return this.numReviews;
		}
		
		public int getNumPostings() {
			return this.numPostings;
		}
}