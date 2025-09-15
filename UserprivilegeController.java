package AdminController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import AdminApplication.UserPrivilegeMain;
import AdminApplicationModel.UserPrivilegeTable;
import AdminApplicationModel.UserprivAllnodetable;
import AdminApplicationModel.UserprivilegePOJO;
import AdminServices.UserPrivilegeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserprivilegeController {

	@FXML private TabPane mainTabPane; // TabPane for managing tabs
	
    public UserPrivilegeMain userprivMain;
    UserPrivilegeService userPrivilegeService;
    
 /**********************
  * User Table 
  **********************/   
   	@FXML private TableView<UserPrivilegeTable> userTable;
    @FXML private TableColumn<UserPrivilegeTable, Integer> userseqIDColumn;
    @FXML private TableColumn<UserPrivilegeTable, String>  useridColumn;
    @FXML private TableColumn<UserPrivilegeTable, String>  usernameColumn;
    @FXML private TableColumn<UserPrivilegeTable, String>  userPrivilegeColumn;
    @FXML private TableColumn<UserPrivilegeTable, String>  deptColumn;
 /**********************
  * ModuleNodes Table 
  **********************/ 
    @FXML private Label moduleNodeTableLabel;
    
    @FXML private TableView<UserprivAllnodetable> moduleNodeTable;
    @FXML private TableColumn<UserprivAllnodetable, Integer> colModuleId;
    @FXML private TableColumn<UserprivAllnodetable, Integer> colNodeId;
    @FXML private TableColumn<UserprivAllnodetable, String>  colModuleName;
    @FXML private TableColumn<UserprivAllnodetable, String>  colNodeName;
    
    List<UserprivAllnodetable> nodesTableList 	= null ;
   	
	Integer selectedUserSeqID 		= 0; 
	UserprivilegePOJO[] userpojo 	= null;
    UserPrivilegeTable SelectedUserRow;
	
//constructor from UserPrivilegeAdd
	public void setUserprivilege(UserPrivilegeMain Userprivmain) {
		this.userprivMain			= Userprivmain;
	}

	@FXML
	public void initialize() {
		userPrivilegeService 		= new UserPrivilegeService();
	    loadUserTable();
        setupModuleNodeTable();
        
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
        	
            if (newSel != null) {
/* to empty the existing data if anything is selected in the table*/ 
            	nodesTableList				= new ArrayList<>();
            	
            	selectedUserSeqID			= newSel.getUserseqID();
            	nodesTableList 				= userPrivilegeService.getUserNodesByUserseqID(selectedUserSeqID);
                moduleNodeTable.setItems(FXCollections.observableArrayList(nodesTableList));
                moduleNodeTable.setVisible(true);
                moduleNodeTableLabel.setVisible(true);
            }
        });
	    
	}
	 
/*this method loadUserTable load the table data 
 * from the Database and set the data in the table*/	    
	    
    private void loadUserTable() {
        List<UserPrivilegeTable> users = userPrivilegeService.getAllUsersWithPrivilege();
        userTable.setItems(FXCollections.observableArrayList(users));

     //set the columns
        userseqIDColumn.setCellValueFactory(new PropertyValueFactory<>("userseqID"));
        useridColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userPrivilegeColumn.setCellValueFactory(new PropertyValueFactory<>("userPrivilege"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
    }
    
/*this method setupModuleNodeTable is to set the table contents */
    private void setupModuleNodeTable() {
    	
        colModuleId.setCellValueFactory(new PropertyValueFactory<>("moduleID"));
        colModuleName.setCellValueFactory(new PropertyValueFactory<>("modeuleName"));
        colNodeId.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
        colNodeName.setCellValueFactory(new PropertyValueFactory<>("nodeName"));

        moduleNodeTable.setVisible(false);
        moduleNodeTableLabel.setVisible(false);
    }
    
/*this addUserPrivilege is to go for the 
 * add user privilege screen fxml */
    public void handleAddUserPrivilege() {
    	int mode 		= 0; // 0 for Add mode
    	int UserSeqId	= 0;
    	SelectedUserRow	= null;
		userprivMain.addModUserPrivilege(UserSeqId,SelectedUserRow,mode);
	}
    
	public void HandleModifyUserPrivilege() {
		int mode 		= 1; // 1 for Modify mode
		PrepareDataForModifyAndDelete();	
		userprivMain.addModUserPrivilege(selectedUserSeqID,SelectedUserRow,mode);
	}
	
	public void HandleDeleteUserPrivilege() {
		int mode 		= 2; // 2 for Delete mode
		PrepareDataForModifyAndDelete();
//		userprivMain.addModUserPrivilege(selectedUserSeqID,mode);
	}
	

	private void PrepareDataForModifyAndDelete() {
			SelectedUserRow			= userTable.getSelectionModel().getSelectedItem();
	    	userpojo 				= new UserprivilegePOJO[nodesTableList.size()];

	      for (int i = 0; i < nodesTableList.size(); i++) {
	        UserprivAllnodetable selectedNode = nodesTableList.get(i);

	        UserprivilegePOJO pojo 	= new UserprivilegePOJO( 
	        		selectedUserSeqID,
	        		selectedNode.getNodeID()
	        		);
//	        pojo.userseqID 			= selectedUserSeqID; 		// assuming this is already set
//	        pojo.nodeId 			= selectedNode.getNodeID(); // access proper getter
	        userpojo[i] 			= pojo;
	    }
	    
	    
	}
	
	public void HandelNewScreenReq() {
	    try { 
	    		String fxmlPath		= "/AdminFxml/AddScreenNameForUserPrivilege.fxml";
		        FXMLLoader loader 	= new FXMLLoader(getClass().getResource(fxmlPath));
		        Parent root 		= loader.load();
	      
		        String popupname	= "Add New Screen Name";
		        AddScreenNameForUserPrivilegeController AddscrnUpController = loader.getController();
		    	 AddscrnUpController.LoadNewScreenNamePopup(this,popupname);
		    	  
		        Stage popupStage 	= new Stage();
		        popupStage.setTitle(popupname);
		        popupStage.initModality(Modality.APPLICATION_MODAL);
		        popupStage.setScene(new Scene(root));
		        popupStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
}
