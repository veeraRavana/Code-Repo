package AdminController;

import AdminServices.UserPrivilegeService;
import LoginPageModel.ModuleNode;
import commonGUI.AlertsandMessages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddScreenNameForUserPrivilegeController extends AlertsandMessages{

public UserprivilegeController userprivmain;
	
//	@FXML private TabPane mainTabPane;
	UserPrivilegeService userpivService	= new UserPrivilegeService();
	
    String Nodename		= null;
   
    @FXML private TableView<ModuleNode> NewScreenModuleTable;
    @FXML private TableColumn<ModuleNode, Integer> ColNewScreenModuleID;
    @FXML private TableColumn<ModuleNode, String> ColNewScreenModuleName;
    
    @FXML private TextField ScreenNameTXB;
    @FXML private Button AddScreenNameBtn;
    
	public void LoadNewScreenNamePopup(UserprivilegeController userprivilegeController,
			 String nodename) {
	
		this.userprivmain		= userprivilegeController;
		this.Nodename			= nodename;
		 
	}
	
	@FXML
	public void initialize() {
		userpivService			= new UserPrivilegeService();
		setDataForAddScreenName();
		loadModuleNodeTable();
	}

	private void setDataForAddScreenName() {
		ColNewScreenModuleID.setCellValueFactory(new PropertyValueFactory<>("moduleId"));
		ColNewScreenModuleName.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
	}
	
	private void loadModuleNodeTable() {
		setDataForAddScreenName();
		NewScreenModuleTable.setItems(userpivService.setModuleNodeTableData());
		NewScreenModuleTable.setVisible(true);
	}
	
	
	public void handleAddNewScreenName() {
		if(validateScreenName()) {
				String screenName	 = ScreenNameTXB.getText();
				int ModuleID		 = NewScreenModuleTable.getSelectionModel().getSelectedItem().getModule_id();
				
				int nodeId			 = userpivService.saveNewScreenNameIntoTable(ModuleID,screenName);
				if(nodeId != 0) {
					showAlert(Nodename,screenName+" New Screen Name added successfully");
					  Stage stage = (Stage) AddScreenNameBtn.getScene().getWindow();
					  stage.close();
				} else {
					showAlertWarning(Nodename,"Failed to add new Screen Name.");
				}
		} 
	}
	
	  
    public  void handleCancel(javafx.event.ActionEvent event) {
    	 Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
	
	
	private boolean validateScreenName() {
		if(NewScreenModuleTable.getSelectionModel().isEmpty()) {
			showAlertWarning(Nodename,"Please select a module from the table.");
			return false;
		}
		if(ScreenNameTXB.getText().isEmpty()) {
			showAlertWarning(Nodename," Screen Name Field should not be empty.");
			return false;
		}
	
		return true; // Placeholder return value
	}

}
