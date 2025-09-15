package AdminApplicationModel;

public class MaterialCategoryMainTablePOJO {
	private Integer MaterialCategoryID;
	private String MaterialCategoryName;
	private String MaterialCategoryStatus;

	// Additional fields for user tracking
	private String UserName;
	private String UserIPAddress;
	private String MatCatTransactionName;

	public MaterialCategoryMainTablePOJO(Integer materialCategoryID, String materialCategoryName,
			String materialCategoryStatus) {
		
		this.MaterialCategoryID 	= materialCategoryID;
		this.MaterialCategoryName 	= materialCategoryName;
		this.MaterialCategoryStatus = materialCategoryStatus;
	}
	
	
/*this is for the set and get data for the insertion into table*/	
	public MaterialCategoryMainTablePOJO(Integer materialCategoryID, String materialCategoryName,
			String materialCategoryStatus , String userName, String userIPAddress,
			String matcatTransactionName ) {
		this.MaterialCategoryID 	= materialCategoryID;
		this.MaterialCategoryName 	= materialCategoryName;
		this.MaterialCategoryStatus = materialCategoryStatus;
		this.UserName				= userName;
		this.UserIPAddress			= userIPAddress;
		this.MatCatTransactionName	= matcatTransactionName;
	}
	
	public int getMaterialCategoryID() {			return MaterialCategoryID;	}
	public void setMaterialCategoryID(Integer materialCategoryID) {
		this.MaterialCategoryID 		= materialCategoryID;
	}

	public String getMaterialCategoryName() {		return MaterialCategoryName;	}
	public void setMaterialCategoryName(String materialCategoryName) {
		this.MaterialCategoryName 	= materialCategoryName;
	}

	public String getMaterialCategoryStatus() {		return MaterialCategoryStatus;	}
	public void setMaterialCategoryStatus(String materialCategoryStatus) {
		this.MaterialCategoryStatus 	= materialCategoryStatus;
	}
	
	public String getUserName() {			return UserName;	}
	public void setUserName(String userName) {		
		this.UserName = userName;
	}

	public String getUserIPAddress() {		return UserIPAddress;	}
	public void setUserIPAddress(String userIPAddress) {
		this.UserIPAddress = userIPAddress;
	}
	
	public String getMaterialCategoryTransactionName() {		return MatCatTransactionName;	}
	public void setMaterialCategoryTransactionName(String materialCategoryTransactionName) {
		this.MatCatTransactionName = materialCategoryTransactionName;
	}
}
