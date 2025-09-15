package AdminApplicationModel;

import java.security.PublicKey;

public class TaxProfilePojo {

	
	public Integer TaxProfileID;
	public String TaxprofileName;
	public String TaxprofileType;
	
	public Integer TaxVersion;
	public double CGstper;
	public double SGstper;

	// this is to compare the old and new data 

	public boolean equals(Object obj) {
		TaxProfilePojo taxpro = (TaxProfilePojo) obj;
		
		if(taxpro.TaxProfileID 		!=this.TaxProfileID) {
			return false;
		}
		if(taxpro.TaxprofileName 	!=this.TaxprofileName) {
			return false;
		}
		if(taxpro.TaxVersion 	!=this.TaxVersion) {
			return false;
		}
		
		if(taxpro.TaxprofileType 	!=this.TaxprofileType) {
			return false;
		}
		if(taxpro.CGstper 			!=this.CGstper) {
			return false;
		}
		if(taxpro.SGstper 			!=this.SGstper) {
			return false;
		}
		

		return true;
		
	}
	
	public boolean checkStringEqual(String st1, String st2){
		if(st1 == null){
			st1 = "null";
		}
		if(st2 == null){
			st2 = "null";
		}
		if(st1.equals(st2)){
			return true;
		} else{
			return false;
		}
	}
	
}
