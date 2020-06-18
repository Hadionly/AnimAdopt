import java.sql.SQLException;

public class Animal {
	protected int animalId;			//each animal has unique id, auto-incremented from 100
	protected String animalName;	//name of animal
	protected String species;		//species of animal
	protected String birthdate;		//birth date of animal: stored in YYYY/MM/DD format
	protected float price;			//price to adopt the animal
	protected String owner;			//user name of the owner/posting user
	protected int traitId1; 		//foreign key id corresponding to the trait table
	protected int traitId2;
	protected int traitId3;
	
	protected String traitList;
	
	//constructors - - - - 
	public Animal(){
	
	}
	
	public Animal(int aAnimalId, String aAnimalName, String aSpecies, String aBirthdate, float aPrice, 
			String aOwner, int aTraitId1, int aTraitId2, int aTraitId3){
		this.animalId = aAnimalId;
		this.animalName = aAnimalName;
		this.species = aSpecies;
		this.birthdate = aBirthdate;
		this.price = aPrice;
		this.owner = aOwner;
		this.traitId1 = aTraitId1;	
		this.traitId2 = aTraitId2;
		this.traitId3 = aTraitId3;
	}
	
	public Animal(int aAnimalId, String aAnimalName, String aSpecies, String aBirthdate, float aPrice, 
			String aOwner, int aTraitId1, int aTraitId2, int aTraitId3, String traits)  throws SQLException {
		this.animalId = aAnimalId;
		this.animalName = aAnimalName;
		this.species = aSpecies;
		this.birthdate = aBirthdate;
		this.price = aPrice;
		this.owner = aOwner;
		this.traitId1 = aTraitId1;	
		this.traitId2 = aTraitId2;
		this.traitId3 = aTraitId3;
		this.traitList = this.getTraitList(); 
	}
	
	public Animal(String aAnimalName, String aSpecies, String aBirthdate, float aPrice, String aOwner, 
			int aTraitId1, int aTraitId2, int aTraitId3){
		this.animalName = aAnimalName;
		this.species = aSpecies;
		this.birthdate = aBirthdate;
		this.price = aPrice;
		this.owner = aOwner;
		this.traitId1 = aTraitId1;	
		this.traitId2 = aTraitId2;
		this.traitId3 = aTraitId3;	
	}
	
	//setters - - - -
	public void setPetId(int newAnimalId) {
		this.animalId = newAnimalId;
	}
	
	public void setPetName(String newAnimalName) {
		this.animalName = newAnimalName;
	}
	
	public void setSpecies(String newSpecies) {
		this.species = newSpecies;
	}
	
	public void setBirthdate(String newBirthdate) {
		this.birthdate = newBirthdate;
	}
	
	public void setPrice(float newPrice) {
		this.price = newPrice;
	}
	
	public void setOwner(String newOwner) {
		this.owner = newOwner;
	}
	
	public void setTraitId1(int newTraitId) {
		this.traitId1 = newTraitId;
	}
	
	public void setTraitId2(int newTraitId) {
		this.traitId2 = newTraitId;
	}
	
	public void setTraitId3(int newTraitId) {
		this.traitId3 = newTraitId;
	}
	
	//getters - - - -
	public int getAnimalId() {
		return this.animalId;
	}
	
	public String getAnimalName() {
		return this.animalName;
	}
	
	public String getSpecies() {
		return this.species;
	}
	
	public String getBirthdate() {
		return this.birthdate;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public int getTraitId1() {
		return this.traitId1;
	}
	
	public int getTraitId2() {
		return this.traitId2;
	}
	
	public int getTraitId3() {
		return this.traitId3;
	}
	
	public String getTraitList() throws SQLException {
		TraitDAO t = new TraitDAO();
		
		if(this.traitId1 == 200) {
			return "N/A";
		}
		
		String t1 = t.getTrait(this.traitId1);
		
		if(this.traitId2 == 200) {		
			return t1;
		}
	
		String t2 = t.getTrait(this.traitId2);
		
		if(this.traitId3 == 200) {
			return t1 + ", " + t2;
		}
		
		String t3 = t.getTrait(this.traitId3);
				
		return t1 + ", " + t2 + ", " + t3;
	}
	
	public static String getTraitList(int tid1, int tid2, int tid3) throws SQLException {
		/*TraitDAO t = new TraitDAO();
		
		if(tid1 == 200) {
			return "N/A";
		}
		
		String t1 = t.getTrait(tid1);
		
		if(tid2 == 200) {		
			return t1;
		}
	
		String t2 = t.getTrait(tid2);
		
		if(tid3 == 200) {
			return t1 + ", " + t2;
		}
		
		String t3 = t.getTrait(tid3);
				
		return t1 + ", " + t2 + ", " + t3;*/
		return "test";
	}
}