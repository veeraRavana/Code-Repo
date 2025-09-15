package AdminApplicationModel;

public class ProductCategoryMainTablePOJO {

	private Integer ProductCategoryID;
	private String ProductCategoryName;
	private String ProductCategoryStatus;

	// Additional fields for user tracking
	private String UserName;
	private String UserIPAddress;
	private String ProductCatTransactionName;
	
	public ProductCategoryMainTablePOJO(Integer productCategoryID,
			String productCategoryName, String productCategoryStatus) {
		this.ProductCategoryID 		= productCategoryID;
		this.ProductCategoryName 	= productCategoryName;
		this.ProductCategoryStatus 	= productCategoryStatus;
	}

	public ProductCategoryMainTablePOJO(Integer productCategoryID,
			String productCategoryName, String productCategoryStatus,
			String userName,String userIPAddress, String ProdCatTransactionName) {
		this.ProductCategoryID 			= productCategoryID;
		this.ProductCategoryName 		= productCategoryName;
		this.ProductCategoryStatus 		= productCategoryStatus;
		this.UserName 					= userName;
		this.UserIPAddress 				= userIPAddress;
		this.ProductCatTransactionName 	= ProdCatTransactionName;
	}
	
	
	public int getProductCategoryID() {		return ProductCategoryID;	}
	public void setProductCategoryID(Integer productCategoryID) {
		ProductCategoryID = productCategoryID;
	}

	public String getProductCategoryName() {		return ProductCategoryName;	}
	public void setProductCategoryName(String productCategoryName) {
		ProductCategoryName = productCategoryName;
	}

	public String getProductCategoryStatus() {		return ProductCategoryStatus;	}
	public void setProductCategoryStatus(String productCategoryStatus) {
		ProductCategoryStatus = productCategoryStatus;
	}

// Additional fields for user tracking	
	public String getUserName() {		return UserName;	}
	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserIPAddress() {		return UserIPAddress;	}
	public void setUserIPAddress(String userIPAddress) {
		UserIPAddress = userIPAddress;
	}

	public String getMatCatTransactionName() {		return ProductCatTransactionName;	}
	public void setMatCatTransactionName(String ProdCatTransactionName) {
		ProductCatTransactionName = ProdCatTransactionName;
	}
	
}
