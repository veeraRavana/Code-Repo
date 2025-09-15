package AdminApplicationModel;

public class UserPrivilegeTable {

	private Integer userseqID;
    private String userid;
    private String username;
    private String userPrivilege;
    private String dept;

    public UserPrivilegeTable(Integer userseqID, String userid,
    			String username, String userPrivilege, String dept) {
      
    	this.userseqID 		= userseqID;
        this.userid 		= userid;
        this.username 		= username;
        this.userPrivilege 	= userPrivilege;
        this.dept 			= dept;
    }
    

    public Integer getUserseqID() { return userseqID; }
    public void setUserseqID(Integer userseqID) {
		this.userseqID = userseqID;
	}
    
    public String getUserid() { return userid; }
    public void setUserid(String userid) {
		this.userid = userid;
	}
    
    public String getUsername() { return username; }
    public void setUsername(String username) {
		this.username = username;
	}
    
    public String getUserPrivilege() { return userPrivilege; }
    public void setUserPrivilege(String userPrivilege) {
		this.userPrivilege = userPrivilege;
	}
    
    public String getDept() { return dept; }
    public void setDept(String dept) {
		this.dept = dept;
	}
    
}
