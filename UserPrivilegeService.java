package AdminServices;

import java.util.List;

import AdminApplicationModel.UserPrivilegeInsertionDataPOJO;
import AdminApplicationModel.UserPrivilegeTable;
import AdminApplicationModel.UserprivAllnodetable;
import AdminApplicationModel.UserprivilegePOJO;
import AdminDAO.UserPrivilegeDAO;
import LoginPageModel.ModuleNode;
import javafx.collections.ObservableList;

public class UserPrivilegeService {

	private final UserPrivilegeDAO userprivDAO  	= new UserPrivilegeDAO();
	
	UserprivilegePOJO[] userpojo 					= null;
	UserPrivilegeTable userpribtable				= null;// for the main screen table set up
	
/***************************************
 *user Privilege main screen methods
 ***************************************/	
	public List<UserPrivilegeTable> getAllUsersWithPrivilege() {
		return userprivDAO.getAllUsersWithPrivilege();
	}
	
	public List<UserprivAllnodetable> getUserNodesByUserseqID(int userseqID) {
	    return userprivDAO.getUserNodesByUserseqID(userseqID);
	}

	
/***************************************
 *user Privilege Add screen methods
 ***************************************/	
	
	public  List<UserprivAllnodetable> getAllmodulesandNodes(String loadtabe, int UserseqID){
		return userprivDAO.getAllmodulesandNodes(loadtabe,UserseqID);
		
	} 
	
	public boolean SaveUserprivilege(UserprivilegePOJO[] userpojo, int UserseqID  ) {
		this.userpojo		= userpojo;
		boolean isInserted	= userprivDAO.save_user_node_privileges(userpojo,UserseqID );
		return isInserted;
	}
	
	
/***************************************
 * New Screen Name methods
 ***************************************/

	public ObservableList<ModuleNode> setModuleNodeTableData(){
		return userprivDAO.setModuleNodeTableData();
	}

	public Integer saveNewScreenNameIntoTable(int ModuleID, String screenName) {
		return userprivDAO.saveNewScreenNameIntoTable(ModuleID, screenName);
	}

	public boolean UserPrivilegeLog(UserPrivilegeInsertionDataPOJO UserIntData, Integer userSeqid) {
	return userprivDAO.UserPrivilegeLog(UserIntData,userSeqid);
		
	}
	
	

}
