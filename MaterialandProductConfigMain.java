package AdminApplication;

import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.MaterialCategoryMainTablePOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminController.AddModMaterialCategoryController;
import AdminController.AddModProductCategoryController;
import AdminController.MaterialandProductConfigMainController;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class MaterialandProductConfigMain {
	
	private final TabManager tabManager = TabManager.getInstance();
	 @FXML public TabPane mainTabPane; // TabPane for managing tabs
	 String Nodename					= null; 
	
	public MaterialandProductConfigMain(String nodeName, TabPane tabPane) {
		this.mainTabPane	= tabPane;
	  	this.Nodename		= nodeName;
		loadMaterialandProductConfig(nodeName, tabPane);
	}
	
	
/*Material Category*/
	
	public void loadMaterialandProductConfig(String nodeName, TabPane tabPane) {
		String fxmlPath			= "/AdminFxml/MaterialandProductConfigurationMain.fxml";
		FXMLLoader loader 		= tabManager.openTab(nodeName, fxmlPath, false, null);
		
		MaterialandProductConfigMainController CompRegCont		= loader.getController();
	    CompRegCont.setMaterialCategoryMain(this);
	}
	
	public void AddModMaterialCategory(MaterialCategoryDataPOJO SelectedItemData,Integer mode) {
	 
		String fxmlPath			= "/AdminFxml/AddModMaterialCategory.fxml";
		String ScreenName 		= mode== 0 ? "Add Material Category" :"Modify Material Category";
		FXMLLoader loader 		= tabManager.openTab(ScreenName , fxmlPath , true, Nodename);
		AddModMaterialCategoryController AddMaterialCat	= loader.getController();
		AddMaterialCat.LoadAddorModMaterialCategoryScreen(this,mainTabPane,Nodename,SelectedItemData,mode);
	}

	
/*Product Category */
	public void AddModProductCategory(ProductCategoryDataPOJO SelectedItemData,Integer mode) {
		
		String fxmlPath			= "/AdminFxml/AddModProductCategory.fxml";
		String ScreenName 		= mode== 0 ? "Add Product Category" :"Modify Product Category";
		FXMLLoader loader 		= tabManager.openTab(ScreenName , fxmlPath , true, Nodename);
		AddModProductCategoryController AddProductCat	= loader.getController();
		AddProductCat.LoadAddorModProductCategoryScreen(this,mainTabPane,Nodename,SelectedItemData,mode);
		
	}
	
	
}
