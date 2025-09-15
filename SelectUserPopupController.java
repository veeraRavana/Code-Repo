package AdminController;

import AdminApplicationModel.SelectUserPopupPOJO;
import AdminServices.SelectuserPopupservices;
import commonGUI.AlertsandMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SelectUserPopupController extends AlertsandMessages{

    @FXML private TableView<SelectUserPopupPOJO> Userdetailstable;
    @FXML private TableColumn<SelectUserPopupPOJO, Integer> Coluserid;
    @FXML private TableColumn<SelectUserPopupPOJO, String> ColUsername;
    @FXML private TableColumn<SelectUserPopupPOJO, String> Colusertype;

    private ObservableList<SelectUserPopupPOJO> userList = FXCollections.observableArrayList();

    AddormodUserprivilegeController AddModUserPriv;
    SelectuserPopupservices SelectUserserv;
    
    public void getUserDataformparent(AddormodUserprivilegeController addmoduserpriv) {
    	this.AddModUserPriv		= addmoduserpriv;
    }

    @FXML
    public void initialize() {
    	setSelectUserTable();
        loadUsers();
        setupRowClickListener();
    }
    
    private void loadUsers() {
    	SelectUserserv 			= new SelectuserPopupservices();
        userList.addAll(SelectUserserv.getAllUsernames());
        Userdetailstable.setItems(userList);
    }

    private void setSelectUserTable() {
       	Coluserid.setCellValueFactory(new PropertyValueFactory<>("userId"));
    	ColUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
    	Colusertype.setCellValueFactory(new PropertyValueFactory<>("userType"));

    }
    
    private void setupRowClickListener() {
        Userdetailstable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
            	handleOkBtn();
            }
        });
    }
        
    public void handleOkBtn() {
    	SelectUserPopupPOJO selectedRow 			= Userdetailstable.getSelectionModel().getSelectedItem();
        if (selectedRow != null && AddModUserPriv != null) {
	        	AddModUserPriv.userSeqid		= selectedRow.getUserSeqID();
	        	AddModUserPriv.usernametf.setText(selectedRow.getUserName());
	        	AddModUserPriv.username 		= selectedRow.getUserName();
	        	AddModUserPriv.userTypeString 	= selectedRow.getUserType();
        	handleCancelBtn();
        }else {
        	showAlert("Select User Popup", "Select a User");
        	return;
        }
    }

    public void handleCancelBtn() {
//    	use to close the popup
    	   Stage stage = (Stage) Userdetailstable.getScene().getWindow();
           stage.close();
    }

}
