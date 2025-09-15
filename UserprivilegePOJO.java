package AdminApplicationModel;

public class UserprivilegePOJO {

	
	private int userseqID;
	private int nodeId;

	
	public UserprivilegePOJO(int userseqID,int nodeId) {
		
		this.userseqID		= userseqID;
		this.nodeId			= nodeId;
	}

	public int getUserseqID() {		return userseqID;	}
	public void setUserseqID(int userseqID) {
		this.userseqID = userseqID;
	}

	public int getNodeId() {		return nodeId;	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	

	
	
/*	public boolean equals(Object obj) {
		
		UserprivilegePOJO userpojo = (UserprivilegePOJO) obj;
		
		if(userpojo.userseqID 		!=this.userseqID) {
			return false;
		}
		if(userpojo.nodeId 	!=this.nodeId) {
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
	}*/
}
