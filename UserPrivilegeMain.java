package AdminApplication;

import AdminApplicationModel.UserPrivilegeTable;
import AdminController.*;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class UserPrivilegeMain {
	 
	 private final TabManager tabManager = TabManager.getInstance();
	 @FXML public TabPane mainTabPane; // TabPane for managing tabs
	 
	 String Nodename= null; 

	
	
	 public UserPrivilegeMain(String nodeName, TabPane tabPane) {
	    	
	    	this.mainTabPane	= tabPane;
	    	this.Nodename		= nodeName;
	    	loadUserPrivilegeAdd(nodeName,tabPane);
	    }
	 
/*this is the main screen loader method */	 
	   public void loadUserPrivilegeAdd(String nodeName, TabPane tabPane) {
		    String fxmlPath			= "/AdminFxml/UserPrivilegeMain.fxml";
	        FXMLLoader loader 		= tabManager.openTab(nodeName, fxmlPath, false, null);
	        UserprivilegeController userprivmain= loader.getController();
	        userprivmain.setUserprivilege(this);
	    }
	   
/*this method is to load the add or mod-tax profile*/  
	    public void addModUserPrivilege(int UserSeqId, UserPrivilegeTable selectedUserRow,int Mode) {
	    	
	    	  String fxmlPath			= "/AdminFxml/AddmodUserPrivilege.fxml";
	    	  String screenName 		= Mode == 1 ? "Modify User Privilege" : "Add User Privilege"; 
	    	  
	    	  FXMLLoader loader 		=  tabManager.openTab(screenName,  fxmlPath, true, Nodename);
		  	  AddormodUserprivilegeController AddUserPrivcontroller = loader.getController();
		  	  AddUserPrivcontroller.setComponetsForUserPrivilegeAddScreen(this, mainTabPane, Nodename, Mode, UserSeqId,selectedUserRow);
	    }
	    

	    
	    
	    
	    
	    
}
