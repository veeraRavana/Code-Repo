package AdminApplication;

import AdminApplicationModel.TransactionConfigurationTypePOJO;
import AdminController.AddorModTransactionConfigurationController;
import AdminController.TransactionConfigurationController;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class TransactionConfigurationMain {
	
	  private final TabManager tabManager 	= TabManager.getInstance();
	  
	  @FXML
	  public TabPane mainTabPane; // TabPane for managing tabs
	  public String Nodename						= null; 
		
	public TransactionConfigurationMain(String nodeName, TabPane tabPane) {
		this.mainTabPane 	= tabPane;
		this.Nodename 		= nodeName;
		loadTransactionConfigurationMain(nodeName, tabPane);
	}
	
	public void loadTransactionConfigurationMain(String nodeName, TabPane tabPane) {

		FXMLLoader loader 				= tabManager.openTab("Transaction Configuration",
								"/AdminFxml/TransactionConfigurationMain.fxml", false, null);
		TransactionConfigurationController transConfig	= loader.getController();
		transConfig.setTransactionConfigurationMain(this);
	}
	
	
	public void AddConfigurationType(String ScreenName) {
		
		int mode 				= 0; // 0 for Add mode
		FXMLLoader loader 		= tabManager.openTab("Add "+ScreenName,
								"/AdminFxml/AddModTransactionConfiguration.fxml", true, Nodename);
		AddorModTransactionConfigurationController controller = loader.getController();
		controller.setTransactionConfigurationAdd(this, mainTabPane,Nodename, mode,ScreenName,null); // mode is 0
	}
	
	public void ModConfigurationType(String ScreenName,int mode, int TransactionID,TransactionConfigurationTypePOJO transConfigTypePOJO	) {
//		int mode				=1; // 1 for Modify mode
		FXMLLoader loader 		= tabManager.openTab("Modify "+ScreenName,
								"/AdminFxml/AddModTransactionConfiguration.fxml", true, Nodename);
		AddorModTransactionConfigurationController controller = loader.getController();
		controller.setTransactionConfigurationAdd(this, mainTabPane,Nodename, mode,ScreenName,transConfigTypePOJO); // mode is 1 for modification
	}
	
	
}
