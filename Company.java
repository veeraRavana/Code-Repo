package AdminApplicationModel;

public class Company {
	
    private  Integer companyId;
   	private  String companyName;
    private  String phone;
    private  String gst;
    private  String email;
	private  String address;
    private  String nationality;
    private  String shipment;
  
	private String TransactionName;

    private String UserName;
    private String userIPAddress;

/*this is to set the data for the table in
 *  the company register main screen */
	public Company(int companyId, String companyName, String phone,
    		String gst,String address,String nationality) {
    	
        this.companyId 		= companyId;
        this.companyName 	= companyName;
        this.phone 			= phone;
        this.gst 			= gst;
        this.address 		= address;
        this.nationality 	= nationality;
    }
    
 /* this method is to get and set the data
  *  for the modification and view screen */   
    public Company( String companyName, int companyId,String phone,String email,
    		String gst,String shipment ) {
    	  this.companyId 	= companyId;
          this.companyName 	= companyName;
          this.phone 		= phone;
          this.email		= email; 
          this.gst 			= gst;
          this.shipment		= shipment;
    }
    
    
    public Integer getCompanyId() {	return companyId;	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {	return companyName;	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getPhone() {	return phone;	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getGst() {	return gst;	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	
    public String getEmail() {	return email;	}
	public void setEmail(String email) {
		this.email = email;
	} 
	
	public String getAddress() { return address;	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getNationality() {	return nationality; }
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getShipment() {	return shipment;	}
	public void setShipment(String shipment) {
			this.shipment = shipment;
	}
	
    public String getTransactionName() { return TransactionName;	}
	public void setTransactionName(String transactionName) {
		TransactionName = transactionName;
	}

	public String getUserName() {		return UserName;	}
	public void setUserName(String userName) {	
		UserName = userName;
	}

	public String getUserIPAddress() {	return userIPAddress;	}
	public void setUserIPAddress(String userIPAddress) {
		this.userIPAddress = userIPAddress;
	}	
	
	
	
}



