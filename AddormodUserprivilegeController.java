package AdminController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import AdminApplication.UserPrivilegeMain;
import AdminApplicationModel.UserPrivilegeInsertionDataPOJO;
import AdminApplicationModel.UserPrivilegeTable;
import AdminApplicationModel.UserprivAllnodetable;
import AdminApplicationModel.UserprivilegePOJO;
import AdminServices.UserPrivilegeService;
import commonGUI.AlertsandMessages;
import commonGUI.TabManager;
import commonGUI.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AddormodUserprivilegeController  extends AlertsandMessages  {

	public UserPrivilegeMain UserPrivilegeMain;
	public UserprivilegeController UserPrivCont;
	
    private final TabManager tabManager = TabManager.getInstance();
	UserPrivilegeService userpivService	= new UserPrivilegeService();

/*************************
 * left-side table
 *************************/ 	
	@FXML private TableView<UserprivAllnodetable> allnodetableView;
    @FXML private TableColumn<UserprivAllnodetable, Integer>antvModuleid ,antvnodeid;
    @FXML private TableColumn<UserprivAllnodetable, String> antvmodulename, antvnodename;
    @FXML private TableColumn<UserprivAllnodetable, Boolean> antvselected; 

    //data list for the table
    private ObservableList<UserprivAllnodetable> LeftData = FXCollections.observableArrayList();

/*************************
 * Right-side table
 *************************/ 
	@FXML private TableView<UserprivAllnodetable> SelectedtableView;
    @FXML private TableColumn<UserprivAllnodetable, Integer>stvModuleid ,stvnodeid;
    @FXML private TableColumn<UserprivAllnodetable, String> stvmodulename, stvnodename;
    @FXML private TableColumn<UserprivAllnodetable, Boolean> stvselected; 

    //data list for the table
    private ObservableList<UserprivAllnodetable> RightData = FXCollections.observableArrayList();


    
/*************************
 * Screen attributes
 *************************/   
    @FXML public Button SelectUserBtn;
    @FXML public TextField usernametf;
    
    @FXML private TabPane mainTabPane;
    String Nodename					= null;
    int Mode						= 0;
    
    
 //user pop up attributes    
    String username					= null;
    String userTypeString			= null;
    Integer userSeqid				= 0;
    
 //save data 
    UserprivilegePOJO[] userpojo 	= null;
    UserPrivilegeInsertionDataPOJO UserIntData;
    
    UserPrivilegeTable SelectedUserRow;
    
	public void setComponetsForUserPrivilegeAddScreen(UserPrivilegeMain userPrivilegeMain , 
						TabPane tabPane,String nodeName,int modes,int UserSeqId,
						UserPrivilegeTable selectedUserRow) {
		
		this.UserPrivilegeMain		= userPrivilegeMain;
		this.mainTabPane			= tabPane;
		this.Nodename				= nodeName;
		this.Mode 					= modes;
		this.userSeqid 				= UserSeqId;
		
		if(Mode == 1) {
			this.SelectedUserRow	= selectedUserRow;
			SetDataForModificationUserPrivilege();
		}
	}
	
	
	@FXML
	public void initialize() {
// this methods are to set the data and load the table
		setLeftTableColumnName();
		loadusermodulenode();
		setRightTableColumnName();
	}

	private void SetDataForModificationUserPrivilege(){
	    loadselectedmodulenode();
        loadusermodulenode();
	
  	  if (SelectedUserRow != null ) {
          	userSeqid		= SelectedUserRow.getUserseqID();
            username 		= SelectedUserRow.getUsername();
            usernametf.setText(SelectedUserRow.getUsername().toString());
  		}
  	  	SelectUserBtn.setVisible(false);
        usernametf.setEditable(false);
	}
	
	private void loadusermodulenode() {
		List<UserprivAllnodetable> allnodetable = userpivService.getAllmodulesandNodes("Left",userSeqid);
		LeftData								= FXCollections.observableArrayList(allnodetable);
        allnodetableView.setItems(LeftData);
	}
	
	private void loadselectedmodulenode() {
		List<UserprivAllnodetable> allnodetable = userpivService.getAllmodulesandNodes("Right",userSeqid);
		RightData								= FXCollections.observableArrayList(allnodetable);
		SelectedtableView.setItems(RightData);
	}
    
	private void setLeftTableColumnName() {
		allnodetableView.setEditable(true);
		antvselected.setEditable(true);

	    antvModuleid.setCellValueFactory(new PropertyValueFactory<>("ModuleID"));
	    antvmodulename.setCellValueFactory(new PropertyValueFactory<>("ModeuleName"));
	    antvnodeid.setCellValueFactory(new PropertyValueFactory<>("NodeID"));
	    antvnodename.setCellValueFactory(new PropertyValueFactory<>("NodeName"));

	    // For checkbox column
	    antvselected.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
	    antvselected.setCellFactory(CheckBoxTableCell.forTableColumn(antvselected));
	}

	private void setRightTableColumnName() {
		SelectedtableView.setEditable(true);
		stvselected.setEditable(true);

		stvModuleid.setCellValueFactory(new PropertyValueFactory<>("ModuleID"));
	    stvmodulename.setCellValueFactory(new PropertyValueFactory<>("ModeuleName"));
	    stvnodeid.setCellValueFactory(new PropertyValueFactory<>("NodeID"));
	    stvnodename.setCellValueFactory(new PropertyValueFactory<>("NodeName"));

	    // For checkbox column
	    stvselected.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
	    stvselected.setCellFactory(CheckBoxTableCell.forTableColumn(stvselected));

	}
	public void SaveAction() {
		
		  if(validate_UserPrivilege()== true) {
				prepareDataforInsert();
				
				if(JOptionPane.showConfirmDialog(null, "Do you want to Save this Transaction ",
						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				 
					boolean isInserted	 	= userpivService.SaveUserprivilege( userpojo,userSeqid); 
					
					 
				   if(isInserted==true) {
					   userpivService.UserPrivilegeLog( UserIntData,userSeqid);
					   
					   String Message 		= Mode == 0 ? "Added" : "Modified";
					   showAlert("User Privilege" ,"User Privilege's "+Message+" successfully");
					   CancelAction();// close tab
				   }else {
					   showAlert("User Privilege" ,"Error Please contact admin");
					   return;
				   }
				}
		  }

	
	}
	
	private boolean validate_UserPrivilege() {
		
		if(usernametf.getText().isEmpty() ) {
			 showAlert("User Privilege" ,"Please add a User to submit ");
			 return false;
		}
		if(RightData.size()==0 || RightData.isEmpty()) {
			 showAlert("User Privilege" ,"Please grant some privilege");
			 return false;
		}
		return true;
	}
	
	
	public void prepareDataforInsert() {
	    userpojo 					= new UserprivilegePOJO[RightData.size()];
	    StringBuilder nodeIdBuilder = new StringBuilder();
	    
	    for (int i = 0; i < RightData.size(); i++) {
	        UserprivAllnodetable selectedNode = RightData.get(i);

	        UserprivilegePOJO pojo 	= new UserprivilegePOJO( userSeqid,selectedNode.getNodeID() );
	        userpojo[i] 			= pojo;
	        
	        nodeIdBuilder.append(selectedNode.getNodeID());
	        if (i < RightData.size() - 1) {
	            nodeIdBuilder.append(",");
	        } 
	    }
	    
	    String userNodeIds 		= nodeIdBuilder.toString();
	    String TransactionName 	=  Mode == 0 ? "Add User Privilege":"Modify User Privilege";
	 
	    UserIntData 			= new UserPrivilegeInsertionDataPOJO(
	    		userSeqid,
	    		userNodeIds,
	    		TransactionName,
	    		UserSession.getInstance().getUsername(),
	    		UserSession.getInstance().getUserIp()
	    		);
	    
	    
	}

    
	public void CancelAction() {

        if (mainTabPane != null) {
        	
            Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
            if (currentTab != null) {
            	
                mainTabPane.getTabs().remove(currentTab);
                UserPrivilegeMain.loadUserPrivilegeAdd(Nodename, mainTabPane);
            }
        } else {
            System.out.println("mainTabPane is NULL! Ensure it's correctly set in FXML.");
        }
    
	}
	public void moveselectedtoright(){
		if(!usernametf.getText().isEmpty() ) {
	        var selectedItems = LeftData.stream()
	            .filter(UserprivAllnodetable::Isselected)
	            .collect(Collectors.toList());
	
	        RightData.addAll(selectedItems);
	        LeftData.removeAll(selectedItems);
		    SelectedtableView.setItems(RightData); // 💥 FIXED
		}else {
			 showAlert("User Privilege" ,"please select an user");
			 return ;
		}
	}
	

	public void moveselectedtoleft() {
		if(!usernametf.getText().isEmpty() ) {
			var selectedItems = RightData.stream()
			        .filter(UserprivAllnodetable::Isselected)
			        .collect(Collectors.toList());

			    LeftData.addAll(selectedItems);
			    RightData.removeAll(selectedItems);

			    allnodetableView.setItems(LeftData);
			    SelectedtableView.setItems(RightData);
		}else {
			 showAlert("User Privilege" ,"please select an user");
			 return ;
		}
	    
	}


	public void adduser() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFxml/SelectUserPopup.fxml"));
	        Parent root = loader.load();

	        SelectUserPopupController popupController = loader.getController();
	        popupController.getUserDataformparent(this); // 👈 Pass reference to parent

	        Stage popupStage = new Stage();
	        popupStage.setTitle("Select User");
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setScene(new Scene(root));
	        popupStage.showAndWait();

	        if (this.username != null && !this.username.isEmpty()) {
	            System.out.println("User selected: " + this.username + " | Type: " + this.userTypeString);
	            loadselectedmodulenode();
	            loadusermodulenode();
	         }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
