package AdminApplication;

import AdminController.MaterialMasterMainController;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;


public class MaterialMasterMain {
	
	private final TabManager tabManager = TabManager.getInstance();
	 @FXML public TabPane mainTabPane; // TabPane for managing tabs
	 String Nodename					= null; 
	
	 public MaterialMasterMain(String nodeName, TabPane tabPane) {
		this.mainTabPane	= tabPane;
	  	this.Nodename		= nodeName;
	  	loadMaterialMaster(nodeName, tabPane);
	 }
	
		public void loadMaterialMaster(String nodeName, TabPane tabPane) {
			
			String fxmlPath 		= "/AdminFxml/MaterialMasterMain.fxml";
			FXMLLoader loader 		= tabManager.openTab(nodeName, fxmlPath, false, null);
			MaterialMasterMainController materialMasterMainController = loader.getController();
			materialMasterMainController.setMaterialMasterMain(this);		
		}
	
//		public void AddMaterialTypeMaster() {
//			
//			String fxmlPath			= "/AdminFxml/AddModMaterialMaster.fxml";
//			FXMLLoader loader 		= tabManager.openTab("Add Material Type", fxmlPath, true, Nodename);
//			AddMaterialMasterController addMaterialController = loader.getController();
//			addMaterialController.LoadAddModMaterialScreen(this, mainTabPane, Nodename, 0);
//		}

}
/*
 * 	
//	import AdminController.AddorModMaterialTypeController;

	private final TabManager tabManager = TabManager.getInstance();
	 @FXML public TabPane mainTabPane; // TabPane for managing tabs
	 String Nodename					= null; 
	 
	 
	public MaterialTypeMasterMain(String nodeName, TabPane tabPane) {
		// Initialize the Material Type Master Main screen
		this.mainTabPane = tabPane;
		this.Nodename = nodeName;
		loadMaterialTypeMaster(nodeName, tabPane);
	}


*/
 