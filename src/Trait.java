public class Trait {
	protected int traitId;		//each trait has a unique id, auto-incremented from 200
	protected String trait;		//the trait
	
	//constructors - - - -
	Trait(){
		
	}
	
	Trait(int tTraitId, String tTrait){
		this.traitId = tTraitId;
		this.trait = tTrait;	
	}
	
	Trait(String tTrait){
		this.trait = tTrait;
	}
	
	//setters - - - -
	public void setTraitId(int newTraitId) {
		this.traitId = newTraitId; 
	}
	
	public void setTrait(String newTrait) {
		this.trait = newTrait;
	}
	
	//getters - - - -
	public int getTraitId() {
		return this.traitId;
	}
	
	public String getTrait() {
		return this.trait;
	}
}