package AdminController;

import java.util.List;

import AdminApplication.TaxProfileMain;
import AdminApplicationModel.TaxProfile;
import AdminServices.TaxProfileService;
import commonGUI.AlertsandMessages;
import commonGUI.TabManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaxProfileController extends AlertsandMessages  {
	
    @FXML private TabPane mainTabPane; // TabPane for managing tabs
 

	@FXML private TableView<TaxProfile> taxProfileTableView;
    @FXML private TableColumn<TaxProfile, Integer> taxProfileId,versionColumn;
    @FXML private TableColumn<TaxProfile, String> taxUpdatedDate, taxProfileName;

    @FXML private MenuItem addTaxMenuItem, modTaxMenuItem, delTaxMenuItem, viewTaxMenuItem;

    private final TabManager tabManager = TabManager.getInstance();
    private final TaxProfileService taxProfileService = new TaxProfileService();
   
    
    
    public  TaxProfileMain taxProfileMain;
		public void setTaxProfileMain(TaxProfileMain taxProfileMain) {
			this.taxProfileMain= taxProfileMain;
		}
		
		
    @FXML
    public void initialize() {
// Set table column properties
        setColumnName();
//        mainTabPane=taxpromain.mainTabPane;
// Load table data
        loadTaxProfiles();
// Attach actions to menu items
//        addTaxMenuItem.setOnAction(e -> addTaxProfile());
        modTaxMenuItem.setOnAction(e -> modifyTaxProfile());
        delTaxMenuItem.setOnAction(e -> deleteTaxProfile());
        viewTaxMenuItem.setOnAction(e -> viewTaxProfile());
    }

    private void setColumnName() {
        taxProfileId.setCellValueFactory(new PropertyValueFactory<>("taxProfileId"));
        taxUpdatedDate.setCellValueFactory(new PropertyValueFactory<>("taxUpdatedDate"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        taxProfileName.setCellValueFactory(new PropertyValueFactory<>("taxProfileName"));
    }

    private void loadTaxProfiles() {
        List<TaxProfile> taxProfiles = taxProfileService.getAllTaxProfiles();
        ObservableList<TaxProfile> data = FXCollections.observableArrayList(taxProfiles);
        taxProfileTableView.setItems(data);
    }

    private void deleteTaxProfile() {
        TaxProfile selected = taxProfileTableView.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
           Boolean  isDeleted	=  taxProfileService.deleteTaxProfile(selected.getTaxProfileId());
// add the code for the checking the if the tax is used in -> the invoice / other screen 
//           {
//        	 //----------add code alert message  
//           }
           if(isDeleted==true) {
               showAlert("No Tax Selected", "Tax Id :" +selected.getTaxProfileId() +" is deleted succesfully");
        	   loadTaxProfiles();
           }else {
               showAlert("No Tax Selected", "Error while deleting ");
               return;
           }
           
        } else {
            showAlert("No Tax Selected", "Please select a tax profile to delete.");
        }
    }
    
    
    // add tax profile
    @FXML
    public void addTaxProfile() {
    	taxProfileMain.addTaxProfile();
    }


    private void modifyTaxProfile() {
//        TaxProfile selected = taxProfileTableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            tabManager.openProductTab("Modify Tax", "/AdminFxml/ModifyTax.fxml", null);
//        } else {
//            showAlert("No Tax Selected", "Please select a tax profile to modify.");
//        }
    }

    private void viewTaxProfile() {
//        TaxProfile selected = taxProfileTableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            tabManager.openProductTab("View Tax", "/AdminFxml/ViewTax.fxml", null);
//        } else {
//            showAlert("No Tax Selected", "Please select a tax profile to view.");
//        }
    }




}