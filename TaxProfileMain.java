package AdminApplication;

import AdminController.TaxProfileController;
import AdminController.*;
import commonGUI.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class TaxProfileMain {
	
	
    private final TabManager tabManager = TabManager.getInstance();

    @FXML public TabPane mainTabPane; // TabPane for managing tabs
    
    String Nodename						= null;

    public TaxProfileMain(String nodeName, TabPane tabPane) {
    	
    	this.mainTabPane	= tabPane;
    	this.Nodename		= nodeName;
//        tabManager.openProductTab(nodeName, "/AdminFxml/TaxProfilemain.fxml", tabPane);
    	  loadTaxProfileMain(nodeName,tabPane);
    }
    
    public void loadTaxProfileMain(String nodeName, TabPane tabPane) {
//      FXMLLoader loader 		=   tabManager.openParentTab(nodeName, "/AdminFxml/TaxProfilemain.fxml", tabPane,false);
//    	FXMLLoader loader 		=   tabManager.openParentTab(nodeName, "/AdminFxml/TaxProfilemain.fxml"	);
    	FXMLLoader loader 		= tabManager.openTab("Tax Master", "/AdminFxml/TaxProfilemain.fxml", false, null);
    	
    	TaxProfileController taxprocont= loader.getController();
        taxprocont.setTaxProfileMain(this);
      }

    
    /*this method is to load the add or mod-tax profile*/  
    public void addTaxProfile() {
    	  FXMLLoader loader 		=  tabManager.openTab("Add Tax Master", "/AdminFxml/AddmodviewTaxprofile.fxml", true, Nodename);
	  	  addormodTaxProfileController controller = loader.getController();
	      controller.setTaxProfileAdd(this,mainTabPane,Nodename,0);// mode is 0
    }
    

    
}
