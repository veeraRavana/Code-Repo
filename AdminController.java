package AdminController;

import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import AdminApplication.*;
import commonGUI.TabManager;

public class AdminController {

	 public AdminController(String nodeName,TabPane tabPane) {

		if (	nodeName.equals("User Privileges")	) {
			
			new UserPrivilegeMain(nodeName,tabPane);// this guide you to the user Privilege 
	
		}else if (	nodeName.equals("Tax Master")	) {
			
			new TaxProfileMain(nodeName,tabPane);// this guide you to the tax profile 
		
		}else if (	nodeName.equals("Company Register")	) {
			
			new CompanyRegisterMain(nodeName,tabPane);
			
		}else if (	nodeName.equals("Transaction Configuration")	) {
		
			new TransactionConfigurationMain(nodeName, tabPane);
			
		}else if (	nodeName.equals("Material and Product Configuration")	) {
			
			new MaterialandProductConfigMain(nodeName, tabPane);
		
		}else if (	nodeName.equals("Material Master")	) {
			
			new MaterialMasterMain(nodeName, tabPane);
		
		}
//		else if (	nodeName.equals("Product Master")	) {
//		
//			new ProductMasterMain(nodeName, tabPane);
//		}
		else {
			System.out.println("Invalid Node");
		}
	}
	
}
