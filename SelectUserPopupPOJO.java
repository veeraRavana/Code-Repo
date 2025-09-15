package AdminApplicationModel;

public class SelectUserPopupPOJO {

	private Integer userSeqID;
    private String userId;
    private String 	userName;
    private String 	userType;

    public SelectUserPopupPOJO(Integer userSeqID, String userId, String userName, String userType) {
    	
        this.userSeqID 	= userSeqID;
        this.userId 	= userId;
        this.userName 	= userName;
        this.userType 	= userType;
    }
    
    public Integer getUserSeqID() {		return userSeqID;	}
	public void setUserSeqID(Integer userSeqID) {
		this.userSeqID = userSeqID;
	}

	public String getUserId() {		return userId;	}//user Id VK 24 
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {		return userName;	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {		return userType;	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
 
}
