package AdminApplicationModel;


public class AddAddress {
	
	public int CompanyID;
    public int companyAddressID;
	
	public String Address1;
    public String Address2;
    
    public Integer stateID;
    public String State;
    
    public Integer DistrictID;
    public String District;
    
    public Integer CountryID;
    public String Countryorigin;
    
    public boolean isHeadOffice;
    public Integer Postalcode;
    
	public int getCompanyID() {	return CompanyID;	}
	public void setCompanyID(int companyID) {
		CompanyID = companyID;
	}
	
	public int getCompanyAddressID() {	return companyAddressID;	}
	public void setCompanyAddressID(int companyAddressID) {
		this.companyAddressID = companyAddressID;
	}
	
	public String getAddress1() {	return Address1;	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getAddress2() {	return Address2;	}
	public void setAddress2(String address2) {
		Address2 = address2;
	}
	
	public Integer getStateID() {	return stateID;		}
	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}
	
	public String getState() {	return State; 		}
	public void setState(String state) {
		State = state;
	}
	
	public Integer getDistrictID() {	return DistrictID;	}
	public void setDistrictID(Integer districtID) {
		DistrictID = districtID;
	}
	
	public String getDistrict() {	return District;	}
	public void setDistrict(String district) {
		District = district;
	}
	
	public Integer getCountryID() {	return CountryID;	}
	public void setCountryID(Integer countryID) {
		CountryID = countryID;
	}
	
	public String getCountryorigin() {	return Countryorigin;	}
	public void setCountryorigin(String countryorigin) {
		Countryorigin = countryorigin;
	}
	
	public boolean isHeadOffice() {	return isHeadOffice;	}
	public void setHeadOffice(boolean isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}
	
	
	public Integer getPostalcode() {	return Postalcode;	}
	public void setPostalcode(Integer postalcode) {
		Postalcode = postalcode;
	}

}
