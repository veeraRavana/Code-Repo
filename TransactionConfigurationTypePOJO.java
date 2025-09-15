package AdminApplicationModel;

public class TransactionConfigurationTypePOJO {

	private int TransactionId;
	private String TransactionType; // Type of transaction (e.g., "Sales type", "Payment type")
	private String TransactionTypeName;  
	private String TransactionDescription;
	private Boolean TransactionStatus;
	private String TransactionMode; // Name of the transaction type


	// Additional fields for user tracking
	private String UserName;
	private String userIPAddress;
	
	
	public TransactionConfigurationTypePOJO(
			int transactionId, String transactionType,
			String transactionName, String transactionDescription,
			Boolean transactionStatus) {
		
		this.TransactionId		 	= transactionId;
		this.TransactionType 		= transactionType;
		this.TransactionTypeName 	= transactionName;
		this.TransactionDescription = transactionDescription;
		this.TransactionStatus 		= transactionStatus;
	}
	
	 public TransactionConfigurationTypePOJO() {
		// Default constructor
	}
	
	public int getTransactionId() {					return TransactionId;	}
	public void setTransactionId(int transactionId) {
		this.TransactionId = transactionId;
	}

	public String getTransactionType() {			return TransactionType;	}
	public void setTransactionType(String transactionType) {
		this.TransactionType = transactionType;
	}

	public String getTransactionTypeName() {		return TransactionTypeName;	}
	public void setTransactionTypeName(String transactionTypeName) {
		this.TransactionTypeName = transactionTypeName;
	}

	public String getTransactionDescription() {		return TransactionDescription;	}
	public void setTransactionDescription(String transactionDescription) {
		this.TransactionDescription = transactionDescription;
	}


	public Boolean getTransactionStatus() {			return TransactionStatus;	}
	public void setTransactionStatus(Boolean transactionStatus) {
		this.TransactionStatus = transactionStatus;
	}
	
	public String getTransactionMode() {			return TransactionMode;	}
	public void setTransactionMode(String transactionMode) {
		this.TransactionMode = transactionMode;
	}

 // Getters and Setters for user tracking fields
	public String getUserName() {					return UserName;	}
	public void setUserName(String userName) {
		this.UserName = userName;
	}

	public String getUserIPAddress() {				return userIPAddress;	}
	public void setUserIPAddress(String userIPAddress) {
		this.userIPAddress = userIPAddress;
	}
	
}
