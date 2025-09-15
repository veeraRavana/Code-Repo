package AdminApplication;

import AdminApplicationModel.CompanyRegisterDataWrapper;
import AdminController.*;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class CompanyRegisterMain {

	private final TabManager tabManager = TabManager.getInstance();
	 @FXML public TabPane mainTabPane; // TabPane for managing tabs
	 String Nodename					= null; 
	
	public CompanyRegisterMain(String nodeName, TabPane tabPane) {
		this.mainTabPane	= tabPane;
	  	this.Nodename		= nodeName;
    	loadCompanyRegister(nodeName,tabPane);
	}
	
/*this is to load the Company Register Main Screen */
	public void loadCompanyRegister(String nodeName, TabPane tabPane) {
		String fxmlPath			= "/AdminFxml/CompanyRegisterMain.fxml";
		FXMLLoader loader 		= tabManager.openTab(nodeName, fxmlPath, false, null);
		
	    CompanyRegistermainController CompRegCont= loader.getController();
	    CompRegCont.setCompanyRegisterMain(this);		
	}
	
/*this method is to load the ad or mod-company register Screen*/
	public void addCompanyRegister() {
		String fxmlPath			= "/AdminFxml/AddmodCompanyRegister.fxml";
		FXMLLoader loader 		=  tabManager.openTab("Add Company Details",fxmlPath , true, Nodename);
		AddorModCompanyRegisterController AddCompRegCont= loader.getController();
		AddCompRegCont.LoadAddorModCompanyRegisterScreen(this,mainTabPane,Nodename,0);
		
	}
	
	public CompanyRegisterDataWrapper DataWraper;
	
	public void modViewCompanyRegister(int mode,CompanyRegisterDataWrapper dataWraper) {
		DataWraper				= dataWraper;
		String screenName		= mode == 1 ? "Modify Company Details":"View Company Details";
		String fxmlPath			= "/AdminFxml/AddmodCompanyRegister.fxml";
		FXMLLoader loader 		=  tabManager.openTab(screenName, fxmlPath, true, Nodename);
		AddorModCompanyRegisterController AddCompRegCont= loader.getController();
		AddCompRegCont.LoadAddorModCompanyRegisterScreen(this,mainTabPane,Nodename,mode);	
	}
	
	
	
	
	
}
