package AdminApplicationModel;

public class UserPrivilegeInsertionDataPOJO {
	
	
	private int UserseqID;
	private String UserNodeIDs;

	private String TransactionName;

	private String UserName;
	private String UserIPAddress;
	
	public UserPrivilegeInsertionDataPOJO(int userseqID,String userNodeIDs,String transactionName,
			String userName,String userIPAddress) {
	
		this.UserseqID			= userseqID;
		this.UserNodeIDs 		= userNodeIDs;
		this.TransactionName	= transactionName;
		this.UserName 			= userName;
		this.UserIPAddress		= userIPAddress;

	}

	public int getUserseqID() {		return UserseqID;	}
	public void setUserseqID(int userseqID) {
		UserseqID = userseqID;
	}

	public String getUserNodeIDs() {		return UserNodeIDs;	}
	public void setUserNodeIDs(String userNodeIDs) {
		this.UserNodeIDs = userNodeIDs;
	}

	public String getTransactionName() {		return TransactionName;	}
	public void setTransactionName(String transactionName) {
		this.TransactionName = transactionName;
	}
	
	public String getUserName() {		return UserName;	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	public String getUserIPAddress() {		return UserIPAddress;	}
	public void setUserIPAddress(String userIPAddress) {
		this.UserIPAddress = userIPAddress;
	}
}
